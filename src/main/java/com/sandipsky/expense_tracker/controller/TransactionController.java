package com.sandipsky.expense_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.expense_tracker.dto.ApiResponse;
import com.sandipsky.expense_tracker.dto.TransactionDTO;
import com.sandipsky.expense_tracker.dto.pagable.PageRequestDTO;
import com.sandipsky.expense_tracker.entity.Transaction;
import com.sandipsky.expense_tracker.service.TransactionService;
import com.sandipsky.expense_tracker.util.ResponseUtil;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping()
    public List<TransactionDTO> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping()
    public Page<TransactionDTO> getTransactions(@RequestBody PageRequestDTO requestDTO) {
        return service.getTransactions(requestDTO);
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransaction(@PathVariable int id) {
        return service.getTransactionById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        Transaction res = service.saveTransaction(transactionDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Transaction Added successfully"));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(@PathVariable int id, @RequestBody TransactionDTO transactionDTO) {
        Transaction res = service.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Transaction Updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Transaction>> deleteTransaction(@PathVariable int id) {
        service.deleteTransaction(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Transaction Deleted successfully"));
    }
}
