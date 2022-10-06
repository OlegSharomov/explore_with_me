package ru.practicum.events.priv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.priv.dto.NewEventDto;
import ru.practicum.events.priv.dto.UpdateEventRequest;

import javax.validation.Valid;

@RestController("/users")
public class EventPrivateController {

    @GetMapping("/{userId}/events")
    // Получение событий, добавленных текущим пользователем. Возвращает список EventShortDto.
    public void getEventsByUserId(@PathVariable Integer userId,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {

    }

    @PatchMapping("/{userId}/events")
    // Изменение события, добавленного текущим пользователем. Возвращает EventFullDto
    /* Изменить можно только отмененные события или события в состоянии ожидания модерации
     * Если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента */
    public void changeEventByUser(@PathVariable Integer userId,
                                  @Valid @RequestBody UpdateEventRequest updateEventRequest) {

    }

    @PostMapping("/{userId}/events")
    // Добавление нового события. Возвращает EventFullDto.
    /* !!! Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента*/
    public void createEvent(@RequestParam Integer userId,
                            @Valid @RequestBody NewEventDto newEventDto) {

    }

    @GetMapping("/{userId}/events/{eventId}")
    // Получение полной информации о событии, добавленном текущим пользователем. Возвращает EventFullDto
    public void getEventById(@PathVariable Integer userId,
                             @PathVariable Integer eventId) {

    }

    @PatchMapping("/{userId}/events/{eventId}")
    // Отмена события добавленного текущим пользователем. Возвращает EventFullDto.
    /* Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.*/
    public void cancellationEvent(@PathVariable Integer userId,
                                  @PathVariable Integer eventId) {

    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    // Получении информации о запросах на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    public void getParticipationRequest(@PathVariable Integer userId,
                                        @PathVariable Integer eventId) {

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

    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    // Отклонение чужой заявки на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    public void rejectParticipationRequest(@PathVariable Integer userId,
                                           @PathVariable Integer eventId,
                                           @PathVariable Integer reqId) {

    }

}
