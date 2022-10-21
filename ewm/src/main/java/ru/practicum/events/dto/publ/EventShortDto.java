package ru.practicum.events.dto.publ;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EventShortDto {
    @NotNull
    private String annotation;         // Краткое описание
    @NotNull
    private CategoryDto category;      // Категория {"id": 1, "name": "Концерты"}
    private Integer confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;   // дата начала события формате "yyyy-MM-dd HH:mm:ss"
    private Integer id;
    @NotNull
    private UserShortDto initiator;    // Пользователь (краткая информация) {"id": 3, "name": "Фёдоров Матвей"}
    @NotNull
    private Boolean paid;              // Нужно ли оплачивать участие
    @NotNull
    private String title;              // Заголовок
    private Integer views;             // Количество просмотрев события
}
