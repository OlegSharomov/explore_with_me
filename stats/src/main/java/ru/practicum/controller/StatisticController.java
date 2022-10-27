package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.service.StatisticService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/hit")
    // Сохранение информации о том, что к эндпоинту был запрос. Возвращает только статус ответа.
    /* Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
    Название сервиса, uri и ip пользователя указаны в теле запроса.*/
    public void save(@NotNull @RequestBody EndpointHit endpointHit) {
        log.info("Received a request: POST /hit with body: {}", endpointHit);
        statisticService.save(endpointHit);
    }

    @GetMapping("/stats")
    // Получение статистики по посещениям. Возвращает список ViewStats.
    /* Обратите внимание: значение даты и времени нужно закодировать (Например, используя java.net.URLEncoder.encode).*/
    public List<ViewStats> getStatistic(
            @RequestParam
            LocalDateTime start, // Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
            @RequestParam
            LocalDateTime end,   // Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
            @RequestParam(required = false) String[] uris,       // Список uri для которых нужно выгрузить статистику
            @RequestParam(defaultValue = "false") Boolean unique) { // Нужно ли учитывать только уникальные посещения (только с уникальным ip)
        log.info("Received a request: GET /stats with parameters: start = {}, end = {}, uris = {}, unique = {}",
                start, end, uris, unique);
        return statisticService.getStats(start, end, uris, unique);
    }

//    @GetMapping("/events")
//    public Integer getViews(@NotEmpty(message = "request empty") @RequestBody String uri) {
//        log.info("Received a request: GET /events with body: {}", uri);
//        return statisticService.getViews(uri);
//    }
    @GetMapping("/events/{eventId}")
    public Integer getViews(@NotNull(message = "Controller get request to path '/events/{eventId}' where eventId==null")
                            @PathVariable Integer eventId) {
        log.info("Received a request: GET /events with pathVariable: {}", eventId);
        return statisticService.getViews("/events/" + eventId);
    }
}