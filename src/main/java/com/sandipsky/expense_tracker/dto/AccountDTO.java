package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
public class AccountDTO {
    private Integer id;

    private String name;

    private String code;

    private String type;

    private String remarks;

    @JsonProperty("is_active")
    private Boolean isActive;
}
