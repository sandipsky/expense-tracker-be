package com.sandipsky.expense_tracker.dto.pagable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDTO {
    private int pageIndex;
    private int pageSize;
    private List<FilterSortDTO> sort;
    private List<FilterSortDTO> filter;
}
