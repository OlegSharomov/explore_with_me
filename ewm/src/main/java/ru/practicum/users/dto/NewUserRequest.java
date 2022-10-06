package ru.practicum.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewUserRequest {
    @NotNull
    @Email
    String email;       // Почтовый адрес
    @NotBlank
    String name;        // Имя
}
