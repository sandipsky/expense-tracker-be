package com.sandipsky.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sandipsky.expense_tracker.dto.DropdownDTO;
import com.sandipsky.expense_tracker.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, int id);

    @Query("""
                SELECT new com.sandipsky.expense_tracker.dto.DropdownDTO(c.id, c.name)
                FROM Account c
                WHERE (:isActive IS NULL OR c.isActive = :isActive)
            """)
    List<DropdownDTO> findFilteredDropdown(
            Boolean isActive);
}
