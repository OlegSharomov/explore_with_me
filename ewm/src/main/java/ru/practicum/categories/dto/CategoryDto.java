package ru.practicum.categories.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CategoryDto {
    @NotNull
    Integer id;     // Идентификатор категории
    @NotBlank
    String name;    // Название категории
}
