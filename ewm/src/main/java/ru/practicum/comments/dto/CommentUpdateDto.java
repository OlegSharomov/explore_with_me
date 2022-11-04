package ru.practicum.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO категории для редактирования")
public class CommentUpdateDto {
    private Long id;
    private Long commentatorId;
    private Long eventId;
    private String text;
}
