package ru.practicum.comments.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.comments.entity.Comment;
import ru.practicum.comments.model.CommentSort;
import ru.practicum.comments.model.CommentStatus;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.exception.ValidationException;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final EntityManager em;

    @Override
    public List<Comment> getAllCommentsOfAllEventsByInitiatorId(Long initiatorId, CommentSort sort,
                                                                Integer from, Integer size) {
        List<Long> ids;
        if (sort.equals(CommentSort.EVENTS)) {
            ids = em.createQuery("" +
                            "SELECT comment.id " +
                            "FROM Comment AS comment " +
                            "WHERE comment.event.initiator.id = ?1 AND comment.status = ?2 " +
                            "ORDER BY comment.event.id", Long.class)
                    .setParameter(1, initiatorId)
                    .setParameter(2, CommentStatus.PUBLISHED)
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        } else if (sort.equals(CommentSort.CREATED_ON)) {
            ids = em.createQuery("" +
                            "SELECT comment.id " +
                            "FROM Comment AS comment " +
                            "WHERE comment.event.initiator.id = ?1 AND comment.status = ?2 " +
                            "ORDER BY comment.createdOn", Long.class)
                    .setParameter(1, initiatorId)
                    .setParameter(2, CommentStatus.PUBLISHED)
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        } else {
            throw new ValidationException("parameter SORT is wrong");
        }
        return em.createQuery("" +
                        "SELECT comment " +
                        "FROM Comment AS comment " +
                        "WHERE comment.id IN ?1", Comment.class)
                .setParameter(1, ids)
                .getResultList();
    }

    @Override
    public Comment findCommentByIdWithoutRelatedFields(Long commentId) {
        try {
            return em.createQuery("" +
                            "SELECT comment " +
                            "FROM Comment AS comment " +
                            "WHERE comment.id = ?1", Comment.class)
                    .setParameter(1, commentId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Comment not found");
        }
    }

    @Override
    public List<Comment> findAllCommentsByCommentatorId(Long commentatorId) {
        return em.createQuery("" +
                        "SELECT comment " +
                        "FROM Comment AS comment " +
                        "WHERE comment.commentator.id = ?1", Comment.class)
                .setParameter(1, commentatorId)
                .getResultList();
    }

    @Override
    public List<Comment> findAllCommentsOfEvent(Long eventId, Integer from, Integer size) {
        List<Long> ids = em.createQuery("" +
                        "SELECT comment.id " +
                        "FROM Comment AS comment " +
                        "WHERE comment.event.id = ?1 AND comment.status = ?2 " +
                        "ORDER BY comment.createdOn", Long.class)
                .setParameter(1, eventId)
                .setParameter(2, CommentStatus.PUBLISHED)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        return em.createQuery("" +
                        "SELECT comment " +
                        "FROM Comment AS comment " +
                        "WHERE comment.id IN ?1", Comment.class)
                .setParameter(1, ids)
                .getResultList();
    }
}
