package ru.practicum.compilations.controller.publ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.publ.CompilationPublicService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationsPublicController {
    private final CompilationPublicService compilationPublicService;

    @GetMapping
    //Получение подборок событий. Возвращает список CompilationDto.
    // Искать только закрепленные/не закрепленные подборки
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                // Количество элементов, которые нужно пропустить для формирования текущего набора
                                                @RequestParam(defaultValue = "10") Integer from,
                                                // Количество элементов в наборе
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request GET /compilations");
        return compilationPublicService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    // Получение подборки событий по id. Возвращает CompilationDto
    public CompilationDto getCompilationById(@PathVariable Integer compId) {
        log.info("Received a request GET /compilations/{}", compId);
        return compilationPublicService.getCompilationById(compId);
    }
}
