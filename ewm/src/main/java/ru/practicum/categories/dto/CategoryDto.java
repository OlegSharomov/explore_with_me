package ru.practicum.categories.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    @NotNull
    private Integer id;     // Идентификатор категории
    @NotBlank
    private String name;    // Название категории
}
