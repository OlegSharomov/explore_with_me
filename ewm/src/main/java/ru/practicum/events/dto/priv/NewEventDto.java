package ru.practicum.events.dto.priv;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.events.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @NotNull
    @Length(min = 20, max = 2000)
    private String annotation;              // Краткое описание события
    @NotNull
    @Positive
    private Integer category;               // id категории к которой относится событие
    @NotNull
    @Length(min = 20, max = 7000)
    private String description;             // Полное описание события
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;        // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotNull
    private Location location;              // Широта и долгота места проведения события
    //    @Builder.Default
    private Boolean paid = false;           // Нужно ли оплачивать участие в событии. Значение по умолчанию = false
    //    @Builder.Default
    @Positive
    private Integer participantLimit = 0;   // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    //    @Builder.Default
    private Boolean requestModeration = true; /* Нужна ли пре-модерация заявок на участие. Если true,
     то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически. */
    @NotNull
    @Length(min = 3, max = 120)
    private String title;                   // Заголовок события
}
