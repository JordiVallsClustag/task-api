package me.idrojone.task_api.application.mapper;

import me.idrojone.task_api.application.dto.TaskDto;
import me.idrojone.task_api.application.dto.TaskInput;
import me.idrojone.task_api.domain.model.Task;

public final class TaskMapper {
    private TaskMapper() {}
    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted(),
            task.getCategoryId()
        );
    }

    public static Task toEntity(TaskInput input) {
        if (input == null) {
            return null;
        }
        Task task = new Task();
        task.setTitle(input.title());
        task.setDescription(input.description());
        task.setCompleted(false);
        task.setCategoryId(input.categoryId());
        return task;
    }
}
