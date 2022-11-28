package ru.practicum.model.viewstats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NotNull
public class ViewStats implements ViewStatsInterface {
    @Schema(description = "Название сервиса", example = "ewm-main-service")
    private String app;
    @Schema(description = "URI сервиса", example = "/event/367")
    private String uri;
    @Schema(description = "Количество просмотров")
    private Long hits;
}
