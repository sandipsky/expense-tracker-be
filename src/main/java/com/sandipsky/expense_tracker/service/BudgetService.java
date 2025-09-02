package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.BudgetDTO;
import com.sandipsky.expense_tracker.entity.Budget;
import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.BudgetRepository;
import com.sandipsky.expense_tracker.repository.UserRepository;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Budget saveBudget(BudgetDTO budgetDTO) {
        if (budgetDTO.getName() == null || budgetDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Budget name cannot be null or blank");
        }
        if (repository.existsByName(budgetDTO.getName().trim())) {
            throw new DuplicateResourceException("Budget with the same name already exists");
        }
        Budget budget = new Budget();
        mapDtoToEntity(budgetDTO, budget);
        return repository.save(budget);
    }

    public List<Budget> getBudgets() {
        return repository.findAll();
    }

    public BudgetDTO getBudgetById(int id) {
        Budget budget = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
        return mapToDTO(budget);
    }

    public Budget updateBudget(int id, BudgetDTO budgetDTO) {
        if (budgetDTO.getName() == null || budgetDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Budget name cannot be null or blank");
        }

        if (repository.existsByNameAndIdNot(budgetDTO.getName().trim(), id)) {
            throw new DuplicateResourceException("Budget with the same name already exists");
        }

        Budget budget = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        mapDtoToEntity(budgetDTO, budget);
        return repository.save(budget);
    }

    public void deleteBudget(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
        repository.deleteById(id);
    }

    private BudgetDTO mapToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setName(budget.getName());
        dto.setColorCode(budget.getColorCode());
        dto.setDescription(budget.getDescription());
        dto.setIsActive(budget.getIsActive());
        dto.setType(budget.getType());
        dto.setUserId(budget.getUser() != null ? budget.getUser().getId() : null);
        dto.setUserName(budget.getUser() != null ? budget.getUser().getUsername() : null);
        return dto;
    }

    private void mapDtoToEntity(BudgetDTO dto, Budget budget) {
        budget.setName(dto.getName().trim());
        budget.setIsActive(dto.getIsActive());
        budget.setColorCode(dto.getColorCode());
        budget.setDescription(dto.getDescription());
        budget.setIsActive(dto.getIsActive());
        budget.setType(dto.getType());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
            budget.setUser(user);
        }
    }
}
