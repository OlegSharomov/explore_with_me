package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.vievstatsshort.ViewStatsShort;
import ru.practicum.model.viewstats.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    @Transactional(readOnly = false)
    void save(EndpointHit endpointHit);

    @Transactional
    List<ViewStats> getStats(LocalDateTime start,
                             LocalDateTime end,
                             String[] uris,
                             Boolean unique);

    Long getViews(String uri);

    List<ViewStatsShort> getViewsForCollect(String[] uris);
}
