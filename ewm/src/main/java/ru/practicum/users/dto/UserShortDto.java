package ru.practicum.users.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserShortDto {
    @NotNull Integer id;        // Идентификатор
    @NotBlank String name;      // Имя

}
