package ru.practicum.categories.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/categories")
public class CategoriesPublicController {

    @GetMapping
    public void getCategories(@RequestParam(defaultValue = "0") Integer from,
                              @RequestParam(defaultValue = "10") Integer size) {
    }

    @GetMapping("/{catId}")
    public void getCategoryById(@PathVariable Integer catId) {

    }
}
