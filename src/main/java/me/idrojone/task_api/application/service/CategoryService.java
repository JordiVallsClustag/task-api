package me.idrojone.task_api.application.service;

import java.util.List;

import me.idrojone.task_api.application.dto.CategoryDto;
import me.idrojone.task_api.application.dto.CategoryInput;
import me.idrojone.task_api.application.dto.CategoryUpdateInput;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(String id);
    CategoryDto createNewCategory(CategoryInput input);
    CategoryDto updateCategory(String id, CategoryUpdateInput input);
}
