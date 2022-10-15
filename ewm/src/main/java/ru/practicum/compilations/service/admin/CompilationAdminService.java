package ru.practicum.compilations.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapper;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.compilations.repository.CompilationRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
//    private final EventRepository eventRepository;

    // Добавление новой подборки. Возвращает CompilationDto.
    //TODO реализовать логгику сервиса
    @Transactional(readOnly = false)
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        // TODO в newCompilationDto взять List<Integer> events, перевести его в List<Event> events и присвоить его compilation
        // пройтись по списку и вернуть для каждого id event или выбросить ошибку
        // присвоить его compilation
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto, Collections.EMPTY_LIST);// подставить лист
        Compilation readyCompilation = compilationRepository.save(compilation);
        // перегнать лист events под EventShortDto и подогнать ответ под тело CompilationDto
        return compilationMapper.toCompilationDto(readyCompilation, Collections.EMPTY_LIST);// подставить лист
    }

    // Удаление подборки. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void removeCompilation(Integer compId) {
        compilationRepository.deleteById(compId);

    }

    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void removeEventFromCompilation(Integer compId, Integer eventId) {

    }

    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void addEventInCompilation(Integer compId, Integer eventId) {

    }

    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void unpinCompilation(Integer compId) {

    }

    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    @Transactional(readOnly = false)
    public void pinCompilation(Integer compId) {

    }

}
