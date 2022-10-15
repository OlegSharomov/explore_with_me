package ru.practicum.client;

import lombok.Data;

@Data
public class ViewStat {
    private String app;         // Название сервиса
    private String uri;         // URI сервиса
    private Integer hits;       // Количество просмотров
}
