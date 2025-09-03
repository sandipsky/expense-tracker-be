package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.CategoryDTO;
import com.sandipsky.expense_tracker.entity.Category;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be null or blank");
        }
        if (repository.existsByName(categoryDTO.getName().trim())) {
            throw new DuplicateResourceException("Category with the same name already exists");
        }
        Category category = new Category();
        mapDtoToEntity(categoryDTO, category);
        return repository.save(category);
    }

    public List<Category> getCategorys() {
        return repository.findAll();
    }

    public CategoryDTO getCategoryById(int id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapToDTO(category);
    }

    public Category updateCategory(int id, CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be null or blank");
        }

        if (repository.existsByNameAndIdNot(categoryDTO.getName().trim(), id)) {
            throw new DuplicateResourceException("Category with the same name already exists");
        }

        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        mapDtoToEntity(categoryDTO, category);
        return repository.save(category);
    }

    public void deleteCategory(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        repository.deleteById(id);
    }

    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setColorCode(category.getColorCode());
        dto.setDescription(category.getDescription());
        dto.setIsActive(category.getIsActive());
        dto.setType(category.getType());
        return dto;
    }

    private void mapDtoToEntity(CategoryDTO dto, Category category) {
        category.setName(dto.getName().trim());
        category.setIsActive(dto.getIsActive());
        category.setColorCode(dto.getColorCode());
        category.setDescription(dto.getDescription());
        category.setIsActive(dto.getIsActive());
        category.setType(dto.getType());
    }
}
