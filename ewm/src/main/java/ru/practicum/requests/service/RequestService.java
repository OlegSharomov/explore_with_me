package ru.practicum.requests.service;

import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    // Получение информации о заявках текущего пользователя на участие в чужих событиях. Возвращает ParticipationRequestDto.
    // Получение информации о заявках текущего пользователя на участие в чужих событиях. Возвращает список ParticipationRequestDto.
    List<ParticipationRequestDto> getParticipationRequest(Integer userId);

    // Добавление запроса от текущего пользователя на участие в событии. Возвращает ParticipationRequestDto.
    /* Нельзя добавить повторный запрос
     * Инициатор события не может добавить запрос на участие в своём событии
     * Нельзя участвовать в неопубликованном событии
     * Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
     * Если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти
     * в состояние подтвержденного*/
    ParticipationRequestDto createParticipationRequest(Integer userId, Integer eventId);

    // Отмена своего запроса на участие в событии. Возвращает ParticipationRequestDto.
    ParticipationRequestDto cancelParticipationRequest(Integer userId, Integer requestId);
}
