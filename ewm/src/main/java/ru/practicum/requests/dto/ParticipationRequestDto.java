package ru.practicum.requests.dto;

import lombok.Data;
import ru.practicum.requests.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {          // Заявка на участие в событии
    private LocalDateTime created;          // Дата и время создания заявки
    private Integer event;                  // Идентификатор события
    private Integer id;                     // Идентификатор заявки
    private Integer requester;              // Идентификатор пользователя, отправившего заявку
    private RequestStatus status;           // Статус заявки.  PENDING, CONFIRMED, REJECTED
}
