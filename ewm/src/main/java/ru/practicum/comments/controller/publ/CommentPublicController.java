package ru.practicum.comments.controller.publ;

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
public class CommentPublicController {
    private final CommentPublicService commentPublicService;

    // Просмотр всех комментариев события. Выводиться должны только опубликованные комментарии.
    @GetMapping("/comments/event/{eventId}")
    public List<CommentShortDto> getAllCommentsOfEvent(@Positive @PathVariable Long eventId,
                                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /comments/event/{} with parameters from = {}, size = {}", eventId, from, size);
        return commentPublicService.getAllCommentsOfEvent(eventId, from, size);
    }


    // Просмотр всех комментариев инициатора событий по всем его событиям
    @GetMapping("/comments/initiator/{initiatorId}")
    public List<CommentShortDto> getAllCommentsByInitiatorOfEvents(@PathVariable Long initiatorId,
                                                            /* Вариант сортировки: по дате комментария или по событиям
                                                                                 values : CREATED_ON, EVENTS */
                                                                   @RequestParam(defaultValue = "CREATED_ON") String sort,
                                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /comments/initiator/{} with parameters sort={}, from={}, size={}",
                initiatorId, sort, from, size);
        return commentPublicService.getAllCommentsByInitiatorOfEvents(initiatorId, sort, from, size);
    }

    // Просмотр события по id
    @GetMapping("/comments/{commentId}")
    public CommentFullDto getCommentById(@PathVariable Long commentId) {
        return commentPublicService.getCommentById(commentId);
    }
}
