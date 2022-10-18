package ru.practicum.events.model;

import ru.practicum.exception.ValidationException;

public enum EventState {
    PENDING,            // находящийся на рассмотрении
    PUBLISHED,          // Опубликованный
    CANCELED;            // Отмененный

    public static EventState getStateById(Integer id) {
        switch (id) {
            case 0:
                return PENDING;
            case 1:
                return PUBLISHED;
            case 2:
                return CANCELED;
            default:
                throw new ValidationException("Id of enum not found");
        }
    }
}
