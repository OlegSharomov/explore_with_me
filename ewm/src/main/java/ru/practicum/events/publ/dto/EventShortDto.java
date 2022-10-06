package ru.practicum.events.publ.dto;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventShortDto {
    @NotNull String annotation;              // Краткое описание
    //@NotNull CategoryResponse category;   // {"id": 1, "name": "Концерты"}
    Integer confirmedRequests;         // Количество одобренных заявок на участие в данном событии
    @NotNull LocalDateTime eventDate;        // дата начала события формате "yyyy-MM-dd HH:mm:ss"
    Long id;
    //@NotNull  User initiator;              // {"id": 3, "name": "Фёдоров Матвей"}
    @NotNull Boolean paid;                   // Нужно ли оплачивать участие
    @NotNull String title;                   // Заголовок
    Integer views;                     // Количество просмотрев события

}
