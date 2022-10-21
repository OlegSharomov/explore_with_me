package ru.practicum.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    private List<String> errors;            // Список стектрейсов или описания ошибок
    private String message;                 // Сообщение об ошибке
    private String reason;                  // Общее описание причины ошибки
    private String status;                  // !!!ENUM!!!  Код статуса HTTP-ответа
    private LocalDateTime timestamp;        // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
}
