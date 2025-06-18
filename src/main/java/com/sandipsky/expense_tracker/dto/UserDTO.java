package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class UserDTO {
    private Integer id;

    private Double amount;

    private String remarks;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("user_id")
    private Integer user;

    @JsonProperty("username")
    private String userName;

    private String period;

    @JsonProperty("notification_threshold")
    private Integer notificationThreshold;
}
