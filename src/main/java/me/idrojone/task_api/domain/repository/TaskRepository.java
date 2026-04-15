package me.idrojone.task_api.domain.repository;

import java.util.List;
import java.util.Optional;

import me.idrojone.task_api.domain.model.Task;

public interface TaskRepository {
    List<Task> findAll();
    List<Task> findAll(int offset, int limit);
    List<Task> findAll(int offset, int limit, Boolean deleted);
    List<Task> findByCategoryId(String categoryId);
    List<Task> findByCategoryId(String categoryId, int offset, int limit);
    List<Task> findByCategoryId(String categoryId, int offset, int limit, Boolean deleted);
    Optional<Task> findById(String id);
    long count();
    long count(Boolean deleted);
    long countCompleted(Boolean deleted);
    long countByCategoryId(String categoryId);
    long countByCategoryId(String categoryId, Boolean deleted);
    long countCompletedByCategoryId(String categoryId, Boolean deleted);
    Task save(Task task);
    void deleteById(String id);
}
