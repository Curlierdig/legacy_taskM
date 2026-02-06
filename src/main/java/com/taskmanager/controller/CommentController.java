package com.taskmanager.controller;

import com.taskmanager.model.Comment;
import com.taskmanager.model.Task;
import com.taskmanager.service.CommentService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.HistoryService;
import com.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/comentarios")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String listComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("comment", new Comment());
        return "comentarios";
    }

    @GetMapping("/tarea/{taskId}")
    public String listCommentsByTask(@PathVariable String taskId, Model model) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isEmpty()) {
            return "redirect:/comentarios";
        }
        model.addAttribute("task", task.get());
        model.addAttribute("comments", commentService.getCommentsByTaskId(taskId));
        model.addAttribute("comment", new Comment());
        model.addAttribute("tasks", taskService.getAllTasks());
        return "comentarios";
    }

    @PostMapping("/agregar")
    public String addComment(@ModelAttribute Comment comment,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        if (comment.getTaskId() == null || comment.getTaskId().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una tarea");
            return "redirect:/comentarios";
        }
        
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El comentario no puede estar vacío");
            return "redirect:/comentarios";
        }

        String username = authentication.getName();
        comment.setUserId(username);
        comment.setUserName(username);
        Comment savedComment = commentService.saveComment(comment);
        
        // Get task name for history
        Optional<Task> task = taskService.getTaskById(comment.getTaskId());
        String taskName = task.map(Task::getTitulo).orElse("Tarea");
        
        // Log history
        historyService.logCommentAction(savedComment.getId(), taskName, "CREATE", username, username);
        
        // Notify task assignee if different from commenter
        task.ifPresent(t -> {
            if (t.getAsignadoA() != null && !t.getAsignadoA().isEmpty() 
                && !t.getAsignadoA().equals(username)) {
                notificationService.notifyCommentAdded(t.getAsignadoA(), t.getTitulo(), savedComment.getId());
            }
        });
        
        redirectAttributes.addFlashAttribute("success", "Comentario agregado exitosamente");
        return "redirect:/comentarios";
    }

    @PostMapping("/eliminar/{id}")
    public String deleteComment(@PathVariable String id, 
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        // Get comment details before deletion
        Optional<Comment> comment = commentService.getCommentById(id);
        String taskName = "Tarea";
        if (comment.isPresent()) {
            Optional<Task> task = taskService.getTaskById(comment.get().getTaskId());
            taskName = task.map(Task::getTitulo).orElse("Tarea");
        }
        
        commentService.deleteComment(id);
        
        // Log history
        String username = authentication.getName();
        historyService.logCommentAction(id, taskName, "DELETE", username, username);
        
        redirectAttributes.addFlashAttribute("success", "Comentario eliminado exitosamente");
        return "redirect:/comentarios";
    }
}
