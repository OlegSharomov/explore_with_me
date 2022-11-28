package ru.practicum.compilations.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.collector.CollectorDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationMapperImpl;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.compilations.repository.CustomCompilationRepository;
import ru.practicum.compilations.service.publ.CompilationPublicServiceImpl;
import ru.practicum.events.dto.EventMapperImpl;
import ru.practicum.events.dto.publ.EventShortDto;
import ru.practicum.events.entity.Event;
import ru.practicum.exception.CustomNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompilationPublicServiceTest {
    @InjectMocks
    private CompilationPublicServiceImpl compilationService;
    @Mock
    private CustomCompilationRepository customCompilationRepository;
    @Spy
    private CompilationMapperImpl compilationMapper;
    @Spy
    private EventMapperImpl eventMapper;
    @Mock
    private CollectorDto collectorDto;

    Boolean pinned = false;
    Integer from = 0;
    Integer size = 10;
    Event event1 = Event.builder().id(1L).build();
    Event event2 = Event.builder().id(2L).build();
    Compilation compilation1 = Compilation.builder().id(1L)
            .events(List.of(event1, event2)).pinned(false).title("Weekend").build();
    Compilation compilation2 = Compilation.builder().id(2L)
            .events(List.of(event1)).pinned(false).title("Halloween").build();

    EventShortDto eventShortDto1 = EventShortDto.builder().id(1L).build();
    EventShortDto eventShortDto2 = EventShortDto.builder().id(2L).build();
    CompilationDto compilationDto1 = CompilationDto.builder()
            .id(1L).events(List.of(eventShortDto1, eventShortDto2)).pinned(false).title("Weekend").build();
    CompilationDto compilationDto2 = CompilationDto.builder()
            .id(2L).events(List.of(eventShortDto1)).pinned(false).title("Halloween").build();


    // getCompilations
    @Test
    public void shouldGetCompilations() {
        when(customCompilationRepository.findListCompilations(pinned, from, size))
                .thenReturn(List.of(compilation1, compilation2));
        when(collectorDto.getListCompilationDto(List.of(compilation1, compilation2)))
                .thenReturn(List.of(compilationDto1, compilationDto2));
        List<CompilationDto> result = compilationService.getCompilations(pinned, from, size);
        List<CompilationDto> listToCheck = List.of(compilationDto1, compilationDto2);
        assertEquals(listToCheck, result);
    }

    // getCompilationById
    @Test
    public void shouldGetCompilationById() {
        when(customCompilationRepository.findCompilationWIthAllFieldsById(1L))
                .thenReturn(compilation1);
        when(collectorDto.getCompilationDto(any(Compilation.class))).thenReturn(compilationDto1);
        CompilationDto result = compilationService.getCompilationById(1L);
        assertEquals(compilationDto1, result);
    }

    @Test
    public void shouldThrowExceptionWhenCompilationNotFound() {
        when(customCompilationRepository.findCompilationWIthAllFieldsById(99L))
                .thenThrow(new CustomNotFoundException("Compilation not found"));
        RuntimeException re = Assertions.assertThrows(CustomNotFoundException.class,
                () -> compilationService.getCompilationById(99L));
        assertEquals("Compilation not found", re.getMessage());
    }
}
