package ru.practicum.events.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.admin.AdminUpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.model.EventState;
import ru.practicum.events.service.admin.EventAdminService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final EventAdminService eventAdminService;

    @GetMapping
    // Поиск событий. Возвращает list of EventFullDto.
    /* Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия */
    // список id пользователей, чьи события нужно найти
    public List<EventFullDto> getAllEvents(@RequestParam(required = false) Integer[] users,
                                           // список состояний в которых находятся искомые события
                                           @RequestParam(required = false) EventState[] states,
                                           // список id категорий в которых будет вестись поиск
                                           @RequestParam(required = false) Integer[] categories,
                                           // дата и время не раньше которых должно произойти событие
                                           @RequestParam(required = false) LocalDateTime rangeStart,
                                           // дата и время не позже которых должно произойти событие
                                           @RequestParam(required = false) LocalDateTime rangeEnd,
                                           // количество событий, которые нужно пропустить для формирования текущего набора
                                           @RequestParam(defaultValue = "0") Integer from,
                                           // количество событий в наборе
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /admin/events with parameters: users = {}, states = {}, categories = {}, " +
                        "rangeStart = {}, rangeEnd = {}, from = {}, size = {}", Arrays.toString(users),
                Arrays.toString(states), Arrays.toString(categories), rangeStart, rangeEnd, from, size);
        return eventAdminService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    // Редактирование событий. Возвращает EventFullDto.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    public EventFullDto changeEvent(@PathVariable Integer eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Received a request: PUT /admin/events/{} with body: {}", eventId, adminUpdateEventRequest);
        return eventAdminService.changeEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    // Публикация события. Возвращает EventFullDto.
    /* Дата начала события должна быть не ранее чем за час от даты публикации.
     * Событие должно быть в состоянии ожидания публикации*/
    public EventFullDto publishingEvent(@PathVariable Integer eventId) {
        log.info("Received a request: PATCH /admin/events/{}/publish ", eventId);
        return eventAdminService.publishingEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    // Отклонение события. Возвращает EventFullDto.
    /* Обратите внимание: событие не должно быть опубликовано.*/
    public void rejectEvent(@PathVariable Integer eventId) {
        log.info("Received a request: PATCH /admin/events/{}/reject ", eventId);


    }

}
