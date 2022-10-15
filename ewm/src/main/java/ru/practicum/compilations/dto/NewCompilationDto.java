package ru.practicum.compilations.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import java.util.List;

// DTO подборки событий для создания
@Data
public class NewCompilationDto {
    @UniqueElements
    private List<Integer> events;           // Список идентификаторов событий входящих в подборку
    //    @Builder.Default
    private Boolean pinned = false;         // Закреплена ли подборка на главной странице сайта
    @NotBlank
    private String title;                   // Заголовок подборки
}
