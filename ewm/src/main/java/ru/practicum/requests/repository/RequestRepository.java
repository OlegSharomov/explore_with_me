package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.collector.interfaces.ConfirmedRequestsInterface;
import ru.practicum.events.entity.Event;
import ru.practicum.requests.entity.Request;
import ru.practicum.requests.model.RequestStatus;
import ru.practicum.users.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    Optional<Long> countByEventAndStatus(Event event, RequestStatus requestStatus);

    List<Request> findAllByEventIdAndStatus(Long eventId, RequestStatus requestStatus);

    Boolean existsByRequesterAndEvent(User requester, Event event);

    @Query(value =
            "SELECT event_id AS eventId, " +
                    "count(id) AS quantityConfirmedRequests " +
                    "FROM requests " +
                    "WHERE event_id IN ?1 AND status = ?2 " +
                    "GROUP BY event_id", nativeQuery = true)
    List<ConfirmedRequestsInterface> getConfirmedRequestsOfEvents(List<Long> events, String requestStatus);

}
