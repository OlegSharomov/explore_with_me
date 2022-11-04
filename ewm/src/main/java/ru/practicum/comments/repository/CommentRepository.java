package ru.practicum.comments.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentStatus;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommentatorId(Long userId);

    List<Comment> findAllByEventIdAndStatus(Long eventId, CommentStatus status, Pageable pageable);

    List<Comment> findAllByEventIdInAndStatus(List<Long> listEventId, CommentStatus status, Pageable pageable);

    boolean existsByCommentatorIdAndEventId(Long commentatorId, Long eventId);
}
