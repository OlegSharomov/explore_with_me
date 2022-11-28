package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(HttpStatus.NOT_FOUND, e, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST, e.getCause(), e.getMessage(), LocalDateTime.now());
    }
}
