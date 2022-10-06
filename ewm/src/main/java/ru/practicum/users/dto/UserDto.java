package ru.practicum.users.dto;

import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    @Email
    String email;           // Почтовый адрес
    @ReadOnlyProperty           // TODO Проверить работу аннотации @ReadOnlyProperty
    Integer id;             // Идентификатор
    @NotBlank
    String name;            // Имя
}
