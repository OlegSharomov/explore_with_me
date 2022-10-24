package ru.practicum.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.events.controller.priv.EventPrivateController;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.priv.UpdateEventRequest;
import ru.practicum.events.model.Location;
import ru.practicum.events.service.priv.EventPrivateService;
import ru.practicum.exception.ValidationException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventPrivateController.class)
public class EventPrivateControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventPrivateService eventPrivateService;
    UpdateEventRequest updateEventRequest = UpdateEventRequest.builder().eventId(1)
            .description("Bring happiness and kindness").build();
    Location location = Location.builder().lat(58.605731F).lon(49.644289F).build();
    NewEventDto newEventDto = NewEventDto.builder()
            .annotation("Exhibition of paintings")
            .category(1)
            .description("Exhibition dedicated to the colors of spring")
            .eventDate(LocalDateTime.now().plusDays(2))
            .location(location)
            .title("Exhibition")
            .build();

    // getEventsByUserId
    @Test
    public void shouldGetParametersAndCallServiceWithBody() throws Exception {
        mockMvc.perform(get("/users/{userId}/events", 1)
                        .param("from", "1")
                        .param("size", "20")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .getEventsByUserId(1, 1, 20);
    }

    // changeEventByUser
    @Test
    public void shouldGetUpdateEventRequestAndCallServiceWithBody() throws Exception {
        mockMvc.perform(patch("/users/{userId}/events", 1)
                        .content(objectMapper.writeValueAsString(updateEventRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .changeEventByUser(1, updateEventRequest);
    }

    // createEvent
    @Test
    public void shouldGetNewEventDtoAndCallServiceWithBody() throws Exception {
        mockMvc.perform(post("/users/{userId}/events", 1)
                        .content(objectMapper.writeValueAsString(newEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .createEvent(eq(1), any(NewEventDto.class));
    }

    @Test
    public void shouldGetNewEventDtoAndThrowException() throws Exception {
        NewEventDto newEventDto2 = NewEventDto.builder()
                .annotation("Exhibition of paintings")
                .category(1)
                .description("Exhibition dedicated to the colors of spring")
                .eventDate(LocalDateTime.now())
                .location(location)
                .title("Exhibition")
                .build();
        mockMvc.perform(post("/users/{userId}/events", 1)
                        .content(objectMapper.writeValueAsString(newEventDto2))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("The start of the event cannot be earlier than 2 hours later")));
        Mockito.verify(eventPrivateService, Mockito.times(0))
                .createEvent(eq(1), any(NewEventDto.class));
    }

    // getEventById
    @Test
    public void shouldGetParametersAndCallService() throws Exception {
        mockMvc.perform(get("/users/{userId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .getEventById(1, 1);
    }

    // cancellationEvent
    @Test
    public void shouldGetParametersForCancellationAndCallService() throws Exception {
        mockMvc.perform(patch("/users/{userId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .cancellationEvent(1, 1);
    }

    // getParticipationRequest
    @Test
    public void shouldGetParametersForGetParticipationRequestAndCallService() throws Exception {
        mockMvc.perform(get("/users/{userId}/events/{eventId}/requests", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .getParticipationRequest(1, 1);
    }

    // acceptParticipationRequest
    @Test
    public void shouldGetParametersForAcceptParticipationRequestAndCallService() throws Exception {
        mockMvc.perform(patch("/users/{userId}/events/{eventId}/requests/{reqId}/confirm", 1, 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .acceptParticipationRequest(1, 1, 1);
    }


    // rejectParticipationRequest
    @Test
    public void shouldGetParametersForRejectParticipationRequestAndCallService() throws Exception {
        mockMvc.perform(patch("/users/{userId}/events/{eventId}/requests/{reqId}/reject", 1, 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(eventPrivateService, Mockito.times(1))
                .rejectParticipationRequest(1, 1, 1);
    }
}
