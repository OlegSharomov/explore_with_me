package ru.practicum.collector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.client.StatisticClient;
import ru.practicum.client.model.ViewStatShort;
import ru.practicum.collector.interfaces.ConfirmedRequests;
import ru.practicum.collector.interfaces.ConfirmedRequestsInterface;
import ru.practicum.collector.interfaces.EventFullDtoInterfaceWithAllFields;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapper;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.Location;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.requests.model.RequestStatus;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.users.dto.UserShortDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class CollectorDto {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final StatisticClient statisticClient;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final CompilationMapper compilationMapper;

    public List<EventShortDto> getListEventShortDto(List<Event> events) {
        Map<Long, Long> confirmedRequests = getMapConfirmedRequests(events);
        Map<Long, Long> mapViews = getMapEventViews(events);
        return events.stream()
                .map(e -> eventMapper.toEventShortDto(
                        e,
                        (confirmedRequests.get(e.getId()) == null) ? 0 : confirmedRequests.get(e.getId()),
                        (mapViews.get(e.getId()) == null) ? 0 : mapViews.get(e.getId())))
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getListEventFullDto(List<Event> events) {
        Map<Long, Long> confirmedRequests = getMapConfirmedRequests(events);
        Map<Long, Long> mapViews = getMapEventViews(events);
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

    public EventFullDto getEventFullDtoWithAllFields(Long eventId) {
        EventFullDtoInterfaceWithAllFields evInterf = eventRepository
                .getEventFullDtoInterfaceWithAllFields(eventId)
                .orElseThrow(() -> new CustomNotFoundException("EventNotFound"));
        Long views = statisticClient.getViewsByUri(eventId).orElse(0L);
        return EventFullDto.builder()
                .annotation(evInterf.getAnnotation())
                .category(CategoryDto.builder().id(evInterf.getCategoryId()).name(evInterf.getCategoryName()).build())
                .confirmedRequests(evInterf.getConfirmedRequests())
                .createdOn(evInterf.getCreatedOn())
                .description(evInterf.getDescription())
                .eventDate(evInterf.getEventDate())
                .id(evInterf.getId())
                .initiator(UserShortDto.builder().id(evInterf.getInitiatorId()).name(evInterf.getInitiatorName()).build())
                .location(Location.builder().lat(evInterf.getLocationLat()).lon(evInterf.getLocationLon()).build())
                .paid(evInterf.getPaid())
                .participantLimit(evInterf.getParticipantLimit())
                .publishedOn(evInterf.getPublishedOn())
                .requestModeration(evInterf.getRequestModeration())
                .state(evInterf.getState())
                .title(evInterf.getTitle())
                .views(views)
                .build();
    }

    public EventFullDto getEventFullDto(Event event, Boolean needViewsAndConfirmedRequests) {
        Long confirmedRequests = 0L;
        Long views = 0L;
        if (needViewsAndConfirmedRequests) {
            views = statisticClient.getViewsByUri(event.getId()).orElse(0L);
            confirmedRequests = requestRepository.countByEventAndStatus(event, RequestStatus.CONFIRMED).orElse(0L);
        }
        CategoryDto categoryDto = categoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = userMapper.toUserShortDto(event.getInitiator());
        return eventMapper.toEventFullDto(event, categoryDto, confirmedRequests, userShortDto, views);
    }

    public CompilationDto getCompilationDto(Compilation compilation) {
        if (compilation.getEvents() == null || compilation.getEvents().isEmpty()) {
            return compilationMapper.toCompilationDto(compilation, Collections.emptyList());
        } else {
            Map<Long, Long> mapViews = getMapEventViews(compilation.getEvents());
            Map<Long, Long> mapConfirmedRequests = getMapConfirmedRequests(compilation.getEvents());
            List<EventShortDto> listEventsDto = compilation.getEvents().stream()
                    .map(e -> eventMapper
                            .toEventShortDto(e,
                                    mapConfirmedRequests.get(e.getId()) == null ? 0 : mapConfirmedRequests.get(e.getId()),
                                    mapViews.get(e.getId()) == null ? 0 : mapViews.get(e.getId())))
                    .collect(Collectors.toList());
            return compilationMapper.toCompilationDto(compilation, listEventsDto);
        }
    }

    public List<CompilationDto> getListCompilationDto(List<Compilation> compilations) {
        Set<Event> uniqueEvents = new HashSet<>();
        compilations.forEach(e -> uniqueEvents.addAll(e.getEvents()));
        if (uniqueEvents.isEmpty()) {
            return compilations.stream()
                    .map(e -> compilationMapper.toCompilationDto(e, Collections.emptyList()))
                    .collect(Collectors.toList());
        }
        List<EventShortDto> eventList = getListEventShortDto(new ArrayList<>(uniqueEvents));
        Map<Long, EventShortDto> eventMap = eventList.stream().collect(Collectors.toMap(EventShortDto::getId, e -> e));

        return compilations.stream().map(
                compilation -> {
                    List<EventShortDto> currentEventList = new ArrayList<>();
                    compilation.getEvents().forEach(event -> currentEventList.add(eventMap.get(event.getId())));
                    return compilationMapper.toCompilationDto(compilation, currentEventList);
                }).collect(Collectors.toList());
    }

    private Map<Long, Long> getMapConfirmedRequests(List<Event> events) {
        List<Long> eventIds = events.stream().mapToLong(Event::getId).boxed().collect(Collectors.toList());
        List<ConfirmedRequestsInterface> confirmedRequestsInterfaceList = requestRepository
                .getConfirmedRequestsOfEvents(eventIds, RequestStatus.CONFIRMED.toString());
        return confirmedRequestsInterfaceList.stream()
                .map(e -> new ConfirmedRequests(e.getEventId(), e.getQuantityConfirmedRequests()))
                .collect(Collectors.toMap(ConfirmedRequestsInterface::getEventId, ConfirmedRequestsInterface::getQuantityConfirmedRequests));
    }

    private Map<Long, Long> getMapEventViews(List<Event> events) {
        String[] uris = events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new);
        List<ViewStatShort> listViews = statisticClient.getStatisticForCollect(uris);
        return listViews.stream()
                .collect(Collectors.toMap(ViewStatShort::getIdFromUriEvent, ViewStatShort::getHits));
    }
}
