package com.taskmanager.config;

import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.ProjectRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize only if database is empty
        if (userRepository.count() == 0) {
            initializeData();
        }
    }
    
    private void initializeData() {
        // Create default admin user with encoded password
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setNombre("Administrador");
        admin.setRol("ADMIN");
        userRepository.save(admin);
        
        // Create sample users with encoded passwords
        User user1 = new User();
        user1.setUsername("juan");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setNombre("Juan Pérez");
        user1.setRol("USER");
        userRepository.save(user1);
        
        User user2 = new User();
        user2.setUsername("maria");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setNombre("María García");
        user2.setRol("USER");
        userRepository.save(user2);
        
        // Create sample projects
        Project project1 = new Project("Proyecto Web", "Desarrollo de aplicación web moderna");
        projectRepository.save(project1);
        
        Project project2 = new Project("Proyecto Mobile", "Desarrollo de aplicación móvil");
        projectRepository.save(project2);
        
        // Create sample tasks
        Task task1 = new Task();
        task1.setTitulo("Diseñar interfaz de usuario");
        task1.setDescripcion("Crear mockups y diseños para la interfaz principal");
        task1.setEstado(Task.Estado.PENDIENTE);
        task1.setPrioridad(Task.Prioridad.ALTA);
        task1.setProyecto("Proyecto Web");
        task1.setAsignadoA("juan");
        task1.setFechaVencimiento(LocalDate.now().plusDays(7));
        task1.setHorasEstimadas(16);
        taskRepository.save(task1);
        
        Task task2 = new Task();
        task2.setTitulo("Implementar backend API");
        task2.setDescripcion("Desarrollar endpoints REST para la aplicación");
        task2.setEstado(Task.Estado.EN_PROGRESO);
        task2.setPrioridad(Task.Prioridad.ALTA);
        task2.setProyecto("Proyecto Web");
        task2.setAsignadoA("maria");
        task2.setFechaVencimiento(LocalDate.now().plusDays(14));
        task2.setHorasEstimadas(40);
        taskRepository.save(task2);
        
        Task task3 = new Task();
        task3.setTitulo("Revisar documentación");
        task3.setDescripcion("Revisar y actualizar la documentación del proyecto");
        task3.setEstado(Task.Estado.COMPLETADA);
        task3.setPrioridad(Task.Prioridad.BAJA);
        task3.setProyecto("Proyecto Mobile");
        task3.setAsignadoA("juan");
        task3.setFechaVencimiento(LocalDate.now().minusDays(2));
        task3.setHorasEstimadas(8);
        taskRepository.save(task3);
    }
}
