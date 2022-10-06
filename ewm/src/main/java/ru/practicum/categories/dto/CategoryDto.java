package ru.practicum.categories.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryDto {
    @NotNull
    Integer id;     // Идентификатор категории
    @NotBlank
    String name;    // Название категории
}
