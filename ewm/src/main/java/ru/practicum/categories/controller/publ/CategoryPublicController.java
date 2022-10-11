package ru.practicum.categories.controller.publ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.publ.CategoryPublicService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {
    private final CategoryPublicService categoryPublicService;

    @GetMapping
    // Получение категорий. Возвращает список CategoryDto.
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a request: GET /categories with parameters: from = {}, size = {}", from, size);
        return categoryPublicService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    // Получение информации о категории по ее id. Возвращает CategoryDto.
    public CategoryDto getCategoryById(@PathVariable Integer catId) {
        log.info("Received a request: GET /categories/{}", catId);
        return categoryPublicService.getCategoryById(catId);
    }
}
