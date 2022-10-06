package ru.practicum.compilations.publ.dto;

import ru.practicum.events.publ.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CompilationDto {
    List<EventShortDto> events;     // Подборка событий
    @NotNull Integer id;            // Идентификатор
    @NotNull Boolean pinned;        // Закреплена ли подборка на главной странице сайта
    @NotBlank String title;         // Заголовок подборки
}
