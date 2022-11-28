package ru.practicum.events.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.events.entity.Event;
import ru.practicum.exception.CustomNotFoundException;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final EntityManager em;

    @Override
    public List<Event> findAllEvents(List<Long> list) {
        return em.createQuery("SELECT ev " +
                                "FROM Event AS ev " +
                                "LEFT JOIN FETCH ev.category AS cat " +
                                "LEFT JOIN FETCH ev.initiator AS init " +
                                "WHERE ev.id IN ?1",
                        Event.class)
                .setParameter(1, list)
                .getResultList();
    }

    @Override
    public Event findEventById(Long eventId) {
        try {
            return em.createQuery("SELECT ev " +
                                    "FROM Event AS ev " +
                                    "LEFT JOIN FETCH ev.category AS cat " +
                                    "LEFT JOIN FETCH ev.initiator AS init " +
                                    "WHERE ev.id = ?1",
                            Event.class)
                    .setParameter(1, eventId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Event not found");
        }
    }

    @Override
    public Event findEventByIdWithoutRelatedFields(Long eventId) {
        try {
            return em.createQuery("SELECT ev " +
                                    "FROM Event AS ev " +
                                    "WHERE ev.id = ?1",
                            Event.class)
                    .setParameter(1, eventId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Event not found");
        }
    }

    @Override
    public Event findEventByIdWithThrowsNoResultException(Long eventId) throws javax.persistence.NoResultException {
        return em.createQuery("SELECT ev " +
                                "FROM Event AS ev " +
                                "LEFT JOIN FETCH ev.category AS cat " +
                                "LEFT JOIN FETCH ev.initiator AS init " +
                                "WHERE ev.id = ?1",
                        Event.class)
                .setParameter(1, eventId)
                .getSingleResult();
    }

    @Override
    public List<Event> getListEventsByInitiatorId(Long initiatorId, int from, int size) {
        List<Long> ids = em.createQuery(
                        "SELECT ev.id " +
                                "FROM Event AS ev " +
                                "WHERE ev.initiator.id = ?1",
                        Long.class)
                .setParameter(1, initiatorId)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
        return em.createQuery(
                        "SELECT ev " +
                                "FROM Event AS ev " +
                                "LEFT JOIN FETCH ev.category AS cat " +
                                "LEFT JOIN FETCH ev.initiator AS init " +
                                "WHERE ev.id IN ?1",
                        Event.class)
                .setParameter(1, ids)
                .getResultList();
    }
}
