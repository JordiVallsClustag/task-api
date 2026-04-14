package me.idrojone.task_api.application.service.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import me.idrojone.task_api.application.dto.PageInfo;
import me.idrojone.task_api.application.dto.task.TaskDto;
import me.idrojone.task_api.application.dto.task.TaskInput;
import me.idrojone.task_api.application.dto.task.TaskPage;
import me.idrojone.task_api.application.mapper.TaskMapper;
import me.idrojone.task_api.domain.exception.NotFoundException;
import me.idrojone.task_api.domain.model.Task;
import me.idrojone.task_api.domain.repository.CategoryRepository;
import me.idrojone.task_api.domain.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TaskPage getAllTasks(int offset, int limit) {
        List<Task> tasks = taskRepository.findAll(offset, limit);
        int total = (int) taskRepository.count();
        List<TaskDto> items = tasks.stream().map(TaskMapper::toDto).collect(Collectors.toList());
        boolean hasNext = offset + items.size() < total;
        boolean hasPrevious = offset > 0;
        PageInfo pageInfo = new PageInfo(offset, limit, total, hasNext, hasPrevious);
        return new TaskPage(items, pageInfo);
    }

    @Override
    public TaskPage getTasksByCategory(String categoryId, int offset, int limit) {
        List<Task> tasks = taskRepository.findByCategoryId(categoryId, offset, limit);
        int total = (int) taskRepository.countByCategoryId(categoryId);
        List<TaskDto> items = tasks.stream().map(TaskMapper::toDto).collect(Collectors.toList());
        boolean hasNext = offset + items.size() < total;
        boolean hasPrevious = offset > 0;
        PageInfo pageInfo = new PageInfo(offset, limit, total, hasNext, hasPrevious);
        return new TaskPage(items, pageInfo);
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
