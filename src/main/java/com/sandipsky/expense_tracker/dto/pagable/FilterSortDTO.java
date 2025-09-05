package com.sandipsky.expense_tracker.dto.pagable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterSortDTO {
    private String field;
    private String value;
}

