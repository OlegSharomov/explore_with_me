package ru.practicum.requests.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.requests.entity.Request;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomRequestRepositoryImpl implements CustomRequestRepository {
    private final EntityManager em;

    @Override
    public Request findRequestByIdOnlyWithEvent(Long requestId) {
        try {
            return em.createQuery("" +
                            "SELECT req " +
                            "FROM Request AS req " +
                            "LEFT JOIN FETCH req.event AS event " +
                            "WHERE req.id = ?1 ", Request.class)
                    .setParameter(1, requestId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Request not found");
        }
    }

    @Override
    public Request findRequestByIWithoutRelatedFields(Long requestId) {
        try {
            return em.createQuery("" +
                            "SELECT req " +
                            "FROM Request AS req " +
                            "WHERE req.id = ?1 ", Request.class)
                    .setParameter(1, requestId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Request not found");
        }
    }

    @Override
    public List<Request> findAllRequestsWithoutRelatedFieldsByRequesterId(Long userId) {
        return em.createQuery("" +
                        "SELECT req " +
                        "FROM Request AS req " +
                        "WHERE req.requester.id = ?1", Request.class)
                .setParameter(1, userId)
                .getResultList();
    }
}
