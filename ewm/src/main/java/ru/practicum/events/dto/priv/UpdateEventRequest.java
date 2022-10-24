package ru.practicum.events.dto.priv;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {               // Данные для изменения информации о событии
    @Length(min = 20, max = 2000)
    private String annotation;          // Новая аннотация
    @Positive
    private Integer category;           // Новая категория
    @Length(min = 20, max = 7000)
    private String description;         // Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;    // Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotNull
    @Positive
    private Integer eventId;            // Идентификатор события
    private Boolean paid;               // Новое значение флага о платности мероприятия
    @Positive
    private Integer participantLimit;   // Новый лимит пользователей
    @Length(min = 3, max = 120)
    private String title;               // Новый заголовок
}
