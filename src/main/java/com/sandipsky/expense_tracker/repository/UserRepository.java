package com.sandipsky.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sandipsky.expense_tracker.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
