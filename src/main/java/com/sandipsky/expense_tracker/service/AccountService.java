package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.AccountDTO;
import com.sandipsky.expense_tracker.entity.Account;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

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

    public List<AccountDTO> getAccounts() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
        dto.setCode(account.getCode());
        dto.setRemarks(account.getRemarks());
        dto.setIsActive(account.getIsActive());
        return dto;
    }

    private void mapDtoToEntity(AccountDTO dto, Account account) {
        account.setName(dto.getName().trim());
        account.setCode(dto.getCode());
        account.setRemarks(dto.getRemarks());
        account.setIsActive(dto.getIsActive());
    }
}
