package me.idrojone.task_api.application.dto.task;

import java.util.List;

import me.idrojone.task_api.application.dto.PageInfo;

public record TaskPage(List<TaskDto> items, PageInfo pageInfo) {}
