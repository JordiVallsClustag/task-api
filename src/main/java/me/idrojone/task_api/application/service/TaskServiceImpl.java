package me.idrojone.task_api.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.idrojone.task_api.application.dto.TaskDto;
import me.idrojone.task_api.application.dto.TaskInput;
import me.idrojone.task_api.application.mapper.TaskMapper;
import me.idrojone.task_api.domain.exception.NotFoundException;
import me.idrojone.task_api.domain.model.Task;
import me.idrojone.task_api.domain.repository.TaskRepository;
import me.idrojone.task_api.domain.repository.CategoryRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByCategory(String categoryId) {
        return taskRepository.findByCategoryId(categoryId).stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(String id) {
        final Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tarea no encontrada con ID: " + id));
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskDto createNewTask(TaskInput input) {
        final Task taskToCreate = TaskMapper.toEntity(input);
        if (taskToCreate.getCategoryId() != null) {
            final String catId = taskToCreate.getCategoryId();
            categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada con ID: " + catId));
        }
        final Task savedTask = taskRepository.save(taskToCreate);
        return TaskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto toggleTaskStatus(String id) {
        final Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tarea no encontrada con ID: " + id));
        task.setCompleted(!task.isCompleted());
        final Task savedTask = taskRepository.save(task);
        return TaskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto updateTask(String id, TaskInput input) {
        final Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tarea no encontrada con ID: " + id));

        if (input.title() != null) {
            task.setTitle(input.title());
        }
        task.setDescription(input.description());

        final String newCategoryId = input.categoryId();
        if (newCategoryId != null) {
            // ensure category exists
            categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada con ID: " + newCategoryId));
            task.setCategoryId(newCategoryId);
        } else {
            task.setCategoryId(null);
        }

        final Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }
}
