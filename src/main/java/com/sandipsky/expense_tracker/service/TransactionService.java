package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.expense_tracker.dto.TransactionDTO;
import com.sandipsky.expense_tracker.entity.Transaction;
import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.TransactionRepository;
import com.sandipsky.expense_tracker.repository.UserRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Transaction saveTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getName() == null || transactionDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Transaction name cannot be null or blank");
        }
        if (repository.existsByName(transactionDTO.getName().trim())) {
            throw new DuplicateResourceException("Transaction with the same name already exists");
        }
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
        if (transactionDTO.getName() == null || transactionDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("Transaction name cannot be null or blank");
        }

        if (repository.existsByNameAndIdNot(transactionDTO.getName().trim(), id)) {
            throw new DuplicateResourceException("Transaction with the same name already exists");
        }

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
        dto.setName(transaction.getName());
        dto.setColorCode(transaction.getColorCode());
        dto.setDescription(transaction.getDescription());
        dto.setIsActive(transaction.getIsActive());
        dto.setType(transaction.getType());
        dto.setUserId(transaction.getUser() != null ? transaction.getUser().getId() : null);
        dto.setUserName(transaction.getUser() != null ? transaction.getUser().getUsername() : null);
        return dto;
    }

    private void mapDtoToEntity(TransactionDTO dto, Transaction transaction) {
        transaction.setName(dto.getName().trim());
        transaction.setIsActive(dto.getIsActive());
        transaction.setColorCode(dto.getColorCode());
        transaction.setDescription(dto.getDescription());
        transaction.setIsActive(dto.getIsActive());
        transaction.setType(dto.getType());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
            transaction.setUser(user);
        }
    }
}
