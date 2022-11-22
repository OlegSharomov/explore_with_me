package ru.practicum.comments.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.comments.censorship.Censorship;
import ru.practicum.comments.dto.CommentAdminUpdateDto;
import ru.practicum.comments.dto.CommentFullDto;
import ru.practicum.comments.dto.CommentMapper;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.comments.repository.CustomCommentRepository;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.users.entity.User;
import ru.practicum.users.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentAdminService {
    private final CommentRepository commentRepository;
    private final CustomCommentRepository customCommentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;
    private final Censorship censorship;

    //+
    // Редактирование комментария.
    /* Редактирование данных любого события администратором. Валидация данных не требуется. */
    @Transactional(readOnly = false)
    public CommentFullDto updateCommentByAdmin(Long commentId, CommentAdminUpdateDto commentAdminUpdateDto) {
        Comment comment = customCommentRepository.findCommentByIdWithoutRelatedFields(commentId);
        User commentator = comment.getCommentator();
        if (Boolean.FALSE.equals(comment.getCommentator().getId().equals(commentAdminUpdateDto.getCommentatorId()))) {
            commentator = userRepository.findById(commentAdminUpdateDto.getCommentatorId())
                    .orElseThrow(() -> new CustomNotFoundException(String
                            .format("User with id=%d not found", commentAdminUpdateDto.getCommentatorId())));
        }
        Event event = comment.getEvent();
        if (Boolean.FALSE.equals(comment.getEvent().getId().equals(commentAdminUpdateDto.getEventId()))) {
            event = eventRepository.findById(commentAdminUpdateDto.getEventId())
                    .orElseThrow(() -> new CustomNotFoundException(String
                            .format("Event with id=%d not found", commentAdminUpdateDto.getEventId())));
        }
        commentMapper.updateCommentFromAdmin(commentAdminUpdateDto, comment, commentator, event);
        Comment readyComment = commentRepository.save(comment);
        return commentMapper.toCommentFullDto(readyComment, readyComment.getCommentator().getId(),
                readyComment.getEvent().getId());
    }

    //+
    // Удаление комментария
    @Transactional(readOnly = false)
    public void removeCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // Посмотреть все стоп-слова
    public String[] showAllStopWords() {
        return censorship.showAllStopWords();
    }

    public String[] addStopWord(@RequestParam String stopWord) {
        return censorship.addStopWord(stopWord);
    }

    public String[] removeStopWord(@RequestParam String stopWord) {
        return censorship.removeFromStopWords(stopWord);
    }
}
