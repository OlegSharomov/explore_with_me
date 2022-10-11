package ru.practicum.categories.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = false)
    public CategoryDto changeCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException());
        categoryMapper.updateCategory(categoryDto, category);
        categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.toCategory(newCategoryDto);
        Category readyCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(readyCategory);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeCategory(Integer catId) {
        // TODO Обратите внимание: с категорией не должно быть связано ни одного события
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public boolean isCategoryExistsById(Integer categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getEntityCategoryById(Integer catId){
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException());
    }
}
