package ru.practicum.categories.publ.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPublicService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(0/20)
                List < Category > list = categoryRepository.findAll()
    }

    public CategoryDto getCategoryById(Integer catId) {

    }
}
