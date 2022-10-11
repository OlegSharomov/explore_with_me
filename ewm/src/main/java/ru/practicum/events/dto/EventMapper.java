package ru.practicum.events.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.EventState;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.model.Event;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "createdOn", source = "createdOn")
    @Mapping(target = "publishedOn", source = "publishedOn")
    @Mapping(target = "compilations", source = "compilations")
    Event toEventFromNewEventDto(NewEventDto newEventDto, Category category, User initiator, EventState state,
                                 LocalDateTime createdOn, LocalDateTime publishedOn, List<Compilation> compilations);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "confirmedRequests", source = "confirmedRequests")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "views", source = "views")
    EventFullDto toEventFullDto(Event event, CategoryDto category, Integer confirmedRequests,
                                UserShortDto initiator, Integer views);
}
