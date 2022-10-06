package ru.practicum.events.admin.dto;

import java.time.LocalDateTime;

// Информация для редактирования события администратором. Все поля необязательные. Значение полей не валидируется.
public class AdminUpdateEventRequest {
    String annotation;          // Краткое описание события
    Integer category;           // id категории к которой относится событие
    String description;         // Полное описание события
    LocalDateTime eventDate;    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
//    Location location;        // Широта и долгота места проведения события
    Boolean paid;               // Нужно ли оплачивать участие в событии
    Integer participantLimit;   // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Boolean requestModeration;  // Нужна ли пре-модерация заявок на участие
    String title;               // Заголовок события
}
