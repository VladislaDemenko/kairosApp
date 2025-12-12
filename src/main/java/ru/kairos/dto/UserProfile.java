package ru.kairos.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserProfile {
    private String email;
    private String phone;
    private String city;
    private String theme;
    private List<Long> favoritePlaces;
}
