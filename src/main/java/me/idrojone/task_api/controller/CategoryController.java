package me.idrojone.task_api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import me.idrojone.task_api.application.dto.CategoryDto;
import me.idrojone.task_api.application.dto.CategoryInput;
import me.idrojone.task_api.application.dto.CategoryUpdateInput;
import me.idrojone.task_api.application.service.CategoryService;

@Controller
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<CategoryDto> categories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public CategoryDto categoryById(@Argument String id) {
        return categoryService.getCategoryById(id);
    }

    @MutationMapping
    public CategoryDto createCategory(@Argument @Valid CategoryInput input) {
        return categoryService.createNewCategory(input);
    }

    @MutationMapping
    public CategoryDto updateCategory(@Argument String id, @Argument CategoryUpdateInput input) {
        return categoryService.updateCategory(id, input);
    }
}
