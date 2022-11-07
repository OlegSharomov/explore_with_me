package ru.practicum.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatShort {
    private String uri;         // URI сервиса
    private Long hits;       // Количество просмотров

    public Long getIdFromUriEvent() {
        return Long.parseLong(uri.substring(7));
    }
}
