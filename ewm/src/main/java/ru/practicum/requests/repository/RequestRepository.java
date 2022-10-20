package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.requests.entity.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Optional<Request> findByRequesterIdAndEventId(Integer userId, Integer eventId);

    List<Request> findAllByRequesterId(Integer requesterId);
}
