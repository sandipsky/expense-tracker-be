package com.sandipsky.expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.expense_tracker.dto.ApiResponse;
import com.sandipsky.expense_tracker.dto.BudgetDTO;
import com.sandipsky.expense_tracker.entity.Budget;
import com.sandipsky.expense_tracker.service.BudgetService;
import com.sandipsky.expense_tracker.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService service;

    @GetMapping()
    public List<BudgetDTO> getBudgets() {
        return service.getBudgets();
    }

    @GetMapping("/{id}")
    public BudgetDTO getBudget(@PathVariable int id) {
        return service.getBudgetById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Budget>> createBudget(@RequestBody BudgetDTO budgetDTO) {
        Budget res = service.saveBudget(budgetDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Budget Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> updateBudget(@PathVariable int id, @RequestBody BudgetDTO budgetDTO) {
        Budget res = service.updateBudget(id, budgetDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Budget Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> deleteBudget(@PathVariable int id) {
        service.deleteBudget(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Budget Deleted successfully"));
    }
}
