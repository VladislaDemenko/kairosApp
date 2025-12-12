package ru.kairos.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String email;
    private String phone;
    private String password;
    private String city;
    private String theme = "LIGHT"; // Тут еще сможет быть DARK
    private boolean enabled = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Long> favoritePlaceIds = new ArrayList<>();
    private List<Long> enabledRandomCategories = new ArrayList<>();
}
