package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.TransactionDTO;
import com.sandipsky.expense_tracker.entity.Account;
import com.sandipsky.expense_tracker.entity.Category;
import com.sandipsky.expense_tracker.entity.Transaction;
import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.AccountRepository;
import com.sandipsky.expense_tracker.repository.CategoryRepository;
import com.sandipsky.expense_tracker.repository.TransactionRepository;
import com.sandipsky.expense_tracker.repository.UserRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        mapDtoToEntity(transactionDTO, transaction);
        return repository.save(transaction);
    }

    public List<Transaction> getTransactions() {
        return repository.findAll();
    }

    public TransactionDTO getTransactionById(int id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return mapToDTO(transaction);
    }

    public Transaction updateTransaction(int id, TransactionDTO transactionDTO) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        mapDtoToEntity(transactionDTO, transaction);
        return repository.save(transaction);
    }

    public void deleteTransaction(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        repository.deleteById(id);
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSystemEntryNo(transaction.getSystemEntryNo());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setRemarks(transaction.getRemarks());
        dto.setUserId(transaction.getUser() != null ? transaction.getUser().getId() : null);
        dto.setUserName(transaction.getUser() != null ? transaction.getUser().getUsername() : null);
        dto.setAccountId(transaction.getAccount() != null ? transaction.getAccount().getId() : null);
        dto.setAccountName(transaction.getAccount() != null ? transaction.getAccount().getName() : null);
        dto.setCategoryId(transaction.getCategory() != null ? transaction.getCategory().getId() : null);
        dto.setCategoryName(transaction.getCategory() != null ? transaction.getCategory().getName() : null);
        return dto;
    }

    private void mapDtoToEntity(TransactionDTO dto, Transaction transaction) {
        transaction.setDate(dto.getDate());
        transaction.setSystemEntryNo(dto.getSystemEntryNo());
        transaction.setAmount(dto.getAmount());
        transaction.setRemarks(dto.getRemarks());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            transaction.setCategory(category);
        }
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            transaction.setUser(user);
        }
        if (dto.getAccountId() != null) {
            Account account = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            transaction.setAccount(account);
        }
    }
}
