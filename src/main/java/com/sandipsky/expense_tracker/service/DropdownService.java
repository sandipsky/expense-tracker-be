package com.sandipsky.expense_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sandipsky.expense_tracker.dto.DropdownDTO;
import com.sandipsky.expense_tracker.repository.AccountRepository;
import com.sandipsky.expense_tracker.repository.CategoryRepository;
import com.sandipsky.expense_tracker.repository.UserRepository;

public class DropdownService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<DropdownDTO> getCategoryDropdown(String status) {
        Boolean isActive = "active".equalsIgnoreCase(status) ? true : null;
        return categoryRepository.findFilteredDropdown(isActive);
    }

    public List<DropdownDTO> getUserDropdown(String status) {
        Boolean isActive = "active".equalsIgnoreCase(status) ? true : null;
        return userRepository.findFilteredDropdown(isActive);
    }

     public List<DropdownDTO> getAccountDropdown(String status) {
        Boolean isActive = "active".equalsIgnoreCase(status) ? true : null;
        return accountRepository.findFilteredDropdown(isActive);
    }
}
