package ru.practicum.collector.interfaces;

public interface EventShortDtoInterfaceWithAllFields {
    String getAnnotation();

    Long getCategoryId();

    String getCategoryName();

    Long getConfirmedRequests();

    java.time.LocalDateTime getEventDate();

    Long getId();

    Long getInitiatorId();

    String getInitiatorName();

    Boolean getPaid();

    String getTitle();
}
