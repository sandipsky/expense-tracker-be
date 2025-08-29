package com.sandipsky.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandipsky.expense_tracker.entity.Category.CategoryType;

import lombok.*;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("user_name")
    private String userName;

    private String name;

    @JsonProperty("color_code")
    private String colorCode;

    private CategoryType type;

    private String description;

    @JsonProperty("is_active")
    private Boolean isActive;
}
