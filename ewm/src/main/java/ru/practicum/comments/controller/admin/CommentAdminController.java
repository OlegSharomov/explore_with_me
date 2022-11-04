package ru.practicum.comments.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.CommentAdminUpdateDto;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.service.admin.CommentAdminService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Admin. Комментарии", description = "API для работы с комментариями событий. Только для администраторов.")
public class CommentAdminController {
    private final CommentAdminService commentAdminService;

    @Operation(summary = "Редактирование комментария",
            description = "Редактирование данных любого события администратором. Валидация данных не требуется")
    @PatchMapping("/admin/comments/{commentId}")
    public CommentFullDto updateCommentByAdmin(@PathVariable Long commentId,
                                               @Parameter(name = "DTO для редактирования комментария")
                                               @RequestBody CommentAdminUpdateDto commentAdminUpdateDto) {
        log.info("Received a request: PATCH /admin/comments/{} with body: {}", commentId, commentAdminUpdateDto);
        return commentAdminService.updateCommentByAdmin(commentId, commentAdminUpdateDto);
    }

    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/admin/comments/{commentId}")
    public void removeCommentById(@PathVariable Long commentId) {
        log.info("Received a request: DELETE /admin/comments/{}", commentId);
        commentAdminService.removeCommentById(commentId);
    }

    @Operation(summary = "Просмотр всех стоп-слов")
    @GetMapping("/admin/comments/censorship")
    public String[] showAllStopWords() {
        log.info("Received a request: GET /admin/comments/censorship");
        return commentAdminService.showAllStopWords();
    }

    @Operation(summary = "Добавление стоп-слова")
    @PostMapping("/admin/comments/censorship")
    public String[] addStopWord(@RequestParam String stopWord) {
        log.info("Received a request: POST /admin/comments/censorship");
        return commentAdminService.addStopWord(stopWord);
    }

    @Operation(summary = "Удаление стоп слова")
    @DeleteMapping("/admin/comments/censorship")
    public String[] removeStopWord(@RequestParam String stopWord) {
        log.info("Received a request: DELETE /admin/comments/censorship");
        return commentAdminService.removeStopWord(stopWord);
    }
}
