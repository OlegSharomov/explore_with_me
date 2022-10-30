package ru.practicum.compilations.service.admin;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

public interface CompilationAdminService {
    CompilationDto createNewCompilation(NewCompilationDto newCompilationDto);

    void removeCompilation(Integer compId);

    void removeEventFromCompilation(Integer compId, Integer eventId);

    void addEventInCompilation(Integer compId, Integer eventId);

    void unpinCompilation(Integer compId);

    void pinCompilation(Integer compId);
}
