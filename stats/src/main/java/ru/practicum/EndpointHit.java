package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EndpointHit {
    private Integer id;         // Идентификатор записи
    private String app;         // Идентификатор сервиса для которого записывается информация
    private String uri;         // URI для которого был осуществлен запрос
    private String ip;          // IP-адрес пользователя, осуществившего запрос
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;    // Дата и время, когда был совершен/ запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
