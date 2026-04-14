package me.idrojone.task_api.application.service;

import java.util.List;

import me.idrojone.task_api.application.dto.TaskDto;
import me.idrojone.task_api.application.dto.TaskInput;

public interface TaskService {
    List<TaskDto> getAllTasks();
    List<TaskDto> getTasksByCategory(String categoryId);
    TaskDto getTaskById(String id);
    TaskDto createNewTask(TaskInput input);
    TaskDto toggleTaskStatus(String id);
    TaskDto updateTask(String id, TaskInput input);
}
