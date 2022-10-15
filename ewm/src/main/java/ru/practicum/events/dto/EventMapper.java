package ru.practicum.events.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.entity.Category;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.events.dto.priv.NewEventDto;
import ru.practicum.events.dto.publ.EventFullDto;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.events.model.EventState;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    Event toEventFromNewEventDto(NewEventDto newEventDto, Category category, User initiator, EventState state,
                                 LocalDateTime createdOn, LocalDateTime publishedOn, List<Compilation> compilations);

    @Mapping(target = "id", source = "event.id")
    EventFullDto toEventFullDto(Event event, CategoryDto category, Integer confirmedRequests,
                                UserShortDto initiator, Integer views);

    EventShortDto toEventShortDto(Event event, Integer confirmedRequests, Integer views);
}
