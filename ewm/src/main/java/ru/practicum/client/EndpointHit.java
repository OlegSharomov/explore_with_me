package ru.practicum.client;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EndpointHit {
    Integer id;         // Идентификатор записи
    String app;         // Идентификатор сервиса для которого записывается информация
    String uri;         // URI для которого был осуществлен запрос
    String ip;          // IP-адрес пользователя, осуществившего запрос
    LocalDateTime timestamp;    // Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
