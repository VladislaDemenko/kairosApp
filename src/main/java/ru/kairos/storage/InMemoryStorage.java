package ru.kairos.storage;

import ru.kairos.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryStorage {
    // Тут будут хранилища данных
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final Map<Long, Place> places = new ConcurrentHashMap<>();
    private final Map<Long, Booking> bookings = new ConcurrentHashMap<>();

    private final AtomicLong userIdGenerator = new AtomicLong(1);
    private final AtomicLong categoryIdGenerator = new AtomicLong(1);
    private final AtomicLong placeIdGenerator = new AtomicLong(1);
    private final AtomicLong slotIdGenerator = new AtomicLong(1);
    private final AtomicLong bookingIdGenerator = new AtomicLong(1);


}
