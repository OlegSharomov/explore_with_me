package ru.practicum.categories.controller.admin;

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
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.service.admin.CategoryAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class CategoryAdminController {
    private final CategoryAdminService adminService;

    @PatchMapping
    // Изменение категории. Возвращает CategoryDto.
    /* Обратите внимание: имя категории должно быть уникальным*/
    public CategoryDto changeCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Received a request: PATCH /admin/categories with body: " + categoryDto);
        return adminService.changeCategory(categoryDto);
    }

    @PostMapping
    // Добавление новой категории. Возвращает CategoryDto.
    /* Обратите внимание: имя категории должно быть уникальным*/
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Received a request: POST /admin/categories with body: " + newCategoryDto);
        return adminService.createCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    // Удаление категории. Возвращает только статус выполнения или ошибку.
    /* Обратите внимание: с категорией не должно быть связано ни одного события.*/
    public void removeCategory(@Positive @PathVariable Integer catId) {
        log.info("Received a request: DELETE /admin/categories/{}", catId);
        adminService.removeCategory(catId);
    }

}
