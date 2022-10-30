package ru.practicum.requests.service;

import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getParticipationRequest(Integer userId);

    ParticipationRequestDto createParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelParticipationRequest(Integer userId, Integer requestId);
}
