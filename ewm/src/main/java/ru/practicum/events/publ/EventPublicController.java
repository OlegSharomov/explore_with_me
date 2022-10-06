package ru.practicum.events.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/events")
public class EventPublicController {
    // Получение событий с возможностью фильтрации. Возвращает список EventShortDto
    /* Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * Текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * Если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут
     *      позже текущей даты и времени
     * Информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @GetMapping
    public void getEvents(@RequestParam(required = false) String text,          // текст для поиска в содержимом аннотации и подробном описании события
                          @RequestParam(required = false) int[] categories,     // список идентификаторов категорий в которых будет вестись поиск
                          @RequestParam(required = false) Boolean paid,         // поиск только платных/бесплатных событий
                          @RequestParam(required = false) String rangeStart,    // дата и время не раньше которых должно произойти событие
                          @RequestParam(required = false) String rangeEnd,      // дата и время не позже которых должно произойти событие
                          @RequestParam(defaultValue = "false") Boolean onlyAvailable,  // только события у которых не исчерпан лимит запросов на участие
                          @RequestParam(required = false) String sort,          /* Вариант сортировки: по дате события или по количеству просмотров
                                                                                Available values : EVENT_DATE, VIEWS */
                                                                                // TODO Проверить возможность использования ENUM
                          @RequestParam(defaultValue = "0") Integer from,       // количество событий, которые нужно пропустить для формирования текущего набора
                          @RequestParam(defaultValue = "10") Integer size) {    // количество событий в наборе

    }



    @GetMapping("/{id}")
    // Получение подробной информации об опубликованном событии по его id. Возвращает EventFullDto.
    /* Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    public void getEventById(@PathVariable Long id) {

    }
}
