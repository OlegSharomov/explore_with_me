package ru.practicum.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    List<String> errors;            // Список стектрейсов или описания ошибок
    String message;                 // Сообщение об ошибке
    String reason;                  // Общее описание причины ошибки
    String status;                  // !!!ENUM!!!  Код статуса HTTP-ответа
    LocalDateTime timestamp;        // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
}
