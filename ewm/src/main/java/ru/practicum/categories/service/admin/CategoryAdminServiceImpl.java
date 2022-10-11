package ru.practicum.categories.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryAdminService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto changeCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException());
        categoryMapper.updateCategory(categoryDto, category);
        categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.toCategory(newCategoryDto);
        Category readyCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(readyCategory);
    }

    public void removeCategory(Integer catId) {
        // TODO Обратите внимание: с категорией не должно быть связано ни одного события
        categoryRepository.deleteById(catId);
    }

    public boolean isCategoryExistsById(Integer categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}
