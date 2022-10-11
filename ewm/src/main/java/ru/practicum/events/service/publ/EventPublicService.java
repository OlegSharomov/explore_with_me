package ru.practicum.events.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.repository.EventRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPublicService {
    private final EventRepository eventRepository;

}
