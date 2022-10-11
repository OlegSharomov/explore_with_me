package ru.practicum.events.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventAdminService {
    private final EventRepository eventRepository;

    // Поиск событий. Возвращает list of EventFullDto.
    /* Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия */
    public List<EventFullDto> getAllEvents(// список id пользователей, чьи события нужно найти
                                           Integer[] users,
                                           // список состояний в которых находятся искомые события
                                           Integer[] states,
                                           // список id категорий в которых будет вестись поиск
                                           Integer[] categories,
                                           // дата и время не раньше которых должно произойти событие
                                           LocalDateTime rangeStart,
                                           // дата и время не позже которых должно произойти событие
                                           LocalDateTime rangeEnd,
                                           // количество событий, которые нужно пропустить для формирования текущего набора
                                           Integer from,    /* (дефолтно = 0) */
                                           // количество событий в наборе
                                           Integer size     /* (дефолтно = 10) */) {
        return null;
    }
}
