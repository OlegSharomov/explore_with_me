package ru.practicum.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.comments.model.CommentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация для редактирования комментария администратором. " +
        "Все поля необязательные. Значение полей не валидируются")
public class CommentAdminUpdateDto {
    private Long id;
    private Long commentatorId;
    private Long eventId;
    private CommentStatus status;
    private String text;
}
