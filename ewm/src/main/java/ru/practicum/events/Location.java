package ru.practicum.events;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Location {             // Широта и долгота места проведения события
    Float lat;          // Широта
    Float lon;          // Долгота
}
