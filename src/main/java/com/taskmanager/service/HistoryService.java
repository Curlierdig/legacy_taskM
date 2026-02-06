package com.taskmanager.service;

import com.taskmanager.model.HistoryEntry;
import com.taskmanager.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    
    @Autowired
    private HistoryRepository historyRepository;
    
    public List<HistoryEntry> getAllHistory() {
        return historyRepository.findAllByOrderByTimestampDesc();
    }
    
    public List<HistoryEntry> getRecentHistory() {
        return historyRepository.findTop50ByOrderByTimestampDesc();
    }
    
    public List<HistoryEntry> getHistoryByEntityType(String entityType) {
        return historyRepository.findByEntityTypeOrderByTimestampDesc(entityType);
    }
    
    public List<HistoryEntry> getHistoryByUserId(String userId) {
        return historyRepository.findByUserIdOrderByTimestampDesc(userId);
    }
    
    public void logAction(String entityType, String entityId, String entityName,
                         String action, String userId, String userName, String details) {
        HistoryEntry entry = new HistoryEntry(entityType, entityId, entityName, 
                                               action, userId, userName, details);
        historyRepository.save(entry);
    }
    
    public void logTaskAction(String taskId, String taskName, String action, 
                              String userId, String userName) {
        String details = switch (action) {
            case "CREATE" -> "Tarea creada";
            case "UPDATE" -> "Tarea actualizada";
            case "DELETE" -> "Tarea eliminada";
            default -> action;
        };
        logAction("TASK", taskId, taskName, action, userId, userName, details);
    }
    
    public void logProjectAction(String projectId, String projectName, String action,
                                  String userId, String userName) {
        String details = switch (action) {
            case "CREATE" -> "Proyecto creado";
            case "UPDATE" -> "Proyecto actualizado";
            case "DELETE" -> "Proyecto eliminado";
            default -> action;
        };
        logAction("PROJECT", projectId, projectName, action, userId, userName, details);
    }
    
    public void logCommentAction(String commentId, String taskName, String action,
                                  String userId, String userName) {
        String details = switch (action) {
            case "CREATE" -> "Comentario agregado en: " + taskName;
            case "DELETE" -> "Comentario eliminado de: " + taskName;
            default -> action;
        };
        logAction("COMMENT", commentId, taskName, action, userId, userName, details);
    }
}
