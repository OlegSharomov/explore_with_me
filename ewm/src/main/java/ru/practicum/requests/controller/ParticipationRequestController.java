package ru.practicum.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final RequestService requestService;

    @GetMapping
    // Получение информации о заявках текущего пользователя на участие в чужих событиях. Возвращает список ParticipationRequestDto.
    public List<ParticipationRequestDto> getParticipationRequest(@PathVariable Integer userId) {
        log.info("Received a request: GET /users/{}/requests", userId);
        return requestService.getParticipationRequest(userId);
    }

    @PostMapping
    // Добавление запроса от текущего пользователя на участие в событии. Возвращает ParticipationRequestDto.
    /* Нельзя добавить повторный запрос
     * Инициатор события не может добавить запрос на участие в своём событии
     * Нельзя участвовать в неопубликованном событии
     * Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
     * Если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти
     * в состояние подтвержденного*/
    public ParticipationRequestDto createParticipationRequest(@PathVariable Integer userId,
                                                              @RequestParam Integer eventId) {
        log.info("Received a request: POST /users/{}/requests with parameter: eventId = {}", userId, eventId);
        return requestService.createParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    // Отмена своего запроса на участие в событии. Возвращает ParticipationRequestDto.
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Integer userId,
                                           @PathVariable Integer requestId) {
        log.info("Received a request: PATCH /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.cancelParticipationRequest(userId, requestId);
    }
}
