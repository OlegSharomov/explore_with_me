package ru.practicum.comments.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.CommentAdminUpdateDto;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.service.admin.CommentAdminService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentAdminController {
    private final CommentAdminService commentAdminService;

    // Редактирование комментария.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    @PatchMapping("/admin/comments/{commentId}")
    public CommentFullDto updateCommentByAdmin(@PathVariable Long commentId,
                                               @RequestBody CommentAdminUpdateDto commentAdminUpdateDto) {
        log.info("Received a request: PATCH /admin/comments/{} with body: {}", commentId, commentAdminUpdateDto);
        return commentAdminService.updateCommentByAdmin(commentId, commentAdminUpdateDto);
    }

    // Удаление комментария
    @DeleteMapping("/admin/comments/{commentId}")
    public void removeCommentById(@PathVariable Long commentId) {
        log.info("Received a request: DELETE /admin/comments/{}", commentId);
        commentAdminService.removeCommentById(commentId);
    }

    // Посмотреть все стоп-слова
    @GetMapping("/admin/comments/censorship")
    public String[] showAllStopWords() {
        return commentAdminService.showAllStopWords();
    }

    // добавить стоп-слово
    @PutMapping("/admin/comments/censorship")
    public String[] addStopWord(@RequestParam String stopWord) {
        return commentAdminService.addStopWord(stopWord);
    }

    // удалить стоп слово
    @DeleteMapping("/admin/comments/censorship")
    public String[] removeStopWord(@RequestParam String stopWord) {
        return commentAdminService.removeStopWord(stopWord);
    }


}
