package me.idrojone.task_api.application.service.task;

import me.idrojone.task_api.application.dto.task.TaskDto;
import me.idrojone.task_api.application.dto.task.TaskInput;
import me.idrojone.task_api.application.dto.task.TaskPage;

public interface TaskService {
    TaskPage getAllTasks(int offset, int limit);
    TaskPage getTasksByCategory(String categoryId, int offset, int limit);
    TaskDto getTaskById(String id);
    TaskDto createNewTask(TaskInput input);
    TaskDto toggleTaskStatus(String id);
    TaskDto updateTask(String id, TaskInput input);
}
