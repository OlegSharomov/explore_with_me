package ru.practicum.categories.admin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

import javax.validation.Valid;

@RestController("/admin/categories")
public class CategoriesAdminController {

    @PatchMapping
    // Изменение категории. Возвращает CategoryDto.
    /* Обратите внимание: имя категории должно быть уникальным*/
    public void changeCategory(@Valid @RequestBody CategoryDto categoryDto) {

    }

    @PostMapping
    // Добавление новой категории. Возвращает CategoryDto.
    /* Обратите внимание: имя категории должно быть уникальным*/
    public void createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {

    }

    @DeleteMapping("/{catId}")
    // Удаление категории. Возвращает только статус выполнения или ошибку.
    /* Обратите внимание: с категорией не должно быть связано ни одного события.*/
    public void removeCategory(@PathVariable Integer catId) {

    }

}
