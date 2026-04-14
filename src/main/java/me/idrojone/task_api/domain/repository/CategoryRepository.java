package me.idrojone.task_api.domain.repository;

import java.util.List;
import java.util.Optional;

import me.idrojone.task_api.domain.model.Category;


public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(String id);
    Category save(Category category);
}
