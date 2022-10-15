package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.entity.AdditionalFields;
import ru.practicum.entity.Statistic;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository statisticRepository;

    // Сохранение информации о том, что к эндпоинту был запрос. Возвращает только статус ответа.
    /* Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
    Название сервиса, uri и ip пользователя указаны в теле запроса.*/
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
    @Transactional
    public List<ViewStats> getStats(LocalDateTime start,  // Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                                    LocalDateTime end,    // Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                                    String[] uris,        // Список uri для которых нужно выгрузить статистику
                                    Boolean unique) {     // Нужно ли учитывать только уникальные посещения (только с уникальным ip)
        if (uris.length == 0) {
            return Collections.emptyList();
        }
        List<ViewStats> readyStatistic = new ArrayList<>();
        if (unique) {
            for (String uri : uris) {
                Statistic statistic = statisticRepository.findByUri(uri).orElseThrow(() -> new NotFoundException());
                String app = statistic.getAdditionalFields().getApp();
                Integer views = statisticRepository.getStatisticWithUniqueIp(uri, app, start, end);
                ViewStats viewStats = ViewStats.builder()
                        .app(app)
                        .uri(uri)
                        .hits(views)
                        .build();
                readyStatistic.add(viewStats);
            }
        } else {
            for (String uri : uris) {
                Statistic statistic = statisticRepository.findByUri(uri).orElseThrow(() -> new NotFoundException());
                String app = statistic.getAdditionalFields().getApp();
                Integer views = statisticRepository.getStatisticWithoutUniqueIp(uri, app, start, end);
                ViewStats viewStats = ViewStats.builder()
                        .app(app)
                        .uri(uri)
                        .hits(views)
                        .build();
                readyStatistic.add(viewStats);
            }
        }
        return readyStatistic;
    }

    public Integer getViews(String uri) {
        Optional<Integer> views = statisticRepository.countByUri(uri);
        if (views.isEmpty()) {
            return 0;
        } else {
            return views.get();
        }
    }
}
