package ru.practicum.events.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.admin.dto.AdminUpdateEventRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/events")
public class EventAdminController {

    @GetMapping
    // Поиск событий. Возвращает list of EventFullDto.
    /* Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия */
    // список id пользователей, чьи события нужно найти
    public void getAllEvents(@RequestParam(required = false) Integer[] users,
                             // список состояний в которых находятся искомые события
                             @RequestParam(required = false) Integer[] states,
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
    }

    @PutMapping("/{eventId}")
    // Редактирование событий. Возвращает EventFullDto.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    public void changeEvent(@PathVariable Integer eventId,
                            @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {

    }

    @PatchMapping("/{eventId}/publish")
    // Публикация события. Возвращает EventFullDto.
    /* Дата начала события должна быть не ранее чем за час от даты публикации.
     * Событие должно быть в состоянии ожидания публикации*/
    public void publishingEvent(@PathVariable Integer eventId) {

    }

    @PatchMapping("/{eventId}/reject")
    // Отклонение события. Возвращает EventFullDto.
    /* Обратите внимание: событие не должно быть опубликовано.*/
    public void rejectEvent(@PathVariable Integer eventId) {

    }

}
