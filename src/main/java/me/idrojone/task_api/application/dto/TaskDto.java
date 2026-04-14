package me.idrojone.task_api.application.dto;

public record TaskDto(String id, String title, String description, boolean completed, String categoryId) {}
