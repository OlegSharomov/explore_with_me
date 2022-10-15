package ru.practicum.events.service.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.entity.Category;
import ru.practicum.categories.service.admin.CategoryAdminService;
import ru.practicum.client.StatisticClient;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.entity.User;
import ru.practicum.users.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPrivateService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryAdminService categoryAdminService;
    private final StatisticClient statisticClient;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    // Получение событий, добавленных текущим пользователем. Возвращает список EventShortDto.
    @Transactional
    public List<EventShortDto> getEventsByUserId(Integer userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        User initiator = userService.getEntityUserById(userId);
        List<Event> events = eventRepository.findByInitiator(initiator, pageable);
        return events.stream().map(e -> {
            // Integer confirmedRequests = TODO реализовать вызов для определения количества одобренных заявок
            Integer views = statisticClient.getViewsByUri("/events/" + e.getId());
            return eventMapper.toEventShortDto(e, 0, views);//TODO изменить параметр confirmedRequests
        }).collect(Collectors.toList());
    }

    // Добавление нового события. Возвращает EventFullDto.
    /* !!! Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента*/
    @Transactional(readOnly = false)
    public EventFullDto createEvent(Integer userId, NewEventDto newEventDto) {
        LocalDateTime createdOn = LocalDateTime.now();
        User initiator = userService.getEntityUserById(userId);
        Category category = categoryAdminService.getEntityCategoryById(newEventDto.getCategory());
        Event event = eventMapper.toEventFromNewEventDto(newEventDto, category, initiator, EventState.PENDING, createdOn,
                null, null);
        Event readyEvent = eventRepository.save(event);
        CategoryDto categoryDto = categoryMapper.toCategoryDto(category);
        Integer confirmedRequests = 0;
        UserShortDto userShortDto = userMapper.toUserShortDto(initiator);
        Integer views = 0;
        return eventMapper.toEventFullDto(readyEvent, categoryDto, confirmedRequests, userShortDto, views);

    }

}
