package ru.practicum.events.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Location {             // Широта и долгота места проведения события
    private Float lat;          // Широта
    private Float lon;          // Долгота
}
