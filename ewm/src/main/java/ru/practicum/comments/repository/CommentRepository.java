package ru.practicum.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.entity.Comment;
import ru.practicum.events.entity.Event;
import ru.practicum.users.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByCommentatorAndEvent(User commentator, Event event);

}
