package ru.practicum.compilations.admin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilations.dto.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    @PostMapping
    // Добавление новой подборки. Возвращает CompilationDto.
    public void createNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {

    }

    @DeleteMapping("/{compId}")
    // Удаление подборки. Возвращает только статус ответа или ошибку.
    public void removeCompilation(@PathVariable Integer compId) {

    }

    @DeleteMapping("/{compId}/events/{eventId}")
    // Удалить событие из подборки. Возвращает только статус ответа или ошибку.
    public void removeEventFromCompilation(@PathVariable Integer compId,
                                           @PathVariable Integer eventId) {

    }

    @PatchMapping("/{compId}/events/{eventId}")
    // Добавить событие в подборку. Возвращает только статус ответа или ошибку.
    public void addEventInCompilation(@PathVariable Integer compId,
                                      @PathVariable Integer eventId) {

    }

    @DeleteMapping("/{compId}/pin")
    // Открепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    public void unpinCompilation(@PathVariable Integer compId) {

    }

    @PatchMapping("/{compId}/pin")
    // Закрепить подборку на главной странице. Возвращает только статус ответа или ошибку.
    public void pinCompilation(@PathVariable Integer compId) {

    }

}
