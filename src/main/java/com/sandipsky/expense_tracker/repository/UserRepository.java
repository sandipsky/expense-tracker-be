package com.sandipsky.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sandipsky.expense_tracker.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, int id);

    boolean existsByEmailAndIdNot(String email, int id);

    Optional<User> findByUsername(String username);
}
