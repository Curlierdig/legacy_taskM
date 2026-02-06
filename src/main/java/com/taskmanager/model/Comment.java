package com.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;
    
    @NotBlank(message = "El ID de tarea es obligatorio")
    private String taskId;
    
    private String userId;
    
    private String userName;
    
    @NotBlank(message = "El contenido es obligatorio")
    private String content;
    
    private LocalDateTime createdAt;
    
    public Comment(String taskId, String userId, String userName, String content) {
        this.taskId = taskId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
