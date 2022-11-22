package ru.practicum.events.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.events.entity.Event;

import java.util.List;

@Repository
public interface CustomEventRepository {
    List<Event> findAllEvents(List<Long> list);

    Event findEventById(Long eventId);

    Event findEventByIdWithoutRelatedFields(Long eventId);

    Event findEventByIdWithThrowsNoResultException(Long eventId);

    List<Event> getListEventsByInitiatorId(Long initiatorId, int from, int size);
}
