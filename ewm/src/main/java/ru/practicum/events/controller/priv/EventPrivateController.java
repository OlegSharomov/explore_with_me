package ru.practicum.events.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.priv.UpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.service.priv.EventPrivateService;
import ru.practicum.exception.ValidationException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventPrivateService eventPrivateService;

    @GetMapping("/{userId}/events")
    // Получение событий, добавленных текущим пользователем. Возвращает список EventShortDto.
    public List<EventShortDto> getEventsByUserId(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /users/{}/events with parameters: from = {}, size = {}", userId, from, size);
        return eventPrivateService.getEventsByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    // Изменение события, добавленного текущим пользователем. Возвращает EventFullDto
    /* Изменить можно только отмененные события или события в состоянии ожидания модерации
     * Если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента */
    public void changeEventByUser(@PathVariable Integer userId,
                                  @Valid @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Received a request: PATCH /users/{}/events with body: {}", userId, updateEventRequest);

    }

    @PostMapping("/{userId}/events")
    // Добавление нового события. Возвращает EventFullDto.
    /* !!! Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента*/
    public EventFullDto createEvent(@PathVariable Integer userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Received a request: POST /users/{}/events with body: {}", userId, newEventDto);
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("The start of the event cannot be earlier than 2 hours later");
        }
        return eventPrivateService.createEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    // Получение полной информации о событии, добавленном текущим пользователем. Возвращает EventFullDto
    public void getEventById(@PathVariable Integer userId,
                             @PathVariable Integer eventId) {
        log.info("Received a request: GET /users/{}/events/{} ", userId, eventId);

    }

    @PatchMapping("/{userId}/events/{eventId}")
    // Отмена события добавленного текущим пользователем. Возвращает EventFullDto.
    /* Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.*/
    public void cancellationEvent(@PathVariable Integer userId,
                                  @PathVariable Integer eventId) {
        log.info("Received a request: PATCH /users/{}/events/{} ", userId, eventId);

    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    // Получена информации о запросах на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    public void getParticipationRequest(@PathVariable Integer userId,
                                        @PathVariable Integer eventId) {
        log.info("Received a request: GET /users/{}/events/{}/requests ", userId, eventId);

    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    // Подтверждение чужой заявки на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    /* Если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     * Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
     * Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки
     * необходимо отклонить */
    public void acceptParticipationRequest(@PathVariable Integer userId,
                                           @PathVariable Integer eventId,
                                           @PathVariable Integer reqId) {
        log.info("Received a request: PATCH /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);

    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    // Отклонение чужой заявки на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    public void rejectParticipationRequest(@PathVariable Integer userId,
                                           @PathVariable Integer eventId,
                                           @PathVariable Integer reqId) {
        log.info("Received a request: PATCH /users/{}/events/{}/requests/{}/reject", userId, eventId, reqId);

    }

}
