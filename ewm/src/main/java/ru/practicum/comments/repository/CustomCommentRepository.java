package ru.practicum.comments.repository;

import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentSort;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> getAllCommentsOfAllEventsByInitiatorId(Long initiatorId, CommentSort sort,
                                                         Integer from, Integer size);

    Comment findCommentByIdWithoutRelatedFields(Long commentId);

    List<Comment> findAllCommentsByCommentatorId(Long commentatorId);

    List<Comment> findAllCommentsOfEvent(Long eventId, Integer from, Integer size);
}
