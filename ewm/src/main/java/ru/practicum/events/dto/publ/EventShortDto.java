package ru.practicum.events.dto.publ;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventShortDto {
    @NotNull String annotation;         // Краткое описание
    @NotNull CategoryDto category;      // Категория {"id": 1, "name": "Концерты"}
    Integer confirmedRequests;          // Количество одобренных заявок на участие в данном событии
    @NotNull LocalDateTime eventDate;   // дата начала события формате "yyyy-MM-dd HH:mm:ss"
    Integer id;
    @NotNull UserShortDto initiator;    // Пользователь (краткая информация) {"id": 3, "name": "Фёдоров Матвей"}
    @NotNull Boolean paid;              // Нужно ли оплачивать участие
    @NotNull String title;              // Заголовок
    Integer views;                      // Количество просмотрев события

}
