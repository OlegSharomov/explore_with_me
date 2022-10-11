package ru.practicum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class StatsController {

    @PostMapping("/hit")
    // Сохранение информации о том, что к эндпоинту был запрос. Возвращает только статус ответа.
    /* Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
    Название сервиса, uri и ip пользователя указаны в теле запроса.*/
    public void save(@RequestBody EndpointHit endpointHit) {

    }

    @GetMapping("/stats")
    // Получение статистики по посещениям. Возвращает список ViewStats.
    /* Обратите внимание: значение даты и времени нужно закодировать (Например, используя java.net.URLEncoder.encode).*/
    public void getStats(@RequestParam LocalDateTime start, // Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                         @RequestParam LocalDateTime end,   // Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
                         @RequestParam String[] uris,       // Список uri для которых нужно выгрузить статистику
                         @RequestParam(defaultValue = "false") Boolean unique       // Нужно ли учитывать только уникальные посещения (только с уникальным ip)
    ){

    }

}
