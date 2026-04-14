package me.idrojone.task_api.domain.repository;

import java.util.List;
import java.util.Optional;

import me.idrojone.task_api.domain.model.Task;

public interface TaskRepository {
    List<Task> findAll();
    List<Task> findAll(int offset, int limit);
    List<Task> findByCategoryId(String categoryId);
    List<Task> findByCategoryId(String categoryId, int offset, int limit);
    Optional<Task> findById(String id);
    long count();
    long countByCategoryId(String categoryId);
    Task save(Task task);
    void deleteById(String id);
}
