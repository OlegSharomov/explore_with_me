package ru.practicum.compilations.repository;

import ru.practicum.compilations.entity.Compilation;

import java.util.List;

public interface CustomCompilationRepository {

    Compilation findCompilationWIthAllFieldsById(Long compId);

    Compilation findCompilationOnlyWithEventFieldById(Long compId);

    Compilation findCompilationWithoutFieldsById(Long compId);

    List<Compilation> findListCompilations(Boolean pinned, int from, int size);
}
