package com.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    private String id;
    
    private String userId;
    
    private String type; // "TASK_ASSIGNED", "DUE_SOON", "COMMENT_ADDED", "TASK_COMPLETED"
    
    private String title;
    
    private String message;
    
    private boolean read;
    
    private LocalDateTime createdAt;
    
    private String relatedEntityId;
    
    private String relatedEntityType;
    
    public Notification(String userId, String type, String title, String message) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.read = false;
        this.createdAt = LocalDateTime.now();
    }
    
    public Notification(String userId, String type, String title, String message, 
                        String relatedEntityId, String relatedEntityType) {
        this(userId, type, title, message);
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }
}
