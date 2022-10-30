package ru.practicum.events.service.priv;

import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.priv.UpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    List<EventShortDto> getEventsByUserId(Integer userId, Integer from, Integer size);

    EventFullDto changeEventByUser(Integer userId, UpdateEventRequest updateEventRequest);

    EventFullDto createEvent(Integer userId, NewEventDto newEventDto);

    EventFullDto getEventById(Integer userId, Integer eventId);

    EventFullDto cancellationEvent(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto acceptParticipationRequest(Integer userId, Integer eventId, Integer reqId);

    ParticipationRequestDto rejectParticipationRequest(Integer userId, Integer eventId, Integer reqId);

}
