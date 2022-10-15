package ru.practicum.events.dto.publ;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.model.EventState;
import ru.practicum.events.model.Location;
import ru.practicum.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
//@Builder
public class EventFullDto {
    @NotNull
    private String annotation;              // Краткое описание
    @NotNull
    private CategoryDto category;           // Категория {"id": 1, "name": "Концерты"}
    private Integer confirmedRequests;      // Количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;        // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description;             // Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime eventDate;        // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Integer id;
    @NotNull
    private UserShortDto initiator;         // Пользователь (краткая информация) {"id": 3, "name": "Фёдоров Матвей"}
    @NotNull
    private Location location;              // Широта и долгота места проведения события {"lat": 55.754167, "lon": 37.62}
    @NotNull
    private Boolean paid;                   // Нужно ли оплачивать участие
    //    @Builder.Default
    private Integer participantLimit = 0;   // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;      // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    //    @Builder.Default
    private Boolean requestModeration = true; // Нужна ли пре-модерация заявок на участие. Дефолтно: true
    private EventState state;               // Список состояний жизненного цикла события. [PENDING, PUBLISHED, CANCELED]
    @NotNull
    private String title;                   // Заголовок
    private Integer views;                  // Количество просмотрев события

    /* В отличие от NewEventDto у FullEventDto есть поля:
    Integer confirmedRequests;          // Количество одобренных заявок на участие в данном событии
    LocalDateTime createdOn;            // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    Integer id;
    @NotNull UserShortDto initiator;    // Пользователь (краткая информация) {"id": 3, "name": "Фёдоров Матвей"}
    LocalDateTime publishedOn;          // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    EventState state;                   // Список состояний жизненного цикла события. [PENDING, PUBLISHED, CANCELED]
    Integer views;                      // Количество просмотрев события
    */
}
