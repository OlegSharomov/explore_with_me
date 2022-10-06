package ru.practicum.events.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/events")
public class EventPublicController {

    /* это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
     * информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @GetMapping
    public void getEvents(@RequestParam(required = false) String text,
                          @RequestParam(required = false) int[] categories,
                          @RequestParam(required = false) Boolean paid,
                          @RequestParam(required = false) String rangeStart,
                          @RequestParam(required = false) String rangeEnd,
                          @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                          @RequestParam(required = false) String sort,  /* Вариант сортировки: по дате события
                                                                           или по количеству просмотров EVENT_DATE, VIEWS */
                          @RequestParam(required = false, defaultValue = "0") Integer from,
                          @RequestParam(required = false, defaultValue = "10") Integer size) {

    }


    /* Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @GetMapping("/{id}")
    public void getEventById(@PathVariable Long id) {

    }
}
