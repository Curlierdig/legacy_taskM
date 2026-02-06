package com.taskmanager.service;

import com.taskmanager.model.Project;
import com.taskmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }
    
    public Project saveProject(Project project) {
        if (project.getFechaCreacion() == null) {
            project.setFechaCreacion(java.time.LocalDateTime.now());
        }
        return projectRepository.save(project);
    }
    
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
    
    public Project updateProject(String id, Project project) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isEmpty()) {
            throw new IllegalArgumentException("Proyecto no encontrado con ID: " + id);
        }
        
        Project updatedProject = existingProject.get();
        updatedProject.setNombre(project.getNombre());
        updatedProject.setDescripcion(project.getDescripcion());
        // Mantener la fecha de creación original
        
        return projectRepository.save(updatedProject);
    }
    
    public Project findByNombre(String nombre) {
        return projectRepository.findByNombre(nombre);
    }
}
