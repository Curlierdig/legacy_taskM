package com.taskmanager.controller.rest;

import com.taskmanager.model.Project;
import com.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectRestController {
    
    @Autowired
    private ProjectService projectService;
    
    /**
     * Get all projects
     * GET /api/projects
     */
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    /**
     * Get project by ID
     * GET /api/projects/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get project by nombre
     * GET /api/projects/nombre/{nombre}
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Project> getProjectByNombre(@PathVariable String nombre) {
        Project project = projectService.findByNombre(nombre);
        if (project != null) {
            return ResponseEntity.ok(project);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Create new project
     * POST /api/projects
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }
    
    /**
     * Update existing project
     * PUT /api/projects/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable String id, 
                                                 @Valid @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete project
     * DELETE /api/projects/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable String id) {
        return projectService.getProjectById(id)
                .map(project -> {
                    projectService.deleteProject(id);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Proyecto eliminado exitosamente");
                    response.put("id", id);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
