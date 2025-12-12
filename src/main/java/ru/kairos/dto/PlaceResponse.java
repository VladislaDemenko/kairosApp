package ru.kairos.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlaceResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String city;
    private String address;
    private Double avgCheck;
    private Boolean hasParking;
    private Double rating;
    private Integer reviewCount;
    private List<String> photoUrls;
}
