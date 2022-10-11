package ru.practicum.requests.dto;

import ru.practicum.requests.RequestStatus;

import java.time.LocalDateTime;

public class ParticipationRequestDto {          // Заявка на участие в событии
    LocalDateTime created;          // Дата и время создания заявки
    Integer event;                  // Идентификатор события
    Integer id;                     // Идентификатор заявки
    Integer requester;              // Идентификатор пользователя, отправившего заявку
    RequestStatus status;           // Статус заявки.  PENDING, CONFIRMED, REJECTED
}
