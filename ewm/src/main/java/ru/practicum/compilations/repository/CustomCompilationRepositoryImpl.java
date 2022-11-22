package ru.practicum.compilations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.exception.CustomNotFoundException;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCompilationRepositoryImpl implements CustomCompilationRepository {
    private final EntityManager em;

    @Override
    public Compilation findCompilationWIthAllFieldsById(Long compId) {
        try {
            return em.createQuery("SELECT com " +
                                    "FROM Compilation AS com " +
                                    "LEFT JOIN FETCH com.events AS ev " +
                                    "LEFT JOIN FETCH ev.initiator AS init " +
                                    "LEFT JOIN FETCH ev.category AS cat " +
                                    "WHERE com.id = ?1",
                            Compilation.class)
                    .setParameter(1, compId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Compilation not found");
        }
    }

    @Override
    public Compilation findCompilationOnlyWithEventFieldById(Long compId) {
        try {
            return em.createQuery("SELECT com " +
                                    "FROM Compilation AS com " +
                                    "LEFT JOIN FETCH com.events AS ev " +
                                    "WHERE com.id = ?1",
                            Compilation.class)
                    .setParameter(1, compId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Compilation not found");
        }
    }

    @Override
    public Compilation findCompilationWithoutFieldsById(Long compId) {
        try {
            return em.createQuery("SELECT com " +
                                    "FROM Compilation AS com " +
                                    "WHERE com.id = ?1",
                            Compilation.class)
                    .setParameter(1, compId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new CustomNotFoundException("Compilation not found");
        }
    }

    @Override
    public List<Compilation> findListCompilations(Boolean pinned, int from, int size) {
        List<Long> eventsId = em.createQuery(
                        "SELECT com.id " +
                                "FROM Compilation AS com " +
                                "WHERE com.pinned = ?1 " +
                                "ORDER BY com.id",
                        Long.class)
                .setParameter(1, pinned)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        return em.createQuery("SELECT com " +
                                "FROM Compilation AS com " +
                                "LEFT JOIN FETCH com.events AS ev " +
                                "LEFT JOIN FETCH ev.initiator AS init " +
                                "LEFT JOIN FETCH ev.category AS cat " +
                                "WHERE com.id IN ?1 " +
                                "ORDER BY com.id",
                        Compilation.class)
                .setParameter(1, eventsId)
                .getResultList();
    }
}
