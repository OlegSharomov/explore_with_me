package ru.practicum.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
//    Exception[] errors;
    String message;
    String reason;
    String status;  // возможно enum
    LocalDateTime timestamp;
}
