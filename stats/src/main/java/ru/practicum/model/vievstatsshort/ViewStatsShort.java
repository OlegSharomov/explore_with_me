package ru.practicum.model.vievstatsshort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsShort implements ViewStatsShortInterface {
    @Schema(description = "URI сервиса", example = "/event/367")
    private String uri;
    @Schema(description = "Количество просмотров")
    private Long hits;
}
