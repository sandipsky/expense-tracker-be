package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.AccountDTO;
import com.sandipsky.expense_tracker.entity.Account;
import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.AccountRepository;
import com.sandipsky.expense_tracker.repository.UserRepository;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Account saveAccount(AccountDTO accountDTO) {
        if (accountDTO.getName() == null || accountDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Account name cannot be null or blank");
        }
        if (repository.existsByName(accountDTO.getName().trim())) {
            throw new DuplicateResourceException("Account with the same name already exists");
        }
        Account account = new Account();
        mapDtoToEntity(accountDTO, account);
        return repository.save(account);
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public AccountDTO getAccountById(int id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToDTO(account);
    }

    public Account updateAccount(int id, AccountDTO accountDTO) {
        if (accountDTO.getName() == null || accountDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Account name cannot be null or blank");
        }

        if (repository.existsByNameAndIdNot(accountDTO.getName().trim(), id)) {
            throw new DuplicateResourceException("Account with the same name already exists");
        }

        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        mapDtoToEntity(accountDTO, account);
        return repository.save(account);
    }

    public void deleteAccount(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        repository.deleteById(id);
    }

    private AccountDTO mapToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setColorCode(account.getColorCode());
        dto.setDescription(account.getDescription());
        dto.setIsActive(account.getIsActive());
        dto.setType(account.getType());
        dto.setUserId(account.getUser() != null ? account.getUser().getId() : null);
        dto.setUserName(account.getUser() != null ? account.getUser().getUsername() : null);
        return dto;
    }

    private void mapDtoToEntity(AccountDTO dto, Account account) {
        account.setName(dto.getName().trim());
        account.setIsActive(dto.getIsActive());
        account.setColorCode(dto.getColorCode());
        account.setDescription(dto.getDescription());
        account.setIsActive(dto.getIsActive());
        account.setType(dto.getType());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            account.setUser(user);
        }
    }
}
