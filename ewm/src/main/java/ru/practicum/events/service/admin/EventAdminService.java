package ru.practicum.events.service.admin;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.dto.admin.AdminUpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {
    // Поиск событий. Возвращает list of EventFullDto.
    /* Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия */
    List<EventFullDto> getAllEvents(// список id пользователей, чьи события нужно найти
                                    Integer[] users,
                                    // список состояний в которых находятся искомые события
                                    EventState[] states,
                                    // список id категорий в которых будет вестись поиск
                                    Integer[] categories,
                                    // дата и время не раньше которых должно произойти событие
                                    LocalDateTime rangeStart,
                                    // дата и время не позже которых должно произойти событие
                                    LocalDateTime rangeEnd,
                                    // количество событий, которые нужно пропустить для формирования текущего набора
                                    Integer from,    /* (дефолтно = 0) */
                                    // количество событий в наборе
                                    Integer size     /* (дефолтно = 10) */);

    // Редактирование событий. Возвращает EventFullDto.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    EventFullDto changeEvent(Integer eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    // Публикация события. Возвращает EventFullDto.
    /* Дата начала события должна быть не ранее чем за час от даты публикации.
     * Событие должно быть в состоянии ожидания публикации*/
    EventFullDto publishingEvent(Integer eventId);

    // Отклонение события. Возвращает EventFullDto.
    /* Обратите внимание: событие не должно быть опубликовано.*/
    EventFullDto rejectEvent(Integer eventId);
}
