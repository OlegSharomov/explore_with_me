package ru.practicum.comments.controller.publ;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.service.publ.CommentPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Public.Комментарии", description = "Публичный API для работы с комментариями.")
public class CommentPublicController {
    private final CommentPublicService commentPublicService;

    @Operation(summary = "Просмотр всех комментариев события",
            description = "Выводиться должны только опубликованные комментарии")
    @GetMapping("/comments/event/{eventId}")
    public List<CommentShortDto> getAllCommentsOfEvent(@Positive @PathVariable Long eventId,
                                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /comments/event/{} with parameters from = {}, size = {}", eventId, from, size);
        return commentPublicService.getAllCommentsOfEvent(eventId, from, size);
    }

    @Operation(summary = "Просмотр всех комментариев инициатора событий по всем его событиям",
            description = "Выводиться должны только опубликованные комментарии")
    @GetMapping("/comments/initiator/{initiatorId}")
    public List<CommentShortDto> getAllCommentsByInitiatorOfEvents(@PathVariable Long initiatorId,
                                                                   @Parameter(name = "Вариант сортировки: " +
                                                                           "по дате комментария или по событиям",
                                                                           example = "CREATED_ON, EVENTS")
                                                                   @RequestParam(defaultValue = "CREATED_ON") String sort,
                                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /comments/initiator/{} with parameters sort={}, from={}, size={}",
                initiatorId, sort, from, size);
        return commentPublicService.getAllCommentsByInitiatorOfEvents(initiatorId, sort, from, size);
    }

    @Operation(summary = "Просмотр комментария по id")
    @GetMapping("/comments/{commentId}")
    public CommentFullDto getCommentById(@PathVariable Long commentId) {
        log.info("Received a request: GET /comments/{}", commentId);
        return commentPublicService.getCommentById(commentId);
    }
}
