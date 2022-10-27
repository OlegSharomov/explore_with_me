package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.requests.entity.Request;
import ru.practicum.requests.model.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Optional<Request> findByRequesterIdAndEventId(Integer userId, Integer eventId);

    List<Request> findAllByRequesterId(Integer requesterId);

    List<Request> findAllByEventId(Integer eventId);

    Optional<Integer> countByEventIdAndStatus(Integer eventId, RequestStatus requestStatus);

    List<Request> findAllByEventIdAndStatus(Integer eventId, RequestStatus requestStatus);

    Boolean existsByRequesterIdAndEventId(Integer userId, Integer eventId);

}
