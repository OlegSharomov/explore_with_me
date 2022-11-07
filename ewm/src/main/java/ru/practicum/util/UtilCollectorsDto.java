package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.client.StatisticClient;
import ru.practicum.client.model.ViewStatShort;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapper;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.requests.model.ConfirmedRequests;
import ru.practicum.requests.model.ConfirmedRequestsInterface;
import ru.practicum.requests.model.RequestStatus;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.users.dto.UserShortDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UtilCollectorsDto {

    public static List<EventShortDto> getListEventShortDto(List<Event> events,
                                                           StatisticClient statisticClient,
                                                           EventMapper eventMapper, RequestRepository requestRepository) {
        Map<Long, Long> confirmedRequests = getMapConfirmedRequests(events, requestRepository);
        Map<Long, Long> mapViews = getMapEventViews(events, statisticClient);
        return events.stream()
                .map(e -> eventMapper.toEventShortDto(
                        e,
                        (confirmedRequests.get(e.getId()) == null) ? 0 : confirmedRequests.get(e.getId()),
                        (mapViews.get(e.getId()) == null) ? 0 : mapViews.get(e.getId())))
                .collect(Collectors.toList());
    }

    public static EventShortDto getEventShortDto(Event event, EventMapper eventMapper,
                                                 StatisticClient statisticClient, RequestRepository requestRepository) {
        Long confirmedRequests = requestRepository
                .countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED).orElse(0L);
        Long views = statisticClient.getViewsByUri(event.getId());
        return eventMapper.toEventShortDto(event, confirmedRequests, views);
    }

    public static List<EventFullDto> getListEventFullDto(List<Event> events, CategoryMapper categoryMapper,
                                                         UserMapper userMapper, StatisticClient statisticClient,
                                                         EventMapper eventMapper, RequestRepository requestRepository) {
        Map<Long, Long> confirmedRequests = getMapConfirmedRequests(events, requestRepository);
        Map<Long, Long> mapViews = getMapEventViews(events, statisticClient);
        return events.stream()
                .map(e -> {
                    CategoryDto categoryDto = categoryMapper.toCategoryDto(e.getCategory());
                    UserShortDto initiator = userMapper.toUserShortDto(e.getInitiator());
                    return eventMapper.toEventFullDto(
                            e,
                            categoryDto,
                            (confirmedRequests.get(e.getId()) == null) ? 0 : confirmedRequests.get(e.getId()),
                            initiator,
                            (mapViews.get(e.getId()) == null) ? 0 : mapViews.get(e.getId()));
                })
                .collect(Collectors.toList());
    }

    public static EventFullDto getEventFullDto(Event event, CategoryMapper categoryMapper, UserMapper userMapper,
                                               StatisticClient statisticClient, EventMapper eventMapper,
                                               RequestRepository requestRepository) {
        CategoryDto categoryDto = categoryMapper.toCategoryDto(event.getCategory());
        Long confirmedRequests = requestRepository
                .countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED).orElse(0L);
        UserShortDto initiator = userMapper.toUserShortDto(event.getInitiator());
        Long views = 0L;
        if (statisticClient != null) {
            views = statisticClient.getViewsByUri(event.getId());
        }
        return eventMapper.toEventFullDto(event, categoryDto, confirmedRequests,
                initiator, views);
    }

    public static CompilationDto getCompilationDto(Compilation compilation,
                                                   StatisticClient statisticClient,
                                                   EventMapper eventMapper,
                                                   CompilationMapper compilationMapper,
                                                   RequestRepository requestRepository) {
        List<EventShortDto> eventsDto = getListEventShortDto(compilation.getEvents(),
                statisticClient, eventMapper, requestRepository);
        return compilationMapper.toCompilationDto(compilation, eventsDto);
    }

    private static Map<Long, Long> getMapConfirmedRequests(List<Event> events, RequestRepository requestRepository) {
        List<Long> eventIds = events.stream().mapToLong(Event::getId).boxed().collect(Collectors.toList());
        List<ConfirmedRequestsInterface> confirmedRequestsInterfaceList = requestRepository
                .getConfirmedRequestsOfEvents(eventIds, RequestStatus.CONFIRMED.toString());

        return confirmedRequestsInterfaceList.stream()
                .map(e -> new ConfirmedRequests(e.getEventId(), e.getQuantityConfirmedRequests()))
                .collect(Collectors.toMap(ConfirmedRequests::getEventId, ConfirmedRequests::getQuantityConfirmedRequests));
    }

    private static Map<Long, Long> getMapEventViews(List<Event> events, StatisticClient statisticClient) {
        String[] uris = events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new);
        List<ViewStatShort> listViews = statisticClient.getStatisticForCollect(uris);
        return listViews.stream()
                .collect(Collectors.toMap(ViewStatShort::getIdFromUriEvent, ViewStatShort::getHits));
    }
}
