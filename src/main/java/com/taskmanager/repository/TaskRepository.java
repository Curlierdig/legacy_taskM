package com.taskmanager.repository;

import com.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByEstado(Task.Estado estado);
    List<Task> findByPrioridad(Task.Prioridad prioridad);
    List<Task> findByProyecto(String proyecto);
    List<Task> findByAsignadoA(String asignadoA);
    List<Task> findByFechaVencimientoBefore(java.time.LocalDate fecha);
}
