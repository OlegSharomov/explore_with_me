package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.entity.AdditionalFields;
import ru.practicum.entity.Statistic;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.vievstatsshort.ViewStatsShort;
import ru.practicum.model.vievstatsshort.ViewStatsShortInterface;
import ru.practicum.model.viewstats.ViewStats;
import ru.practicum.model.viewstats.ViewStatsInterface;
import ru.practicum.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    // Сохранение информации о том, что к эндпоинту был запрос. Возвращает только статус ответа.
    /* Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
    Название сервиса, uri и ip пользователя указаны в теле запроса.*/
    @Override
    @Transactional(readOnly = false)
    public void save(EndpointHit endpointHit) {
        AdditionalFields additionalFields = new AdditionalFields(endpointHit.getApp(), endpointHit.getIp());
        Statistic statistic = Statistic.builder()
                .uri(endpointHit.getUri())
                .createdAt(endpointHit.getTimestamp())
                .additionalFields(additionalFields)
                .build();
        statisticRepository.save(statistic);
    }

    // Получение статистики по посещениям. Возвращает список ViewStats.
    /* Обратите внимание: значение даты и времени нужно закодировать (Например, используя java.net.URLEncoder.encode).*/
    @Override
    @Transactional
    public List<ViewStats> getStats(LocalDateTime start,  // Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                                    LocalDateTime end,    // Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                                    String[] uris,        // Список uri для которых нужно выгрузить статистику
                                    Boolean unique) {     // Нужно ли учитывать только уникальные посещения (только с уникальным ip)
        if (uris.length == 0) {
            return Collections.emptyList();
        }
        List<String> listUris = Arrays.stream(uris).collect(Collectors.toList());
        List<ViewStatsInterface> viewStatsInterfacesList;
        if (unique) {
            viewStatsInterfacesList = statisticRepository.getStatisticWithUniqueIp(listUris, start, end);
        } else {
            viewStatsInterfacesList = statisticRepository.getStatisticWithoutUniqueIp(listUris, start, end);
        }
        return viewStatsInterfacesList.stream()
                .map(i -> new ViewStats(i.getApp(), i.getUri(), i.getHits()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long getViews(String uri) {
        return statisticRepository.countByUri(uri).orElse(0L);
    }

    @Override
    @Transactional
    public List<ViewStatsShort> getViewsForCollect(String[] uris) {
        List<String> listUris = Arrays.stream(uris).collect(Collectors.toList());
        List<ViewStatsShortInterface> shortInterfaceList = statisticRepository.getStatisticForCollect(listUris);
        return shortInterfaceList.stream().map(e -> new ViewStatsShort(e.getUri(), e.getHits())).collect(Collectors.toList());
    }
}
