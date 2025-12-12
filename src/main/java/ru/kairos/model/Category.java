package ru.kairos.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String iconUrl;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
