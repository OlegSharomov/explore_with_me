package ru.practicum.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    public ApiError(List<StackTraceElement> errors, HttpStatus status, Throwable reason, String message, LocalDateTime timestamp) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiError(HttpStatus status, Throwable reason, String message, LocalDateTime timestamp) {
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    private List<StackTraceElement> errors;             // Список стектрейсов или описания ошибок
    private final String message;                       // Сообщение об ошибке
    private final Throwable reason;                     // Общее описание причины ошибки
    private final HttpStatus status;                    // !!!ENUM!!!  Код статуса HTTP-ответа
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;        // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
}
    /* {
  "status": "NOT_FOUND",
  "reason": "The required object was not found.",
  "message": "Event with id=21 was not found.",
  "timestamp": "2022-09-07 09:10:50"
} */