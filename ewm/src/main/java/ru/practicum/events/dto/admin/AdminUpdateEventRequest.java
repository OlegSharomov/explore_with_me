package ru.practicum.events.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.events.model.Location;

import java.time.LocalDateTime;

// Информация для редактирования события администратором. Все поля необязательные. Значение полей не валидируются.
@Data
public class AdminUpdateEventRequest {
    private String annotation;          // Краткое описание события
    private Integer category;           // id категории к которой относится событие
    private String description;         // Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Location location;          // Широта и долгота места проведения события
    private Boolean paid;               // Нужно ли оплачивать участие в событии
    private Integer participantLimit;   // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Boolean requestModeration = false;  // Нужна ли пре-модерация заявок на участие
    private String title;               // Заголовок события
    /* В отличии от EVENT Нет:
    id,
    initiator,
    state,
    createdOn,
    publishedOn,
    compilations
     */

}
