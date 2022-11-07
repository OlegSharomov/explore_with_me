package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.entity.Statistic;
import ru.practicum.model.vievstatsshort.ViewStatsShortInterface;
import ru.practicum.model.viewstats.ViewStatsInterface;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Optional<Long> countByUri(String uri);

    @Query(value =
            "SELECT additional_fields->>'app' AS app, " +
                    "uri AS uri," +
                    "count(distinct (additional_fields->>'ip')) AS hits " +
                    "FROM statistics " +
                    "WHERE uri IN ?1 " +
                    "AND (cast(created_at as date)) BETWEEN ?2 AND ?3 " +
                    "group by uri, additional_fields", nativeQuery = true)
    List<ViewStatsInterface> getStatisticWithUniqueIp(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query(value =
            "SELECT additional_fields->>'app' AS app, " +
                    "uri AS uri," +
                    "count(id) AS hits " +
                    "FROM statistics " +
                    "WHERE uri IN ?1 " +
                    "AND (cast(created_at as date)) BETWEEN ?2 AND ?3 " +
                    "group by uri, additional_fields", nativeQuery = true)
    List<ViewStatsInterface> getStatisticWithoutUniqueIp(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query(value =
            "SELECT uri AS uri," +
                    "count(id) AS hits " +
                    "FROM statistics " +
                    "WHERE uri IN ?1 " +
                    "group by uri", nativeQuery = true)
    List<ViewStatsShortInterface> getStatisticForCollect(List<String> uris);
}
