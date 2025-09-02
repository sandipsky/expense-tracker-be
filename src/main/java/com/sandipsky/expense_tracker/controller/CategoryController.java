package com.sandipsky.expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.expense_tracker.dto.ApiResponse;
import com.sandipsky.expense_tracker.dto.CategoryDTO;
import com.sandipsky.expense_tracker.entity.Category;
import com.sandipsky.expense_tracker.service.CategoryService;
import com.sandipsky.expense_tracker.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping()
    public List<Category> getCategorys() {
        return service.getCategorys();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable int id) {
        return service.getCategoryById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category res = service.saveCategory(categoryDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Category Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        Category res = service.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Category Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(@PathVariable int id) {
        service.deleteCategory(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Category Deleted successfully"));
    }
}
