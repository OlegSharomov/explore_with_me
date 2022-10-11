package ru.practicum.events.dto.priv;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class UpdateEventRequest {               // Данные для изменения информации о событии
    @Length(min = 20, max = 2000)
    String annotation;          // Новая аннотация
    @Positive
    Integer category;           // Новая категория
    @Length(min = 20, max = 7000)
    String description;         // Новое описание
    LocalDateTime eventDate;    // Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotNull
    @Positive
    Integer eventId;            // Идентификатор события
    Boolean paid;               // Новое значение флага о платности мероприятия
    @Positive
    Integer participantLimit;   // Новый лимит пользователей
    @Length(min = 3, max = 120)
    String title;               // Новый заголовок
}
