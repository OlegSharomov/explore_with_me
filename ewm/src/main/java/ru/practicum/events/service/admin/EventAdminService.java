package ru.practicum.events.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.entity.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.client.StatisticClient;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.dto.admin.AdminUpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.util.UtilCollectorsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventAdminService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final StatisticClient statisticClient;

    // Поиск событий. Возвращает list of EventFullDto.
    /* Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия */
    @Transactional
    public List<EventFullDto> getAllEvents(// список id пользователей, чьи события нужно найти
                                           Integer[] users,
                                           // список состояний в которых находятся искомые события
                                           EventState[] states,
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
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> listEvent = eventRepository
                .findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(List.of(users), List.of(states),
                        List.of(categories), rangeStart, rangeEnd, pageable);
        return UtilCollectorsDto.getListEventFullDto(listEvent, categoryMapper, userMapper, statisticClient, eventMapper);
    }

    // Редактирование событий. Возвращает EventFullDto.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    @Transactional(readOnly = false)
    public EventFullDto changeEvent(Integer eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            Category category = categoryRepository.findById(adminUpdateEventRequest.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            eventMapper.updateEventFromAdminUpdateEventRequest(adminUpdateEventRequest, event, category);
            Event readyEvent = eventRepository.save(event);
            return UtilCollectorsDto.getEventFullDto(readyEvent, categoryMapper, userMapper, statisticClient, eventMapper);
        } else {
            LocalDateTime createdOn = LocalDateTime.now();
            Category category = categoryRepository.findById(adminUpdateEventRequest.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
//            Event event = eventMapper.toEventFromNewEventDto(newEventDto, category, initiator, EventState.PENDING, createdOn,
//                    null, null);
            Event event = eventMapper.toEventFromAdminUpdateEventRequest(eventId, adminUpdateEventRequest, category,
                    EventState.PENDING, createdOn);
//            Event readyEvent = eventRepository.save(event);
//            return UtilCollectorsDto.getEventFullDto(readyEvent, categoryMapper, userMapper, statisticClient, eventMapper);

                        eventRepository.save(event);
            return UtilCollectorsDto.getEventFullDto(event, categoryMapper, userMapper, statisticClient, eventMapper);

        }
    }


    // Публикация события. Возвращает EventFullDto.
    /* Дата начала события должна быть не ранее чем за час от даты публикации.
     * Событие должно быть в состоянии ожидания публикации*/
    @Transactional(readOnly = false)
    public EventFullDto publishingEvent(Integer eventId) {
        Event eventEntity = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        if (eventEntity.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ValidationException("The start of the event cannot be earlier than in an hour");
        }
        if (!eventEntity.getState().equals(EventState.PENDING)) {
            throw new ValidationException("The publication of the event can only be from the PENDING status");
        }
        eventEntity.setState(EventState.PUBLISHED);
        Event readyEvent = eventRepository.save(eventEntity);
        return UtilCollectorsDto.getEventFullDto(readyEvent, categoryMapper, userMapper, statisticClient, eventMapper);
    }
}
