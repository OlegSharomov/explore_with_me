package ru.practicum.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.comments.model.CommentStatus;

import javax.validation.constraints.Max;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO категории. Текст комментария выводится в укороченном виде. Не более 150 символов")
public class CommentShortDto {
    private Long id;
    private Long commentatorId;
    private Long eventId;
    @Max(150)
    private String text;
    private CommentStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

}
