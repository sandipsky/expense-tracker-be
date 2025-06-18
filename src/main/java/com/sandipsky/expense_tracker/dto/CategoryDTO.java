package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;

    @JsonProperty("user_id")
    private Integer user;

    @JsonProperty("username")
    private String userName;

    private String name;

    @JsonProperty("color_code")
    private String colorCode;

    private String type;

    private String description;

    @JsonProperty("is_active")
    private Boolean isActive;
}
