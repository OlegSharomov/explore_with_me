package ru.practicum.events.priv.dto;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class NewEventDto {
    @Length(min = 20, max = 2000)
    @NotNull
    String annotation;          // Краткое описание события
    @NotNull
    Integer category;           // id категории к которой относится событие
    @NotNull
    @Length(min = 20, max = 7000)
    String description;         // Полное описание события
    @NotNull
    LocalDateTime eventDate;    // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    //    @NotNull Location location;   // Широта и долгота места проведения события
    @Builder.Default
    Boolean paid = false;               // Нужно ли оплачивать участие в событии. Значение по умолчаниие = false
    @Builder.Default
    Integer participantLimit = 0;   // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Builder.Default
    Boolean requestModeration = true;   // Нужна ли пре-модерация заявок на участие. Если true,
    // то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
    @NotNull
    @Length(min = 3, max = 120)
    String title;               // Заголовок события
}
