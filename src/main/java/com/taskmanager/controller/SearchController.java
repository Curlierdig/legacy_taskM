package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/busqueda")
public class SearchController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping
    public String buscar(@RequestParam(required = false) String query,
                        @RequestParam(required = false) String estado,
                        @RequestParam(required = false) String prioridad,
                        Model model) {
        List<Task> tasks = taskService.getAllTasks();
        
        if (query != null && !query.isEmpty()) {
            final String searchQuery = query.toLowerCase();
            tasks = tasks.stream()
                    .filter(task -> 
                        task.getTitulo().toLowerCase().contains(searchQuery) ||
                        (task.getDescripcion() != null && task.getDescripcion().toLowerCase().contains(searchQuery)) ||
                        (task.getProyecto() != null && task.getProyecto().toLowerCase().contains(searchQuery))
                    )
                    .toList();
        }
        
        if (estado != null && !estado.isEmpty()) {
            try {
                Task.Estado estadoEnum = Task.Estado.valueOf(estado);
                tasks = tasks.stream()
                        .filter(task -> task.getEstado() == estadoEnum)
                        .toList();
            } catch (IllegalArgumentException e) {
                // Invalid estado, ignore filter
            }
        }
        
        if (prioridad != null && !prioridad.isEmpty()) {
            try {
                Task.Prioridad prioridadEnum = Task.Prioridad.valueOf(prioridad);
                tasks = tasks.stream()
                        .filter(task -> task.getPrioridad() == prioridadEnum)
                        .toList();
            } catch (IllegalArgumentException e) {
                // Invalid prioridad, ignore filter
            }
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("estados", Task.Estado.values());
        model.addAttribute("prioridades", Task.Prioridad.values());
        model.addAttribute("query", query);
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("prioridadFiltro", prioridad);
        
        return "busqueda";
    }
}
