package com.taskmanager.repository;

import com.taskmanager.model.HistoryEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryEntry, String> {
    List<HistoryEntry> findAllByOrderByTimestampDesc();
    List<HistoryEntry> findByEntityTypeOrderByTimestampDesc(String entityType);
    List<HistoryEntry> findByUserIdOrderByTimestampDesc(String userId);
    List<HistoryEntry> findTop50ByOrderByTimestampDesc();
}
