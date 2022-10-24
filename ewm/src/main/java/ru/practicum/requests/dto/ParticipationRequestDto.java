package ru.practicum.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.requests.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {          // Заявка на участие в событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;          // Дата и время создания заявки
    private Integer event;                  // Идентификатор события
    private Integer id;                     // Идентификатор заявки
    private Integer requester;              // Идентификатор пользователя, отправившего заявку
    private RequestStatus status;           // Статус заявки.  PENDING, CONFIRMED, REJECTED
}
