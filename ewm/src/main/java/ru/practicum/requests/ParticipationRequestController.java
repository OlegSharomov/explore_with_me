package ru.practicum.requests;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/requests")
public class ParticipationRequestController {

    @GetMapping
    // Получение информации о заявках текущего пользователя на участие в чужих событиях. Возвращает ParticipationRequestDto.
    public void getParticipationRequest(@PathVariable Integer userId) {

    }

    @PostMapping
    // Добавление запроса от текущего пользователя на участие в событии. Возвращает ParticipationRequestDto.
    /* Нельзя добавить повторный запрос
     * Инициатор события не может добавить запрос на участие в своём событии
     * Нельзя участвовать в неопубликованном событии
     * Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
     * Если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти
     * в состояние подтвержденного*/
    public void createParticipationRequest(@PathVariable Integer userId,
                                           @RequestParam Integer eventId) {

    }

    @PatchMapping("/{requestId}/cancel")
    // Отмена своего запроса на участие в событии. Возвращает ParticipationRequestDto.
    public void cancelParticipationRequest(@PathVariable Integer userId,
                                           @PathVariable Integer requestId) {

    }
}
