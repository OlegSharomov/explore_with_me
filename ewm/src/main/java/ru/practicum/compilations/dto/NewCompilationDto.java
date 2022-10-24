package ru.practicum.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import java.util.List;

// DTO подборки событий для создания
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @UniqueElements
    private List<Integer> events;           // Список идентификаторов событий входящих в подборку
    private Boolean pinned = false;         // Закреплена ли подборка на главной странице сайта
    @NotBlank
    private String title;                   // Заголовок подборки
}
