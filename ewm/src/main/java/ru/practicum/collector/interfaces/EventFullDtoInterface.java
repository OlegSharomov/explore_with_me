package ru.practicum.collector.interfaces;

public interface EventFullDtoInterface {
    Long getCategoryId();

    String getCategoryName();

    Long getConfirmedRequests();

    Long getId();

    Long getInitiatorId();

    String getInitiatorName();
}
