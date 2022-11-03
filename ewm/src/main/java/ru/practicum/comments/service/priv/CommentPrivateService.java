package ru.practicum.comments.service.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentMapper;
import ru.practicum.comments.dto.CommentUpdateDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentStatus;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.users.entity.User;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPrivateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    // Добавление нового комментария. Возвращает CommentDto.
    // Должна производиться проверка на корректность введенных данных.
    @Transactional(readOnly = false)
    public CommentFullDto createComment(Long userId, NewCommentDto newCommentDto) {

        // TODO добавить проверку на корректность введенных данных
        Boolean correct = null;

        User commentator = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("User not found"));
        Event event = eventRepository.findById(newCommentDto.getEventId())
                .orElseThrow(() -> new CustomNotFoundException("Event not found"));
        LocalDateTime createdOn = LocalDateTime.now();
        Comment comment;
        if (correct) {
            comment = commentMapper.toComment(newCommentDto, commentator, event, CommentStatus.PUBLISHED, createdOn);
        } else {
            comment = commentMapper.toComment(newCommentDto, commentator, event, CommentStatus.CANCELED, createdOn);
        }
        Comment readyComment = commentRepository.save(comment);
        return commentMapper.toCommentFullDto(readyComment, readyComment.getCommentator().getId(), readyComment.getEvent().getId());
    }

    // Редактирование комментария. Возвращает CommentDto.
    // Должна производиться проверка на корректность введенных данных.
    // Произвести проверку на существование пользователя по id, принадлежность commentatorId к текущему пользователю
    @Transactional(readOnly = false)
    public CommentFullDto updateComment(Long userId, CommentUpdateDto commentUpdateDto) {
        // TODO Добавить проверку корректности введенных данных

        if (Boolean.FALSE.equals(userRepository.existsById(userId))) {
            throw new CustomNotFoundException("User not found");
        }
        Comment comment = commentRepository.findById(commentUpdateDto.getId())
                .orElseThrow(() -> new CustomNotFoundException("Comment not found"));
        if (Boolean.FALSE.equals(comment.getCommentator().getId().equals(userId))) {
            throw new ValidationException("Commentator is not current user");
        }
        Event event = eventRepository.findById(commentUpdateDto.getEventId())
                .orElseThrow(() -> new CustomNotFoundException("Event not found"));
        if (Boolean.FALSE.equals(event.getId().equals(commentUpdateDto.getEventId()))) {
            throw new ValidationException("Event ID don't match");
        }
        commentMapper.updateComment(commentUpdateDto, comment);
        Comment readyComment = commentRepository.save(comment);
        return commentMapper.toCommentFullDto(readyComment, readyComment.getCommentator().getId(), readyComment.getEvent().getId());
    }

    // Удаление комментария по id
    @Transactional(readOnly = false)
    public void removeCommentById(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomNotFoundException("Comment not found"));
        if (Boolean.FALSE.equals(comment.getCommentator().getId().equals(userId))) {
            throw new ValidationException("Commentator is not current user");
        }
        commentRepository.deleteById(commentId);
    }

    // Просмотр всех комментариев текущего пользователя
    @Transactional
    @GetMapping("/users/{userId}/comments")
    public List<CommentFullDto> getAllCommentsOfCurrentUser(Long userId) {
        List<Comment> comments = commentRepository.findAllByCommentatorId(userId);
        return comments.stream()
                .map(e -> commentMapper.toCommentFullDto(e, e.getCommentator().getId(), e.getEvent().getId()))
                .collect(Collectors.toList());
    }
}
