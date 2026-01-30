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
    
    public Project findByNombre(String nombre) {
        return projectRepository.findByNombre(nombre);
    }
}
