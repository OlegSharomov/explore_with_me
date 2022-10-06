package ru.practicum.events.publ.dto;

import lombok.Builder;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.EventState;
import ru.practicum.events.Location;
import ru.practicum.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventFullDto {
    @NotNull String annotation;         // Краткое описание
    @NotNull CategoryDto category;      // Категория {"id": 1, "name": "Концерты"}
    Integer confirmedRequests;          // Количество одобренных заявок на участие в данном событии
    LocalDateTime createdOn;            // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    String description;                 // Полное описание события
    @NotNull LocalDateTime eventDate;   // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    Integer id;
    @NotNull UserShortDto initiator;    // Пользователь (краткая информация) {"id": 3, "name": "Фёдоров Матвей"}
    @NotNull Location location;         // Широта и долгота места проведения события {"lat": 55.754167, "lon": 37.62}
    @NotNull Boolean paid;              // Нужно ли оплачивать участие
    @Builder.Default
    Integer participantLimit = 0;       // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения.
    LocalDateTime publishedOn;          // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    Boolean requestModeration = true;   // Нужна ли пре-модерация заявок на участие. Дефолтно: true
    EventState state;                   // Список состояний жизненного цикла события. Enum: [PENDING, PUBLISHED, CANCELED]
    @NotNull String title;              // Заголовок
    Integer views;                      // Количество просмотрев события


}
