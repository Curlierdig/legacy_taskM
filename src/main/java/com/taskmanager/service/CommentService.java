package com.taskmanager.service;

import com.taskmanager.model.Comment;
import com.taskmanager.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    public List<Comment> getAllComments() {
        return commentRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<Comment> getCommentsByTaskId(String taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId);
    }
    
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }
    
    public Comment saveComment(Comment comment) {
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());
        }
        return commentRepository.save(comment);
    }
    
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
    
    public long getCommentCountByTaskId(String taskId) {
        return commentRepository.countByTaskId(taskId);
    }
}
