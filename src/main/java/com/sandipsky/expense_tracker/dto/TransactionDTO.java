package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class TransactionDTO {
    private Integer id;

    private String date;

    @JsonProperty("system_entry_no")
    private String systemEntryNo;

    private Double amount;

    private String remarks;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("account_name")
    private String accountName;
}
