package com.sandipsky.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sandipsky.expense_tracker.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    boolean existsByCategoryId(Integer categoryId);
}
