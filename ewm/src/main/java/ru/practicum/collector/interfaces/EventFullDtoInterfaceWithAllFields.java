package ru.practicum.collector.interfaces;

public interface EventFullDtoInterfaceWithAllFields {
    String getAnnotation();

    Long getCategoryId();

    String getCategoryName();

    Long getConfirmedRequests();

    java.time.LocalDateTime getCreatedOn();

    String getDescription();

    java.time.LocalDateTime getEventDate();

    Long getId();

    Long getInitiatorId();

    String getInitiatorName();

    Float getLocationLat();

    Float getLocationLon();

    Boolean getPaid();

    Long getParticipantLimit();

    java.time.LocalDateTime getPublishedOn();

    Boolean getRequestModeration();

    ru.practicum.events.model.EventState getState();

    String getTitle();
}
