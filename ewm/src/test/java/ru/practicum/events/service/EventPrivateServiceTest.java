package ru.practicum.events.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapperImpl;
import ru.practicum.categories.entity.Category;
import ru.practicum.categories.service.admin.CategoryAdminServiceImpl;
import ru.practicum.client.StatisticClient;
import ru.practicum.events.dto.EventMapperImpl;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.priv.UpdateEventRequest;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.priv.EventPrivateServiceImpl;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.dto.RequestMapperImpl;
import ru.practicum.requests.entity.Request;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.dto.UserMapperImpl;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.entity.User;
import ru.practicum.users.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.practicum.events.model.EventState.CANCELED;
import static ru.practicum.events.model.EventState.PENDING;
import static ru.practicum.requests.model.RequestStatus.CONFIRMED;
import static ru.practicum.requests.model.RequestStatus.REJECTED;

@ExtendWith(MockitoExtension.class)
public class EventPrivateServiceTest {
    @InjectMocks
    private EventPrivateServiceImpl eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private CategoryAdminServiceImpl categoryAdminService;
    @Mock
    private StatisticClient statisticClient;
    @Spy
    private EventMapperImpl eventMapper;
    @Spy
    private CategoryMapperImpl categoryMapper;
    @Spy
    private UserMapperImpl userMapper;
    @Spy
    private RequestMapperImpl requestMapper;

    Integer from = 0;
    Integer size = 10;
    Pageable pageable = PageRequest.of(from / size, size);
    LocalDateTime eventDate = LocalDateTime.now().plusDays(2);
    User initiator1 = User.builder().id(1).name("User1").email("user1@gmail.com").build();
    UserShortDto initiatorDto1 = UserShortDto.builder().id(1).name("User1").build();
    Category category1 = Category.builder().id(1).name("Category1").build();
    CategoryDto categoryDto1 = CategoryDto.builder().id(1).name("Category1").build();
    Event event1 = Event.builder().id(1).title("Title1").initiator(initiator1).state(PENDING).eventDate(eventDate).build();
    Event event2 = Event.builder().id(2).title("Title2").initiator(initiator1).state(PENDING).eventDate(eventDate).build();
    EventShortDto eventDto1 = EventShortDto.builder().id(1).title("Title1").initiator(initiatorDto1)
            .confirmedRequests(0).views(0).eventDate(eventDate).build();
    EventShortDto eventDto2 = EventShortDto.builder().id(2).title("Title2").initiator(initiatorDto1)
            .confirmedRequests(0).views(0).eventDate(eventDate).build();

    // getEventsByUserId
    @Test
    public void shouldGetAllEventsByUserId() {
        when(userService.getEntityUserById(1)).thenReturn(initiator1);
        when(eventRepository.findByInitiator(initiator1, pageable)).thenReturn(List.of(event1, event2));
        List<EventShortDto> result = eventService.getEventsByUserId(1, from, size);
        assertEquals(List.of(eventDto1, eventDto2), result);
    }

    // changeEventByUser
    @Test
    public void shouldChangeEventByUser() {
        UpdateEventRequest updateEventRequest = UpdateEventRequest.builder().eventId(1).title("UpdateTitle1").build();
        when(eventRepository.findById(1)).thenReturn(Optional.of(event1));
        Event readyEvent = Event.builder().id(1).title("UpdateTitle1").initiator(initiator1).state(PENDING)
                .eventDate(eventDate).build();
        when(eventRepository.save(any(Event.class))).thenReturn(readyEvent);
        EventFullDto result = eventService.changeEventByUser(1, updateEventRequest);
        EventFullDto eventToCheck = EventFullDto.builder().id(1).title("UpdateTitle1").initiator(initiatorDto1)
                .confirmedRequests(0).views(0).state(PENDING).eventDate(eventDate).build();
        assertEquals(eventToCheck, result);
    }

    // createEvent
    @Test
    public void shouldCreateEvent() {
        NewEventDto newEventDto = NewEventDto.builder().title("Title1").category(1).build();
        when(userService.getEntityUserById(1)).thenReturn(initiator1);
        when(categoryAdminService.getEntityCategoryById(1)).thenReturn(category1);
        Event readyEvent = Event.builder().id(1).title("Title1").initiator(initiator1).state(PENDING)
                .category(category1).eventDate(eventDate).build();
        when(eventRepository.save(any(Event.class))).thenReturn(readyEvent);
        EventFullDto result = eventService.createEvent(1, newEventDto);
        EventFullDto eventToCheck = EventFullDto.builder().id(1).title("Title1").initiator(initiatorDto1)
                .category(categoryDto1).confirmedRequests(0).views(0).state(PENDING).eventDate(eventDate).build();
        assertEquals(eventToCheck, result);
    }

    //  getEventById
    @Test
    public void shouldGetEventById() {
        when(eventRepository.findById(1)).thenReturn(Optional.of(event1));
        EventFullDto result = eventService.getEventById(1, 1);
        EventFullDto eventToCheck = EventFullDto.builder().id(1).title("Title1").initiator(initiatorDto1)
                .confirmedRequests(0).views(0).state(PENDING).eventDate(eventDate).build();
        assertEquals(eventToCheck, result);
    }

    // cancellationEvent
    @Test
    public void shouldCanceledEvent() {
        when(eventRepository.findById(1)).thenReturn(Optional.of(event1));
        Event readyEvent = Event.builder().id(1).title("Title1").initiator(initiator1).state(CANCELED)
                .eventDate(eventDate).build();
        when(eventRepository.save(any(Event.class))).thenReturn(readyEvent);
        EventFullDto result = eventService.cancellationEvent(1, 1);
        EventFullDto eventToCheck = EventFullDto.builder().id(1).title("Title1").initiator(initiatorDto1)
                .confirmedRequests(0).views(0).state(CANCELED).eventDate(eventDate).build();
        assertEquals(eventToCheck, result);
    }

    // getParticipationRequest
    @Test
    public void shouldGetListParticipationRequest() {
        Request request1 = Request.builder().id(1).event(event1).requester(initiator1).build();
        Request request2 = Request.builder().id(2).event(event2).requester(initiator1).build();
        when(requestRepository.findAllByEventId(1)).thenReturn(List.of(request1, request2));
        List<ParticipationRequestDto> result = eventService.getParticipationRequest(1, 1);
        ParticipationRequestDto requestDto1 = ParticipationRequestDto.builder().id(1).event(1).requester(1).build();
        ParticipationRequestDto requestDto2 = ParticipationRequestDto.builder().id(2).event(2).requester(1).build();
        List<ParticipationRequestDto> listToCheck = List.of(requestDto1, requestDto2);
        assertEquals(listToCheck, result);
    }

    // acceptParticipationRequest
    @Test
    public void shouldAcceptParticipationRequest() {
        Event event0 = Event.builder().id(1).title("Title1").initiator(initiator1).state(PENDING)
                .eventDate(eventDate).participantLimit(10).requestModeration(true).build();
        Request request1 = Request.builder().id(1).event(event0).requester(initiator1).build();
        when(requestRepository.findById(1)).thenReturn(Optional.of(request1));
        Request readyRequest = Request.builder().id(1).event(event0).requester(initiator1).status(CONFIRMED).build();
        when(requestRepository.save(request1)).thenReturn(readyRequest);
        ParticipationRequestDto result = eventService.acceptParticipationRequest(1, 1, 1);
        ParticipationRequestDto requestToCheck = ParticipationRequestDto.builder()
                .id(1).event(1).requester(1).status(CONFIRMED).build();
        assertEquals(requestToCheck, result);
    }

    // rejectParticipationRequest
    @Test
    public void shouldRejectParticipationRequest() {
        Event event0 = Event.builder().id(1).title("Title1").initiator(initiator1).state(PENDING)
                .eventDate(eventDate).participantLimit(10).requestModeration(true).build();
        Request request1 = Request.builder().id(1).event(event0).requester(initiator1).status(CONFIRMED).build();
        when(requestRepository.findById(1)).thenReturn(Optional.of(request1));

        Request readyRequest = Request.builder().id(1).event(event0).requester(initiator1).status(REJECTED).build();
        when(requestRepository.save(request1)).thenReturn(readyRequest);

        ParticipationRequestDto result = eventService.rejectParticipationRequest(1, 1, 1);
        ParticipationRequestDto requestToCheck = ParticipationRequestDto.builder()
                .id(1).event(1).requester(1).status(REJECTED).build();
        assertEquals(requestToCheck, result);
    }

    //
}
