package com.financetracker.services.Category;

import com.financetracker.dto.category.CategoryRequest;
import com.financetracker.dto.category.CategoryResponse;
import com.financetracker.entity.Category;
import com.financetracker.entity.User;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.CategoryRepository;
import com.financetracker.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getType(),
            category.getColor(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        User currentUser = UserUtils.getCurrentUser();

        Category category = new Category();
        category.setName(request.name());
        category.setType(request.type());
        category.setUser(currentUser);

        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        User currentUser = UserUtils.getCurrentUser();
        return categoryRepository.findAllByUser(currentUser)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {
        User currentUser = UserUtils.getCurrentUser();
        Category category = categoryRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setName(request.name());
        category.setType(request.type());

        Category updatedCategory = categoryRepository.save(category);
        return mapToResponse(updatedCategory);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        Category category = categoryRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        categoryRepository.delete(category);
    }

    public Category findCategoryEntity(UUID id, User user) {
        return categoryRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    }
}