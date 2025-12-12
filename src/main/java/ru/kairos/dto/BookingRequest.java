package ru.kairos.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long placeId;
    private Long slotId;
    private LocalDate date;
}
