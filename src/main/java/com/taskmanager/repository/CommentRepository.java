package com.taskmanager.repository;

import com.taskmanager.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByTaskIdOrderByCreatedAtDesc(String taskId);
    List<Comment> findAllByOrderByCreatedAtDesc();
    long countByTaskId(String taskId);
}
