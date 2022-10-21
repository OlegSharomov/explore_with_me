package ru.practicum.users.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class UserDto {
    @NotNull
    @Email
    String email;
    Integer id;
    @NotBlank
    String name;
}
