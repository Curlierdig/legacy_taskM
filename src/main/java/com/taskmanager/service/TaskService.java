package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }
    
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    
    public List<Task> getTasksByEstado(Task.Estado estado) {
        return taskRepository.findByEstado(estado);
    }
    
    public List<Task> getTasksByPrioridad(Task.Prioridad prioridad) {
        return taskRepository.findByPrioridad(prioridad);
    }
    
    public List<Task> getTasksByProyecto(String proyecto) {
        return taskRepository.findByProyecto(proyecto);
    }
    
    public List<Task> getTasksByAsignadoA(String asignadoA) {
        return taskRepository.findByAsignadoA(asignadoA);
    }
    
    public List<Task> getVencidasTasks() {
        return taskRepository.findByFechaVencimientoBefore(LocalDate.now());
    }
    
    public long getTotalTasks() {
        return taskRepository.count();
    }
    
    public long getCompletadasCount() {
        return taskRepository.findByEstado(Task.Estado.COMPLETADA).size();
    }
    
    public long getPendientesCount() {
        return taskRepository.findByEstado(Task.Estado.PENDIENTE).size();
    }
    
    public long getAltaPrioridadCount() {
        return taskRepository.findByPrioridad(Task.Prioridad.ALTA).size();
    }
    
    public long getVencidasCount() {
        return getVencidasTasks().size();
    }
}
