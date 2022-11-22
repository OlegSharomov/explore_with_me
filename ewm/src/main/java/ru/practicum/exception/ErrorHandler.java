package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public ApiError handleNotFoundException(CustomNotFoundException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(HttpStatus.NOT_FOUND, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleValidationException(ValidationException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.FORBIDDEN, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST, e.getCause(), e.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(javax.validation.ConstraintViolationException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(Arrays.stream(e.getStackTrace()).collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(final org.hibernate.exception.ConstraintViolationException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(HttpStatus.CONFLICT, e.getCause(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleStatisticClientException(final StatisticSendingClientException e) {
        log.warn("{}\n{}\n{}", e, e.getMessage(), e.getStackTrace());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause(), e.getMessage(), LocalDateTime.now());
    }
}
