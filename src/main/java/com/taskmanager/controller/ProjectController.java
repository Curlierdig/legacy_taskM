package com.taskmanager.controller;

import com.taskmanager.model.Project;
import com.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proyectos")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("project", new Project());
        return "proyectos";
    }
    
    @PostMapping("/agregar")
    public String agregarProyecto(@Valid @ModelAttribute Project project,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, complete todos los campos requeridos");
            return "redirect:/proyectos";
        }
        
        projectService.saveProject(project);
        redirectAttributes.addFlashAttribute("success", "Proyecto agregado exitosamente");
        return "redirect:/proyectos";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable String id, RedirectAttributes redirectAttributes) {
        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("success", "Proyecto eliminado exitosamente");
        return "redirect:/proyectos";
    }
}
