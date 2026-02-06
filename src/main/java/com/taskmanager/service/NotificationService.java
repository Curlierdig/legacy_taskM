package com.taskmanager.service;

import com.taskmanager.model.Notification;
import com.taskmanager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public List<Notification> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
    }
    
    public long getUnreadCount(String userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }
    
    public Optional<Notification> getById(String id) {
        return notificationRepository.findById(id);
    }
    
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
    
    public void markAsRead(String id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
    
    public void markAllAsRead(String userId) {
        List<Notification> unread = getUnreadNotifications(userId);
        unread.forEach(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
    
    public void delete(String id) {
        notificationRepository.deleteById(id);
    }
    
    // Notification creation helpers
    public void notifyTaskAssigned(String userId, String taskTitle, String taskId) {
        Notification notification = new Notification(
            userId,
            "TASK_ASSIGNED",
            "Nueva tarea asignada",
            "Se te ha asignado la tarea: " + taskTitle,
            taskId,
            "TASK"
        );
        save(notification);
    }
    
    public void notifyTaskDueSoon(String userId, String taskTitle, String taskId, int daysLeft) {
        Notification notification = new Notification(
            userId,
            "DUE_SOON",
            "Tarea próxima a vencer",
            "La tarea '" + taskTitle + "' vence en " + daysLeft + " día(s)",
            taskId,
            "TASK"
        );
        save(notification);
    }
    
    public void notifyCommentAdded(String userId, String taskTitle, String commentId) {
        Notification notification = new Notification(
            userId,
            "COMMENT_ADDED",
            "Nuevo comentario",
            "Se agregó un comentario en: " + taskTitle,
            commentId,
            "COMMENT"
        );
        save(notification);
    }
}
