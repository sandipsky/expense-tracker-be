package com.sandipsky.expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.expense_tracker.dto.ApiResponse;
import com.sandipsky.expense_tracker.dto.AccountDTO;
import com.sandipsky.expense_tracker.entity.Account;
import com.sandipsky.expense_tracker.service.AccountService;
import com.sandipsky.expense_tracker.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping()
    public List<AccountDTO> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable int id) {
        return service.getAccountById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody AccountDTO accountDTO) {
        Account res = service.saveAccount(accountDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Account Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> updateAccount(@PathVariable int id, @RequestBody AccountDTO accountDTO) {
        Account res = service.updateAccount(id, accountDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Account Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> deleteAccount(@PathVariable int id) {
        service.deleteAccount(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Account Deleted successfully"));
    }
}
