package ru.practicum.compilations.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.service.admin.CompilationAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class CompilationAdminController {
    private final CompilationAdminService compilationAdminService;

    @PostMapping
    // Добавление новой подборки. Возвращает CompilationDto.
    public CompilationDto createNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Received a request POST /admin/compilations with body: {}", newCompilationDto);
        return compilationAdminService.createNewCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    // Удаление подборки. Возвращает только статус ответа или ошибку.
    public void removeCompilation(@Positive @PathVariable Integer compId) {
        log.info("Received a request DELETE /admin/compilations/{}", compId);
        compilationAdminService.removeCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    public void removeEventFromCompilation(@Positive @PathVariable Integer compId,
                                           @Positive @PathVariable Integer eventId) {
        log.info("Received a request DELETE /admin/compilations/{}/events/{}", compId, eventId);
        compilationAdminService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    public void addEventInCompilation(@Positive @PathVariable Integer compId,
                                      @Positive @PathVariable Integer eventId) {
        log.info("Received a request PATCH /admin/compilations/{}/events/{}", compId, eventId);
        compilationAdminService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    public void unpinCompilation(@Positive @PathVariable Integer compId) {
        log.info("Received a request DELETE /admin/compilations/{}/pin", compId);
        compilationAdminService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    public void pinCompilation(@Positive @PathVariable Integer compId) {
        log.info("Received a request PATCH /admin/compilations/{}/pin", compId);
        compilationAdminService.pinCompilation(compId);
    }
}
