package ru.practicum.events.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.client.StatisticClient;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.repository.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPublicService {
    private final EventRepository eventRepository;
    private final StatisticClient statisticClient;

    public List<EventShortDto> getEvents(String text,          // текст для поиска в содержимом аннотации и подробном описании события
                                         int[] categories,     // список идентификаторов категорий в которых будет вестись поиск
                                         Boolean paid,         // поиск только платных/бесплатных событий
                                         String rangeStart,    // дата и время не раньше которых должно произойти событие
                                         String rangeEnd,      // дата и время не позже которых должно произойти событие
                                         Boolean onlyAvailable,  // только события у которых не исчерпан лимит запросов на участие
                                         String sort,          /* Вариант сортировки: по дате события или по количеству просмотров
                                                                                Available values : EVENT_DATE, VIEWS */
                                         Integer from,       // количество событий, которые нужно пропустить для формирования текущего набора
                                         Integer size,
                                         HttpServletRequest request
    ) {
        saveStatisticCall(null, request);


        return null;
    }

    public EventFullDto getEventById(@PathVariable Integer id, HttpServletRequest request) {
        saveStatisticCall(id, request);
        Integer views = statisticClient.getViewsByUri(request.getRequestURI());


        return null;
    }



    private void saveStatisticCall(Integer id, HttpServletRequest request) {
        Map<String, String> endpointHitMap = new HashMap<>();
        endpointHitMap.put("app", "ewm-main-service");
        endpointHitMap.put("uri", request.getRequestURI());
        endpointHitMap.put("ip", request.getRemoteAddr());
        endpointHitMap.put("timestamp", String.valueOf(LocalDateTime.now()));
        statisticClient.saveCall(endpointHitMap);
    }

}
