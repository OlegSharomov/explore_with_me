package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import java.util.List;

// DTO подборки событий для создания
@Data
@Builder
public class NewCompilationDto {
    @UniqueElements
    List<Integer> events;           // Список идентификаторов событий входящих в подборку
    @Builder.Default
    Boolean pinned = false;         // Закреплена ли подборка на главной странице сайта
    @NotBlank
    String title;                   // Заголовок подборки
}
