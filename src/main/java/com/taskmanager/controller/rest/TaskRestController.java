package com.taskmanager.controller.rest;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskRestController {
    
    @Autowired
    private TaskService taskService;
    
    /**
     * Get all tasks
     * GET /api/tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get task by ID
     * GET /api/tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Create new task
     * POST /api/tasks
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
    
    /**
     * Update existing task
     * PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, 
                                          @Valid @RequestBody Task task) {
        return taskService.getTaskById(id)
                .map(existingTask -> {
                    task.setId(id);
                    Task updatedTask = taskService.saveTask(task);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Delete task
     * DELETE /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable String id) {
        return taskService.getTaskById(id)
                .map(task -> {
                    taskService.deleteTask(id);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Tarea eliminada exitosamente");
                    response.put("id", id);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get tasks by estado
     * GET /api/tasks/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Task>> getTasksByEstado(@PathVariable String estado) {
        try {
            Task.Estado estadoEnum = Task.Estado.valueOf(estado.toUpperCase());
            List<Task> tasks = taskService.getTasksByEstado(estadoEnum);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get tasks by prioridad
     * GET /api/tasks/prioridad/{prioridad}
     */
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<Task>> getTasksByPrioridad(@PathVariable String prioridad) {
        try {
            Task.Prioridad prioridadEnum = Task.Prioridad.valueOf(prioridad.toUpperCase());
            List<Task> tasks = taskService.getTasksByPrioridad(prioridadEnum);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get tasks by proyecto
     * GET /api/tasks/proyecto/{proyecto}
     */
    @GetMapping("/proyecto/{proyecto}")
    public ResponseEntity<List<Task>> getTasksByProyecto(@PathVariable String proyecto) {
        List<Task> tasks = taskService.getTasksByProyecto(proyecto);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get tasks by asignadoA
     * GET /api/tasks/asignado/{asignadoA}
     */
    @GetMapping("/asignado/{asignadoA}")
    public ResponseEntity<List<Task>> getTasksByAsignadoA(@PathVariable String asignadoA) {
        List<Task> tasks = taskService.getTasksByAsignadoA(asignadoA);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get overdue tasks
     * GET /api/tasks/vencidas
     */
    @GetMapping("/vencidas")
    public ResponseEntity<List<Task>> getVencidasTasks() {
        List<Task> tasks = taskService.getVencidasTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get task statistics
     * GET /api/tasks/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getTaskStats() {
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", taskService.getTotalTasks());
        estadisticas.put("completadas", taskService.getCompletadasCount());
        estadisticas.put("pendientes", taskService.getPendientesCount());
        estadisticas.put("altaPrioridad", taskService.getAltaPrioridadCount());
        estadisticas.put("vencidas", taskService.getVencidasCount());
        return ResponseEntity.ok(estadisticas);
    }
}
