package ru.practicum.events.service.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.admin.CategoryAdminService;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;

import java.time.LocalDateTime;

import static ru.practicum.events.EventState.PENDING;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPrivateService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryAdminService categoryAdminService;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    // Добавление нового события. Возвращает EventFullDto.
    /* !!! Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента*/
    public EventFullDto createEvent(Integer userId, NewEventDto newEventDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        User initiator = userService.getEntityUserById(userId);
        Category category = categoryAdminService.getEntityCategoryById(newEventDto.getCategory());
        Event event = eventMapper.toEventFromNewEventDto(newEventDto, category, initiator, PENDING, currentTime,
                null, null);
        Event readyEvent = eventRepository.save(event);

        CategoryDto categoryDto = categoryMapper.toCategoryDto(category);
        Integer confirmedRequests = 0;// TODO реализовать получение количества одобренных заявок на участие в данном событии
        UserShortDto userShortDto = userMapper.toUserShortDto(initiator);
        Integer views = 0;// TODO реализовать вызов к БД статистики
        return eventMapper.toEventFullDto(readyEvent, categoryDto, confirmedRequests, userShortDto, views);

    }

}
