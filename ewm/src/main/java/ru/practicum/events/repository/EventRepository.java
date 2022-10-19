package ru.practicum.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.EventState;
import ru.practicum.users.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByInitiator(User initiator, Pageable pageable);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween
            (List<Integer> users, List<EventState> states, List<Integer> categories,
             LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    

}
