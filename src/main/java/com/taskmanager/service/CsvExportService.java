package com.taskmanager.service;

import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CsvExportService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    private static final String CSV_SEPARATOR = ",";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Exporta todas las tareas a CSV.
     */
    public byte[] exportTasksCsv() {
        List<Task> tasks = taskService.getAllTasks();

        StringBuilder sb = new StringBuilder();
        // BOM para Excel
        sb.append('\uFEFF');
        // Header
        sb.append("ID,Título,Descripción,Estado,Prioridad,Proyecto,Asignado A,Fecha Vencimiento,Horas Estimadas\n");

        for (Task task : tasks) {
            sb.append(escapeCsv(task.getId())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getTitulo())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getDescripcion())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getEstado() != null ? task.getEstado().getDisplayName() : "")).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getPrioridad() != null ? task.getPrioridad().getDisplayName() : "")).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getProyecto())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getAsignadoA())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(task.getFechaVencimiento() != null ? task.getFechaVencimiento().format(DATE_FORMAT) : "")).append(CSV_SEPARATOR);
            sb.append(task.getHorasEstimadas() != null ? task.getHorasEstimadas() : "");
            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Exporta todos los proyectos a CSV con el conteo de tareas.
     */
    public byte[] exportProjectsCsv() {
        List<Project> projects = projectService.getAllProjects();
        List<Task> allTasks = taskService.getAllTasks();

        // Contar tareas por proyecto
        Map<String, Long> taskCountByProject = allTasks.stream()
                .filter(t -> t.getProyecto() != null && !t.getProyecto().isEmpty())
                .collect(Collectors.groupingBy(Task::getProyecto, Collectors.counting()));

        StringBuilder sb = new StringBuilder();
        sb.append('\uFEFF');
        sb.append("ID,Nombre,Descripción,Fecha Creación,Número de Tareas\n");

        for (Project project : projects) {
            sb.append(escapeCsv(project.getId())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(project.getNombre())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(project.getDescripcion())).append(CSV_SEPARATOR);
            sb.append(escapeCsv(project.getFechaCreacion() != null ? project.getFechaCreacion().format(DATETIME_FORMAT) : "")).append(CSV_SEPARATOR);
            sb.append(taskCountByProject.getOrDefault(project.getNombre(), 0L));
            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Exporta resumen de tareas agrupadas por proyecto.
     */
    public byte[] exportTasksByProjectCsv() {
        List<Task> allTasks = taskService.getAllTasks();

        Map<String, List<Task>> tasksByProject = allTasks.stream()
                .filter(t -> t.getProyecto() != null && !t.getProyecto().isEmpty())
                .collect(Collectors.groupingBy(Task::getProyecto));

        StringBuilder sb = new StringBuilder();
        sb.append('\uFEFF');
        sb.append("Proyecto,Total Tareas,Completadas,Pendientes,En Progreso\n");

        for (Map.Entry<String, List<Task>> entry : tasksByProject.entrySet()) {
            List<Task> tasks = entry.getValue();
            long completadas = tasks.stream().filter(t -> t.getEstado() == Task.Estado.COMPLETADA).count();
            long pendientes = tasks.stream().filter(t -> t.getEstado() == Task.Estado.PENDIENTE).count();
            long enProgreso = tasks.stream().filter(t -> t.getEstado() == Task.Estado.EN_PROGRESO).count();

            sb.append(escapeCsv(entry.getKey())).append(CSV_SEPARATOR);
            sb.append(tasks.size()).append(CSV_SEPARATOR);
            sb.append(completadas).append(CSV_SEPARATOR);
            sb.append(pendientes).append(CSV_SEPARATOR);
            sb.append(enProgreso);
            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Exporta resumen de tareas agrupadas por usuario asignado.
     */
    public byte[] exportTasksByUserCsv() {
        List<Task> allTasks = taskService.getAllTasks();

        Map<String, List<Task>> tasksByUser = allTasks.stream()
                .filter(t -> t.getAsignadoA() != null && !t.getAsignadoA().isEmpty())
                .collect(Collectors.groupingBy(Task::getAsignadoA));

        StringBuilder sb = new StringBuilder();
        sb.append('\uFEFF');
        sb.append("Usuario,Total Tareas,Completadas,Pendientes,En Progreso\n");

        for (Map.Entry<String, List<Task>> entry : tasksByUser.entrySet()) {
            List<Task> tasks = entry.getValue();
            long completadas = tasks.stream().filter(t -> t.getEstado() == Task.Estado.COMPLETADA).count();
            long pendientes = tasks.stream().filter(t -> t.getEstado() == Task.Estado.PENDIENTE).count();
            long enProgreso = tasks.stream().filter(t -> t.getEstado() == Task.Estado.EN_PROGRESO).count();

            sb.append(escapeCsv(entry.getKey())).append(CSV_SEPARATOR);
            sb.append(tasks.size()).append(CSV_SEPARATOR);
            sb.append(completadas).append(CSV_SEPARATOR);
            sb.append(pendientes).append(CSV_SEPARATOR);
            sb.append(enProgreso);
            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Escapa un valor para uso en CSV (maneja comas, comillas y saltos de línea).
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        // Si contiene coma, comillas o salto de línea, envolver en comillas
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
