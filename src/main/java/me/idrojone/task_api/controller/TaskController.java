package me.idrojone.task_api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import me.idrojone.task_api.application.dto.TaskDto;
import me.idrojone.task_api.application.dto.TaskInput;
import me.idrojone.task_api.application.dto.CategoryDto;
import me.idrojone.task_api.application.service.TaskService;
import me.idrojone.task_api.application.service.CategoryService;

@Controller
@Validated
public class TaskController {
    private final TaskService taskService;
    private final CategoryService categoryService;

    public TaskController(TaskService taskService, CategoryService categoryService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<TaskDto> findAllTasks(@Argument("categoryId") String categoryId) {
        if (categoryId == null) return taskService.getAllTasks();
        return taskService.getTasksByCategory(categoryId);
    }

    @SchemaMapping(typeName = "Task", field = "category")
    public CategoryDto category(TaskDto task) {
        if (task == null || task.categoryId() == null) return null;
        return categoryService.getCategoryById(task.categoryId());
    }

    @QueryMapping
    public TaskDto taskById(@Argument String id) {
        return taskService.getTaskById(id);
    }

    @MutationMapping
    public TaskDto createTask(@Argument @Valid TaskInput input) {
        return taskService.createNewTask(input);
    }

    @MutationMapping
    public TaskDto toggleTask(@Argument String id) {
        return taskService.toggleTaskStatus(id);
    }

    @MutationMapping
    public TaskDto updateTask(@Argument String id, @Argument @Valid TaskInput input) {
        return taskService.updateTask(id, input);
    }
}
