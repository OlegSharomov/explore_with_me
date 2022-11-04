package ru.practicum.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.comments.model.CommentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdminUpdateDto {
    private Long id;
    private Long commentatorId;
    private Long eventId;
    private CommentStatus status;
    private String text;
}
