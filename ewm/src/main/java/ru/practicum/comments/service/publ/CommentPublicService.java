package ru.practicum.comments.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentMapper;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentSort;
import ru.practicum.comments.repository.CustomCommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPublicService {
    private final CustomCommentRepository customCommentRepository;
    private final CommentMapper commentMapper;

    // Просмотр всех комментариев события. Выводит только опубликованные события
    @Transactional
    public List<CommentShortDto> getAllCommentsOfEvent(Long eventId, Integer from, Integer size) {
        List<Comment> comments = customCommentRepository.findAllCommentsOfEvent(eventId, from, size);
        return comments.stream()
                .map(e -> commentMapper.toCommentShortDto(e, e.getCommentator().getId(), e.getEvent().getId(),
                        getShortText(e)))
                .collect(Collectors.toList());
    }

    // Просмотр всех комментариев инициатора событий по всем его событиям
    @Transactional
    public List<CommentShortDto> getAllCommentsByInitiatorOfEvents(Long initiatorId, CommentSort sort,
                                                                   Integer from, Integer size) {
        List<Comment> comments = customCommentRepository
                .getAllCommentsOfAllEventsByInitiatorId(initiatorId, sort, from, size);
        return comments.stream()
                .map(e -> commentMapper.toCommentShortDto(e, e.getCommentator().getId(), e.getEvent().getId(),
                        getShortText(e)))
                .collect(Collectors.toList());
    }

    // Просмотр события по id
    @Transactional
    public CommentFullDto getCommentById(Long commentId) {
        Comment comment = customCommentRepository.findCommentByIdWithoutRelatedFields(commentId);
        return commentMapper.toCommentFullDto(comment, comment.getCommentator().getId(), comment.getEvent().getId());
    }

    private String getShortText(Comment comment) {
        int endIndex = Math.min(comment.getText().length(), 149);
        return comment.getText().substring(0, endIndex);
    }
}
