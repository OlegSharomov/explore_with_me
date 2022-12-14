package ru.practicum.comments.dto;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentStatus;
import ru.practicum.events.entity.Event;
import ru.practicum.users.entity.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", source = "event")
    Comment toComment(NewCommentDto newCommentDto, User commentator, Event event, CommentStatus status, LocalDateTime createdOn);

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "commentatorId", source = "commentatorId")
    @Mapping(target = "eventId", source = "eventId")
    CommentFullDto toCommentFullDto(Comment comment, Long commentatorId, Long eventId);

    @Mapping(target = "commentatorId", source = "commentatorId")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "text", source = "text")
    CommentShortDto toCommentShortDto(Comment comment, Long commentatorId, Long eventId, String text);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commentator", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "status", source = "status")
    void updateComment(CommentUpdateDto commentUpdateDto, @MappingTarget Comment comment, CommentStatus status);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commentator", source = "commentator")
    @Mapping(target = "event", source = "event")
    void updateCommentFromAdmin(CommentAdminUpdateDto commentAdminUpdateDto, @MappingTarget Comment comment,
                                User commentator, Event event);

}
