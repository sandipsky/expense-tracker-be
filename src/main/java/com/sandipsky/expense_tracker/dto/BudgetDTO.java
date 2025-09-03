package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandipsky.expense_tracker.entity.Budget.Period;

import lombok.*;

@Getter
@Setter
public class BudgetDTO {
    private Integer id;

    private Double amount;

    private String remarks;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private Period period;

    @JsonProperty("notification_threshold")
    private Integer notificationThreshold;
}
