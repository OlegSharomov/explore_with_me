package ru.practicum.compilations.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatisticClient;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapper;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.events.dto.EventMapper;
import ru.practicum.events.entity.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;

import java.util.List;
import java.util.Optional;

import static ru.practicum.util.UtilCollectorsDto.getCompilationDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final StatisticClient statisticClient;
    private final EventMapper eventMapper;

    // Добавление новой подборки. Возвращает CompilationDto.
    //TODO реализовать логгику сервиса
    @Transactional(readOnly = false)
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        // TODO в newCompilationDto взять List<Integer> events, перевести его в List<Event> events и присвоить его compilation
        // пройтись по списку и вернуть для каждого id event или выбросить ошибку
        // присвоить его compilation
        List<Event> eventEntities = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto, eventEntities);// подставить лист
        Compilation readyCompilation = compilationRepository.save(compilation);
        return getCompilationDto(readyCompilation, statisticClient, eventMapper, compilationMapper);
    }

    // Удаление подборки. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void removeCompilation(Integer compId) {
        compilationRepository.deleteById(compId);
    }

    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void removeEventFromCompilation(Integer compId, Integer eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        List<Event> events = compilation.getEvents();
        Event event = events.stream().filter(e -> e.getId().equals(eventId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Event not found in the compilation list"));
        events.remove(event);
        compilationRepository.save(compilation);
    }

    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void addEventInCompilation(Integer compId, Integer eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        Optional<Event> optionalEventFromList = compilation.getEvents().stream().filter(e -> e.getId().equals(eventId)).findFirst();
        if (optionalEventFromList.isPresent()) {
            throw new ValidationException("Event already exists in compilation");
        }
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void unpinCompilation(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        if (compilation.getPinned().equals(false)) {
            throw new ValidationException("Pinned already false");
        }
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void pinCompilation(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        if (compilation.getPinned().equals(true)) {
            throw new ValidationException("Pinned already false");
        }
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
