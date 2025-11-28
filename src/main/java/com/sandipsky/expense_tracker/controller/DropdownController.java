package com.sandipsky.expense_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandipsky.expense_tracker.dto.DropdownDTO;
import com.sandipsky.expense_tracker.service.DropdownService;

@RestController
@RequestMapping("/dropdown")
public class DropdownController {

    @Autowired
    private DropdownService service;

    @GetMapping("/account/{status}")
    public List<DropdownDTO> getAccountDropdown(@PathVariable String status) {
        return service.getAccountDropdown(status);
    }

    @GetMapping("/category/{status}")
    public List<DropdownDTO> getCategorysDropdown(@PathVariable String status) {
        return service.getCategoryDropdown(status);
    }

    @GetMapping("/users/{status}")
    public List<DropdownDTO> getUsersDropdown(@PathVariable String status) {
        return service.getUserDropdown(status);
    }
}
