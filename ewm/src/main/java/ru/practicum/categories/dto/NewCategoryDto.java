package ru.practicum.categories.dto;

import javax.validation.constraints.NotBlank;

public class NewCategoryDto {
    @NotBlank String name;       // Название категории
}
