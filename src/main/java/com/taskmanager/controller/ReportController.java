package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reportes")
public class ReportController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showReports(Model model) {
        List<Task> allTasks = taskService.getAllTasks();
        
        // Statistics
        long total = allTasks.size();
        long completadas = allTasks.stream().filter(t -> t.getEstado() == Task.Estado.COMPLETADA).count();
        long pendientes = allTasks.stream().filter(t -> t.getEstado() == Task.Estado.PENDIENTE).count();
        long enProgreso = allTasks.stream().filter(t -> t.getEstado() == Task.Estado.EN_PROGRESO).count();
        
        long altaPrioridad = allTasks.stream().filter(t -> t.getPrioridad() == Task.Prioridad.ALTA).count();
        long mediaPrioridad = allTasks.stream().filter(t -> t.getPrioridad() == Task.Prioridad.MEDIA).count();
        long bajaPrioridad = allTasks.stream().filter(t -> t.getPrioridad() == Task.Prioridad.BAJA).count();
        
        long vencidas = taskService.getVencidasCount();
        
        // Completion rate
        double completionRate = total > 0 ? (completadas * 100.0 / total) : 0;
        
        // Tasks by project
        Map<String, Long> tasksByProject = allTasks.stream()
                .filter(t -> t.getProyecto() != null && !t.getProyecto().isEmpty())
                .collect(Collectors.groupingBy(Task::getProyecto, Collectors.counting()));
        
        // Tasks by assignee
        Map<String, Long> tasksByAssignee = allTasks.stream()
                .filter(t -> t.getAsignadoA() != null && !t.getAsignadoA().isEmpty())
                .collect(Collectors.groupingBy(Task::getAsignadoA, Collectors.counting()));
        
        // Add to model
        model.addAttribute("total", total);
        model.addAttribute("completadas", completadas);
        model.addAttribute("pendientes", pendientes);
        model.addAttribute("enProgreso", enProgreso);
        model.addAttribute("altaPrioridad", altaPrioridad);
        model.addAttribute("mediaPrioridad", mediaPrioridad);
        model.addAttribute("bajaPrioridad", bajaPrioridad);
        model.addAttribute("vencidas", vencidas);
        model.addAttribute("completionRate", String.format("%.1f", completionRate));
        model.addAttribute("tasksByProject", tasksByProject);
        model.addAttribute("tasksByAssignee", tasksByAssignee);
        model.addAttribute("totalProjects", projectService.getAllProjects().size());
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        
        return "reportes";
    }
}
