package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.UserService;
import com.taskmanager.service.HistoryService;
import com.taskmanager.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("task", new Task());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("estados", Task.Estado.values());
        model.addAttribute("prioridades", Task.Prioridad.values());
        
        // Statistics
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", taskService.getTotalTasks());
        estadisticas.put("completadas", taskService.getCompletadasCount());
        estadisticas.put("pendientes", taskService.getPendientesCount());
        estadisticas.put("altaPrioridad", taskService.getAltaPrioridadCount());
        estadisticas.put("vencidas", taskService.getVencidasCount());
        
        model.addAttribute("estadisticas", estadisticas);
        
        return "index";
    }
    
    @PostMapping("/agregar")
    public String agregarTarea(@Valid @ModelAttribute Task task, 
                              BindingResult result,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/";
        }
        
        // Clear empty ID so MongoDB can auto-generate ObjectId
        if (task.getId() != null && task.getId().isEmpty()) {
            task.setId(null);
        }
        
        Task savedTask = taskService.saveTask(task);
        
        // Log history
        String username = authentication.getName();
        historyService.logTaskAction(savedTask.getId(), savedTask.getTitulo(), "CREATE", username, username);
        
        // Notify assigned user if different from creator
        if (task.getAsignadoA() != null && !task.getAsignadoA().isEmpty() 
            && !task.getAsignadoA().equals(username)) {
            notificationService.notifyTaskAssigned(task.getAsignadoA(), task.getTitulo(), savedTask.getId());
        }
        
        redirectAttributes.addFlashAttribute("success", "Tarea agregada exitosamente");
        return "redirect:/";
    }
    
    @PostMapping("/actualizar")
    public String actualizarTarea(@Valid @ModelAttribute Task task,
                                  BindingResult result,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/";
        }
        
        if (task.getId() == null || task.getId().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de tarea no válido");
            return "redirect:/";
        }
        
        // Get old task to check assignment changes
        Optional<Task> oldTask = taskService.getTaskById(task.getId());
        String oldAssignee = oldTask.map(Task::getAsignadoA).orElse(null);
        
        Task savedTask = taskService.saveTask(task);
        
        // Log history
        String username = authentication.getName();
        historyService.logTaskAction(savedTask.getId(), savedTask.getTitulo(), "UPDATE", username, username);
        
        // Notify if assignment changed
        if (task.getAsignadoA() != null && !task.getAsignadoA().isEmpty() 
            && !task.getAsignadoA().equals(oldAssignee)
            && !task.getAsignadoA().equals(username)) {
            notificationService.notifyTaskAssigned(task.getAsignadoA(), task.getTitulo(), savedTask.getId());
        }
        
        redirectAttributes.addFlashAttribute("success", "Tarea actualizada exitosamente");
        return "redirect:/";
    }
    
    @PostMapping("/eliminar")
    public String eliminarTarea(@RequestParam String id,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        if (id == null || id.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de tarea no válido");
            return "redirect:/";
        }
        
        // Get task name before deletion
        Optional<Task> task = taskService.getTaskById(id);
        String taskName = task.map(Task::getTitulo).orElse("Desconocida");
        
        taskService.deleteTask(id);
        
        // Log history
        String username = authentication.getName();
        historyService.logTaskAction(id, taskName, "DELETE", username, username);
        
        redirectAttributes.addFlashAttribute("success", "Tarea eliminada exitosamente");
        return "redirect:/";
    }
    
    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        
        if (taskOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tarea no encontrada: " + id);
            return "redirect:/";
        }
        
        Task task = taskOpt.get();
        model.addAttribute("task", task);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("estados", Task.Estado.values());
        model.addAttribute("prioridades", Task.Prioridad.values());
        
        // Statistics
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", taskService.getTotalTasks());
        estadisticas.put("completadas", taskService.getCompletadasCount());
        estadisticas.put("pendientes", taskService.getPendientesCount());
        estadisticas.put("altaPrioridad", taskService.getAltaPrioridadCount());
        estadisticas.put("vencidas", taskService.getVencidasCount());
        
        model.addAttribute("estadisticas", estadisticas);
        
        return "index";
    }
}

