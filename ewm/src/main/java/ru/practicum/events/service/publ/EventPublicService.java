package ru.practicum.events.service.publ;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventPublicService {
    // Получение событий с возможностью фильтрации. Возвращает список EventShortDto
    /* Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * Текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * Если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут
     *      позже текущей даты и времени
     * Информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    List<EventShortDto> getEvents(String text,          // текст для поиска в содержимом аннотации и подробном описании события
                                  int[] categories,     // список идентификаторов категорий в которых будет вестись поиск
                                  Boolean paid,         // поиск только платных/бесплатных событий
                                  LocalDateTime rangeStart,    // дата и время не раньше которых должно произойти событие
                                  LocalDateTime rangeEnd,      // дата и время не позже которых должно произойти событие
                                  Boolean onlyAvailable,  // только события у которых не исчерпан лимит запросов на участие
                                  String sort,          /* Вариант сортировки: по дате события или по количеству просмотров
                                                                                Available values : EVENT_DATE, VIEWS */
                                  Integer from,       // количество событий, которые нужно пропустить для формирования текущего набора
                                  Integer size,
                                  HttpServletRequest request
    );

    // Получение подробной информации об опубликованном событии по его id. Возвращает EventFullDto.
    /* Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    EventFullDto getEventById(@PathVariable Integer id, HttpServletRequest request);

    void statisticClientCallAndSaveRequest(HttpServletRequest request);
}
