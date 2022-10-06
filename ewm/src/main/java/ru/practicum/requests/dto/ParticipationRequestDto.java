package ru.practicum.requests.dto;

import java.time.LocalDateTime;

public class ParticipationRequestDto {          // Заявка на участие в событии
    LocalDateTime created;          // Дата и время создания заявки
    Integer event;                  // Идентификатор события
    Integer id;                     // Идентификатор заявки
    Integer requester;              // Идентификатор пользователя, отправившего заявку
    String status;                  // Статус заявки. !!! Возможно использовать Enum!!! TODO Проверить возможность использования Enum
}
