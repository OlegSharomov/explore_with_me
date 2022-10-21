package ru.practicum.events.controller.publ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.service.publ.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventPublicController {
    private final EventPublicService eventPublicService;

    // Получение событий с возможностью фильтрации. Возвращает список EventShortDto
    /* Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * Текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * Если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут
     *      позже текущей даты и времени
     * Информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,          // текст для поиска в содержимом аннотации и подробном описании события
                                         @RequestParam(required = false) int[] categories,     // список идентификаторов категорий в которых будет вестись поиск
                                         @RequestParam(required = false) Boolean paid,         // поиск только платных/бесплатных событий
                                         @RequestParam(required = false) LocalDateTime rangeStart,    // дата и время не раньше которых должно произойти событие
                                         @RequestParam(required = false) LocalDateTime rangeEnd,      // дата и время не позже которых должно произойти событие
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,  // только события у которых не исчерпан лимит запросов на участие
                                         @RequestParam(required = false) String sort,          /* Вариант сортировки: по дате события или по количеству просмотров
                                                                                Available values : EVENT_DATE, VIEWS */
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,       // количество событий, которые нужно пропустить для формирования текущего набора
                                         @Positive @RequestParam(defaultValue = "10") Integer size,
                                         HttpServletRequest request) {    // количество событий в наборе
        log.info("Received a request: GET /events with parameters: text = {}, categories = {}, paid = {}, " +
                        "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}",
                text, Arrays.toString(categories), paid, rangeStart, rangeEnd, onlyAvailable, sort);
        return eventPublicService.getEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, request);
    }


    @GetMapping("/{id}")
    // Получение подробной информации об опубликованном событии по его id. Возвращает EventFullDto.
    /* Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    public EventFullDto getEventById(@Positive @PathVariable Integer id, HttpServletRequest request) {
        log.info("Received a request: GET /events/{}", id);
        return eventPublicService.getEventById(id, request);
    }
}
