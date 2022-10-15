package ru.practicum.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ViewStats {
    String app;         // Название сервиса
    String uri;         // URI сервиса
    Integer hits;       // Количество просмотров
}
