package com.sandipsky.expense_tracker.util;

import org.springframework.data.jpa.domain.Specification;

import com.sandipsky.expense_tracker.dto.pagable.FilterSortDTO;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class FilterSortSpecBuilder<T> {

    public Specification<T> buildSpecification(List<FilterSortDTO> filters, List<FilterSortDTO> sortDTOs) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtering
            if (filters != null) {
                for (FilterSortDTO filter : filters) {
                    if (filter.getField() != null && filter.getValue() != null) {
                        try {
                            Path<?> path = getPath(root, filter.getField());
                            Expression<String> expression = cb.lower(path.as(String.class));
                            predicates.add(cb.like(expression, "%" + filter.getValue().toLowerCase() + "%"));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Skipping unknown filter field: " + filter.getField());
                        }
                    }
                }
            }

            // Sorting
            if (sortDTOs != null && !sortDTOs.isEmpty()) {
                List<Order> orders = new ArrayList<>();
                for (FilterSortDTO dto : sortDTOs) {
                    if (dto.getField() != null && dto.getValue() != null) {
                        Path<?> path = getPath(root, dto.getField());
                        if ("desc".equalsIgnoreCase(dto.getValue())) {
                            orders.add(cb.desc(path));
                        } else {
                            orders.add(cb.asc(path));
                        }
                    }
                }
                query.orderBy(orders);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Path<?> getPath(Root<?> root, String fieldName) {
        if (fieldName.contains(".")) {
            String[] parts = fieldName.split("\\.");
            Path<?> path = root.get(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                path = path.get(parts[i]);
            }
            return path;
        } else {
            return root.get(fieldName);
        }
    }
}
