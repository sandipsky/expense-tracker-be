package com.sandipsky.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sandipsky.expense_tracker.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
