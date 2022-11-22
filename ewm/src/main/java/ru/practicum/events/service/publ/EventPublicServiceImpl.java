package ru.practicum.events.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.categories.entity.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.client.StatisticClient;
import ru.practicum.client.model.EndpointHit;
import ru.practicum.collector.CollectorDto;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.specification.EventFindSpecification;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final StatisticClient statisticClient;
    private final CollectorDto collectorDto;

    // Получение событий с возможностью фильтрации.
    /* Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * Текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * Если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут
     *      позже текущей даты и времени
     * Информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @Override
    @Transactional
    public List<EventShortDto> getEvents(String text,          // текст для поиска в содержимом аннотации и подробном описании события
                                         long[] categories,     // список идентификаторов категорий в которых будет вестись поиск
                                         Boolean paid,         // поиск только платных/бесплатных событий
                                         LocalDateTime rangeStart,    // дата и время не раньше которых должно произойти событие
                                         LocalDateTime rangeEnd,      // дата и время не позже которых должно произойти событие
                                         Boolean onlyAvailable,  // только события у которых не исчерпан лимит запросов на участие
                                         String sort,          /* Вариант сортировки: по дате события или по количеству просмотров
                                                                                Available values : EVENT_DATE, VIEWS */
                                         Integer from,       // количество событий, которые нужно пропустить для формирования текущего набора
                                         Integer size,
                                         HttpServletRequest request
    ) {
        Pageable pageable;
        if (sort != null && sort.equals("EVENT_DATE")) {
            pageable = PageRequest.of(from / size, size, Sort.by("eventDate"));
        } else if (sort != null && sort.equals("VIEWS")) {
            pageable = PageRequest.of(from / size, size, Sort.by("views"));
        } else {
            pageable = PageRequest.of(from / size, size);
        }
        List<Long> listOfCategoriesId = Arrays.stream(categories).boxed().collect(Collectors.toList());
        List<Category> listOfCategories = categoryRepository.findAllByIdIn(listOfCategoriesId);
        Page<Event> eventsPage;
        if (rangeStart == null || rangeEnd == null) {
            eventsPage = eventRepository.findAll(EventFindSpecification.specificationForPublicSearchWithoutDate(text,
                    listOfCategories, paid, onlyAvailable, EventState.PUBLISHED), pageable);
        } else {
            eventsPage = eventRepository.findAll(EventFindSpecification.specificationForPublicSearchWithDate(text,
                    listOfCategories, paid, rangeStart, rangeEnd, onlyAvailable, EventState.PUBLISHED), pageable);
        }
        statisticClientCallAndSaveRequest(request);
        return collectorDto.getListEventShortDto(eventsPage.toList());
    }

    // Получение подробной информации об опубликованном событии по его id.
    /* Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики */
    @Override
    @Transactional
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        EventFullDto eventDto = collectorDto.getEventFullDtoWithAllFields(id);
        statisticClientCallAndSaveRequest(request);
        return eventDto;
    }

    private void statisticClientCallAndSaveRequest(HttpServletRequest request) {
        EndpointHit endpointHit = EndpointHit.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        statisticClient.saveCall(endpointHit);
    }
}
