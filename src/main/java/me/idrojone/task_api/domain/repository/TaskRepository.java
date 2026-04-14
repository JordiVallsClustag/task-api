package me.idrojone.task_api.domain.repository;

import java.util.List;
import java.util.Optional;

import me.idrojone.task_api.domain.model.Task;

public interface TaskRepository {
    List<Task> findAll();
    List<Task> findByCategoryId(String categoryId);
    Optional<Task> findById(String id);
    Task save(Task task);
}
