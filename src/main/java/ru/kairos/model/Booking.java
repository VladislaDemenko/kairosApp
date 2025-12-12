package ru.kairos.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private Long userId;
    private Long placeId;
    private Long slotId;
    private String status = "CONFIRMED"; // Будет еще CANCELLED и COMPLETED
    private LocalDateTime createdAt = LocalDateTime.now();
    private String qrCodeUrl;

    public Booking() {
    }

    public Booking(Long id, Long userId, Long placeId, Long slotId) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.slotId = slotId;
        this.status = "CONFIRMED";
        this.createdAt = LocalDateTime.now();
        this.qrCodeUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=KAIROS-" + id;
    }
}
