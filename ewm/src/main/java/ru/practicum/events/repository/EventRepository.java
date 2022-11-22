package ru.practicum.events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.collector.interfaces.EventFullDtoInterfaceWithAllFields;
import ru.practicum.events.entity.Event;
import ru.practicum.users.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByInitiator(User initiator, Pageable pageable);

    Long countByCategoryId(Long catId);

    @Override
    @EntityGraph(attributePaths = {"initiator"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

    @Query(value =
            "SELECT event.annotation AS annotation, " +
                    "category.id AS categoryId, " +
                    "category.category_name AS categoryName, " +
                    "COUNT(request.status = 'CONFIRMED') AS confirmedRequests, " +
                    "event.created_on AS createdOn, " +
                    "event.description AS description, " +
                    "event.event_date AS eventDate, " +
                    "event.id AS id, " +
                    "initiator.id AS initiatorId, " +
                    "initiator.user_name AS initiatorName, " +
                    "event.lat AS locationLat, " +
                    "event.lon AS locationLon, " +
                    "event.paid AS paid, " +
                    "event.participant_limit AS participantLimit, " +
                    "event.published_on AS publishedOn, " +
                    "event.request_moderation AS requestModeration, " +
                    "event.state AS state, " +
                    "event.title AS title " +
                    "FROM events AS event " +
                    "LEFT JOIN categories AS category " +
                    "ON event.category_id=category.id " +
                    "LEFT JOIN users AS initiator " +
                    "ON event.initiator_id=initiator.id " +
                    "LEFT JOIN requests AS request " +
                    "ON event.id = request.event_id " +
                    "WHERE event.id = ?1 " +
                    "GROUP BY event.id, categoryId, categoryName, initiatorId, initiatorName;", nativeQuery = true)
    Optional<EventFullDtoInterfaceWithAllFields> getEventFullDtoInterfaceWithAllFields(Long eventId);
}














