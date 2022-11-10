package ru.practicum.comments.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentMapper;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentSort;
import ru.practicum.comments.model.CommentStatus;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.CustomNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPublicService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    // Просмотр всех комментариев события. Выводит только опубликованные события
    @Transactional
    public List<CommentShortDto> getAllCommentsOfEvent(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("createdOn"));
        List<Comment> comments = commentRepository.findAllByEventIdAndStatus(eventId, CommentStatus.PUBLISHED, pageable);
        return comments.stream()
                .map(e -> commentMapper.toCommentShortDto(e, e.getCommentator().getId(), e.getEvent().getId(),
                        getShortText(e)))
                .collect(Collectors.toList());
    }

    // Просмотр всех комментариев инициатора событий по всем его событиям
    @Transactional
    public List<CommentShortDto> getAllCommentsByInitiatorOfEvents(Long initiatorId, CommentSort sort,
                                                                   Integer from, Integer size) {
        Pageable pageable;
        if (sort.equals(CommentSort.CREATED_ON)) {
            pageable = PageRequest.of(from / size, size, Sort.by("createdOn"));
        } else {
            pageable = PageRequest.of(from / size, size, Sort.by("event"));
        }
        List<Event> events = eventRepository.findAllByInitiatorId(initiatorId);
        List<Long> listEventsId = events.stream().map(Event::getId).collect(Collectors.toList());
        List<Comment> comments = commentRepository.findAllByEventIdInAndStatus(listEventsId, CommentStatus.PUBLISHED,
                pageable);
        return comments.stream()
                .map(e -> commentMapper.toCommentShortDto(e, e.getCommentator().getId(), e.getEvent().getId(),
                        getShortText(e)))
                .collect(Collectors.toList());
    }

    // Просмотр события по id
    @Transactional
    public CommentFullDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomNotFoundException("Comment not found"));
        return commentMapper.toCommentFullDto(comment, comment.getCommentator().getId(), comment.getEvent().getId());
    }

    private String getShortText(Comment comment) {
        int endIndex = Math.min(comment.getText().length(), 149);
        return comment.getText().substring(0, endIndex);
    }

}
