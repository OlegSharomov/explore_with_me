package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.UniqueElements;
import ru.practicum.events.dto.publ.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class CompilationDto {       // Подборка событий
    @UniqueElements
    List<EventShortDto> events;     // Подборка событий
    @NotNull
    Integer id;            // Идентификатор
    @NotNull
    Boolean pinned;        // Закреплена ли подборка на главной странице сайта
    @NotBlank
    String title;         // Заголовок подборки
}
