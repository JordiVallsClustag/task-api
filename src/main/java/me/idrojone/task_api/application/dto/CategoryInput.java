package me.idrojone.task_api.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryInput(@NotBlank String name) {}
