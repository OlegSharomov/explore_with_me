package ru.practicum.events.service.priv;

import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.priv.UpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    // Получение событий, добавленных текущим пользователем. Возвращает список EventShortDto.
    List<EventShortDto> getEventsByUserId(Integer userId, Integer from, Integer size);

    // Изменение события, добавленного текущим пользователем. Возвращает EventFullDto.
    /* Изменять можно только отмененные события или события в состоянии ожидания модерации
     * Если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента */
    EventFullDto changeEventByUser(Integer userId, UpdateEventRequest updateEventRequest);

    // Добавление нового события. Возвращает EventFullDto.
    /* !!! Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента*/
    EventFullDto createEvent(Integer userId, NewEventDto newEventDto);

    // Получение полной информации о событии, добавленном текущим пользователем. Возвращает EventFullDto
    EventFullDto getEventById(Integer userId, Integer eventId);

    // Отмена события добавленного текущим пользователем. Возвращает EventFullDto.
    /* Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.*/
    EventFullDto cancellationEvent(Integer userId, Integer eventId);

    // Получение информации о запросах на участие в событии текущего пользователя. Возвращает список ParticipationRequestDto.
    List<ParticipationRequestDto> getParticipationRequest(Integer userId, Integer eventId);

    // Подтверждение чужой заявки на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    /* Если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     * Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
     * Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки
     * необходимо отклонить */
    ParticipationRequestDto acceptParticipationRequest(Integer userId, Integer eventId, Integer reqId);

    // Отклонение чужой заявки на участие в событии текущего пользователя. Возвращает ParticipationRequestDto.
    ParticipationRequestDto rejectParticipationRequest(Integer userId, Integer eventId, Integer reqId);

}
