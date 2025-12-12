package ru.kairos.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Place {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private String city;
    private String address;
    private Double avgCheck;
    private Boolean hasParking;
    private Double rating = 0.0;
    private Integer reviewCount = 0;
    private List<String> photoUrls = new ArrayList<>();
    private List<BookingSlot> bookingSlots = new ArrayList<>();
}
