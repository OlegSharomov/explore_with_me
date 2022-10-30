package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class UserShortDto {
    @NotNull
    Integer id;
    @NotBlank
    String name;
}
