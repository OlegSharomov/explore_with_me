package ru.practicum.compilations.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.collector.CollectorDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapper;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.compilations.repository.CustomCompilationRepository;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.CustomEventRepository;
import ru.practicum.exception.CustomNotFoundException;
import ru.practicum.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final CustomCompilationRepository customCompilationRepository;
    private final CustomEventRepository customEventRepository;
    private final CompilationMapper compilationMapper;
    private final CollectorDto collectorDto;

    // Добавление новой подборки.
    @Override
    @Transactional(readOnly = false)
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        List<Event> eventEntities;
        if (newCompilationDto.getEvents() == null || newCompilationDto.getEvents().isEmpty()) {
            eventEntities = Collections.emptyList();
        } else {
            eventEntities = customEventRepository.findAllEvents(newCompilationDto.getEvents());
        }
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto, eventEntities);
        Compilation readyCompilation = compilationRepository.save(compilation);
        return collectorDto.getCompilationDto(readyCompilation);
    }

    // Удаление подборки.
    @Override
    @Transactional(readOnly = false)
    public void removeCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    @Override
    @Transactional(readOnly = false)
    public void removeEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = customCompilationRepository.findCompilationOnlyWithEventFieldById(compId);
        List<Event> events = new ArrayList<>(compilation.getEvents());
        Event event = events.stream().filter(e -> e.getId().equals(eventId)).findFirst()
                .orElseThrow(() -> new CustomNotFoundException("Event not found in the compilation list"));
        events.remove(event);
        compilation.setEvents(events);
        Compilation readyCompilation = compilationRepository.save(compilation);
        System.out.println(readyCompilation);
    }

    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    @Override
    @Transactional(readOnly = false)
    public void addEventInCompilation(Long compId, Long eventId) {
        Compilation compilation = customCompilationRepository.findCompilationWIthAllFieldsById(compId);
        Event event;
        event = customEventRepository.findEventById(eventId);
        if (compilation.getEvents().contains(event)) {
            throw new ValidationException("Event already exists in compilation");
        }
        List<Event> events = new ArrayList<>(compilation.getEvents());
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Override
    @Transactional(readOnly = false)
    public void unpinCompilation(Long compId) {
        Compilation compilation = customCompilationRepository.findCompilationWithoutFieldsById(compId);
        if (compilation.getPinned().equals(false)) {
            throw new ValidationException("Pinned already false");
        }
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Override
    @Transactional(readOnly = false)
    public void pinCompilation(Long compId) {
        Compilation compilation = customCompilationRepository.findCompilationWithoutFieldsById(compId);
        if (compilation.getPinned().equals(true)) {
            throw new ValidationException("Pinned already false");
        }
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
