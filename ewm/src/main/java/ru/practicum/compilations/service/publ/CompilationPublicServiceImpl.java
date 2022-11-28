package ru.practicum.compilations.service.publ;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.collector.CollectorDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.compilations.repository.CustomCompilationRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CustomCompilationRepository customCompilationRepository;
    private final CollectorDto collectorDto;

    @Override
    @Transactional
    //Получение подборок событий.
    // Искать только закрепленные/не закрепленные подборки
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilationList = customCompilationRepository.findListCompilations(pinned, from, size);
        return collectorDto.getListCompilationDto(compilationList);
    }

    @Override
    @Transactional
    // Получение подборки событий по id.
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        Compilation compilation = customCompilationRepository.findCompilationWIthAllFieldsById(compId);
        return collectorDto.getCompilationDto(compilation);
    }
}
