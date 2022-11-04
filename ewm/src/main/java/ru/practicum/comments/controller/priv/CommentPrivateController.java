package ru.practicum.comments.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentUpdateDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.service.priv.CommentPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CommentPrivateController {

    private final CommentPrivateService commentPrivateService;

    //+
    @PostMapping("/users/{userId}/comments")
    // Добавление нового комментария.
    // Должна производиться проверка на корректность введенных данных.
    // Пользователь может оставить только по одному комментарию для каждого события
    public CommentFullDto createComment(@Positive @PathVariable Long userId,
                                        @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Received a request: POST /users/{}/comments with body: {}", userId, newCommentDto);
        return commentPrivateService.createComment(userId, newCommentDto);
    }

    //+
    // Редактирование комментария.
    // Должна производиться проверка на корректность введенных данных.
    // Произвести проверку на существование id, принадлежность eventId и commentatorId к текущему пользователю
    @PatchMapping("/users/{userId}/comments")
    public CommentFullDto updateComment(@Positive @PathVariable Long userId,
                                        @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        log.info("Received a request: PATCH /users/{}/comments with body: {}", userId, commentUpdateDto);
        return commentPrivateService.updateComment(userId, commentUpdateDto);
    }

    //+
    // Удаление комментария по id. Пользователь может удалить только свой комментарий.
    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public void removeCommentById(@Positive @PathVariable Long userId,
                                  @Positive @PathVariable Long commentId) {
        log.info("Received a request: DELETE /users/{}/comments/{}", userId, commentId);
        commentPrivateService.removeCommentById(userId, commentId);
    }

    //+
    // Просмотр всех комментариев текущего пользователя
    @GetMapping("/users/{userId}/comments")
    public List<CommentFullDto> getAllCommentsOfCurrentUser(@Positive @PathVariable Long userId) {
        log.info("Received a request: GET /users/{}/comments", userId);
        return commentPrivateService.getAllCommentsOfCurrentUser(userId);
    }
}
