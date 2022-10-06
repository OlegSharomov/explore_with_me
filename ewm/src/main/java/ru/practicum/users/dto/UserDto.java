package ru.practicum.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    @Email
    String email;           // Почтовый адрес
    Integer id;             // Идентификатор
    @NotBlank
    String name;            // Имя
}
