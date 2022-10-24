package ru.practicum.compilations.service.admin;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

public interface CompilationAdminService {
    // Добавление новой подборки. Возвращает CompilationDto.
    CompilationDto createNewCompilation(NewCompilationDto newCompilationDto);

    // Удаление подборки. Возвращает только статус ответа или ошибку.
    void removeCompilation(Integer compId);

    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    void removeEventFromCompilation(Integer compId, Integer eventId);

    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    void addEventInCompilation(Integer compId, Integer eventId);

    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    void unpinCompilation(Integer compId);

    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    void pinCompilation(Integer compId);
}
