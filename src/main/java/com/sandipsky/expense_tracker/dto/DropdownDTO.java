package com.sandipsky.expense_tracker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DropdownDTO {
    private int id;
    private String name;

    public DropdownDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
