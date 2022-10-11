package ru.practicum.users.dto;

import lombok.Value;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class UserDto {
    @NotNull
    @Email
    String email;
    @ReadOnlyProperty           // TODO Проверить работу аннотации @ReadOnlyProperty
    Integer id;
    @NotBlank
    String name;
}
