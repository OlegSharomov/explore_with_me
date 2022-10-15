package ru.practicum.users.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class UserShortDto {
    @NotNull
    Integer id;
    @NotBlank
    String name;

}
