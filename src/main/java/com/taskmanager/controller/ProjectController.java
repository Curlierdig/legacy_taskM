package com.taskmanager.controller;

import com.taskmanager.model.Project;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private HistoryService historyService;
    
    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("project", new Project());
        return "proyectos";
    }
    
    @PostMapping("/agregar")
    public String agregarProyecto(@Valid @ModelAttribute Project project,
                                  BindingResult result,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/proyectos";
        }
        
        Project savedProject = projectService.saveProject(project);
        
        // Log history
        String username = authentication.getName();
        historyService.logProjectAction(savedProject.getId(), savedProject.getNombre(), "CREATE", username, username);
        
        redirectAttributes.addFlashAttribute("success", "Proyecto agregado exitosamente");
        return "redirect:/proyectos";
    }
    
    @PostMapping("/actualizar")
    public String actualizarProyecto(@Valid @ModelAttribute Project project,
                                     BindingResult result,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/proyectos";
        }
        
        if (project.getId() == null || project.getId().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de proyecto no válido");
            return "redirect:/proyectos";
        }
        
        Project savedProject = projectService.updateProject(project.getId(), project);
        
        // Log history
        String username = authentication.getName();
        historyService.logProjectAction(savedProject.getId(), savedProject.getNombre(), "UPDATE", username, username);
        
        redirectAttributes.addFlashAttribute("success", "Proyecto actualizado exitosamente");
        return "redirect:/proyectos";
    }
    
    @GetMapping("/editar/{id}")
    public String editarProyecto(@PathVariable String id, Model model) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + id));
        
        model.addAttribute("project", project);
        model.addAttribute("projects", projectService.getAllProjects());
        return "proyectos";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable String id, 
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        if (id == null || id.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ID de proyecto no válido");
            return "redirect:/proyectos";
        }
        
        // Get project name before deletion
        Optional<Project> project = projectService.getProjectById(id);
        String projectName = project.map(Project::getNombre).orElse("Desconocido");
        
        projectService.deleteProject(id);
        
        // Log history
        String username = authentication.getName();
        historyService.logProjectAction(id, projectName, "DELETE", username, username);
        
        redirectAttributes.addFlashAttribute("success", "Proyecto eliminado exitosamente");
        return "redirect:/proyectos";
    }
}
