package com.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntry {
    @Id
    private String id;
    
    private String entityType; // "TASK", "PROJECT", "USER", "COMMENT"
    
    private String entityId;
    
    private String entityName;
    
    private String action; // "CREATE", "UPDATE", "DELETE"
    
    private String userId;
    
    private String userName;
    
    private LocalDateTime timestamp;
    
    private String details;
    
    public HistoryEntry(String entityType, String entityId, String entityName, 
                        String action, String userId, String userName, String details) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityName = entityName;
        this.action = action;
        this.userId = userId;
        this.userName = userName;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
