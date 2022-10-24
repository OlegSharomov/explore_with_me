package ru.practicum.compilations.service.publ;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.compilations.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    //Получение подборок событий. Возвращает список CompilationDto.
    // Искать только закрепленные/не закрепленные подборки
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    // Получение подборки событий по id. Возвращает CompilationDto
    CompilationDto getCompilationById(@PathVariable Integer compId);
}
