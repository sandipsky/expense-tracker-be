package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.BudgetDTO;
import com.sandipsky.expense_tracker.entity.Budget;
import com.sandipsky.expense_tracker.entity.Category;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.BudgetRepository;
import com.sandipsky.expense_tracker.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Budget saveBudget(BudgetDTO budgetDTO) {
        if (budgetDTO.getCategoryId() == null) {
            throw new RuntimeException("Category is required for a budget");
        }
        if (budgetDTO.getPeriod() == null) {
            throw new RuntimeException("Period is required for a budget");
        }

        if (repository.existsByCategoryId(budgetDTO.getCategoryId())) {
            throw new DuplicateResourceException("Budget already exists for this category");
        }

        Budget budget = new Budget();
        mapDtoToEntity(budgetDTO, budget);
        return repository.save(budget);
    }

    public List<BudgetDTO> getBudgets() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BudgetDTO getBudgetById(int id) {
        Budget budget = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
        return mapToDTO(budget);
    }

    public Budget updateBudget(int id, BudgetDTO budgetDTO) {
        Budget budget = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        if (!budget.getCategory().getId().equals(budgetDTO.getCategoryId())) {
            if (repository.existsByCategoryId(budgetDTO.getCategoryId())) {
                throw new DuplicateResourceException("Budget already exists for this category");
            }
        }

        mapDtoToEntity(budgetDTO, budget);
        return repository.save(budget);
    }

    public void deleteBudget(int id) {
        Budget budget = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
        repository.delete(budget);
    }

    private BudgetDTO mapToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setAmount(budget.getAmount());
        dto.setRemarks(budget.getRemarks());
        dto.setCategoryId(budget.getCategory().getId());
        dto.setCategoryName(budget.getCategory().getName());
        dto.setPeriod(budget.getPeriod());
        dto.setNotificationThreshold(budget.getNotificationThreshold());
        return dto;
    }

    private void mapDtoToEntity(BudgetDTO dto, Budget budget) {
        budget.setAmount(dto.getAmount() != null ? dto.getAmount() : 0.0);
        budget.setRemarks(dto.getRemarks());
        budget.setNotificationThreshold(dto.getNotificationThreshold());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            budget.setCategory(category);
        }

        if (dto.getPeriod() != null) {
            budget.setPeriod(dto.getPeriod());
        }
    }
}
