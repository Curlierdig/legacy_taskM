package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
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
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/";
        }
        
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("success", "Tarea agregada exitosamente");
        return "redirect:/";
    }
    
    @PostMapping("/actualizar")
    public String actualizarTarea(@Valid @ModelAttribute Task task,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/";
        }
        
        if (task.getId() == null || task.getId().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de tarea no válido");
            return "redirect:/";
        }
        
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("success", "Tarea actualizada exitosamente");
        return "redirect:/";
    }
    
    @PostMapping("/eliminar")
    public String eliminarTarea(@RequestParam String id, 
                               RedirectAttributes redirectAttributes) {
        if (id == null || id.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de tarea no válido");
            return "redirect:/";
        }
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("success", "Tarea eliminada exitosamente");
        return "redirect:/";
    }
    
    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable String id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada: " + id));
        
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
