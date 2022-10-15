package ru.practicum.users.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewUserRequest {
    @NotNull
    @Email
    private String email;       // Почтовый адрес
    @NotBlank
    private String name;        // Имя
}
