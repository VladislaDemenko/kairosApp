package ru.kairos.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingSlot {
    private Long id;
    private Long placeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available = true;
    private Double price;
    private String tableNumber;
    private String masterName;
    private String eventName;

    public BookingSlot() {
    }

    public BookingSlot(Long id, Long placeId, LocalDate date, LocalTime startTime,
                       LocalTime endTime, Double price, String tableNumber) {
        this.id = id;
        this.placeId = placeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.tableNumber = tableNumber;
        this.available = true;
    }
}
