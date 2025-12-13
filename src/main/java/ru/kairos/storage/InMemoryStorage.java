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

    private final Map<String, Long> tokenUserId = new ConcurrentHashMap<>();
    private final Map<Long, String> userIdToToken = new ConcurrentHashMap<>();

    // тестовые данные
    @PostConstruct
    public void init() {

    }

    private void initCategories() {
        List<Category> categoryList = Arrays.asList(
                new Category(nextCategoryId(), "Кафе"),
                new Category(nextCategoryId(), "Рестораны"),
                new Category(nextCategoryId(), "Салоны красоты для женщин"),
                new Category(nextCategoryId(), "Барбершопы"),
                new Category(nextCategoryId(), "Музеи"),
                new Category(nextCategoryId(), "Парки"),
                new Category(nextCategoryId(), "Здоровый отдых"),
                new Category(nextCategoryId(), "Активный отдых"),
                new Category(nextCategoryId(), "Достопримечательности")
        );

        categoryList.forEach(cat -> categories.put(cat.getId(), cat));
    }

    // Заведения
    private void initPlaces() {
        createMoscowPlaces();
        createSpbPlaces();
    }

    private void createMoscowPlaces() {

    }

    private void createSpbPlaces() {

    }

    public void createAndSavePlace(String name, String desc, Long categoryId, String city,
                                   String address, Double avgCheck, Boolean hasParking, Double rating,
                                   Integer reviewCount, List<String> photos,
                                   java.util.function.Consumer<Place> slotInitialize) {
        Place place = new Place();
        place.setId(nextPlaceId());
        place.setName(name);
        place.setDescription(desc);
        place.setCategoryId(categoryId);
        place.setCity(city);
        place.setAddress(address);
        place.setAvgCheck(avgCheck);
        place.setHasParking(hasParking);
        place.setRating(rating);
        place.setReviewCount(reviewCount);
        place.getPhotoUrls().addAll(photos);

        slotInitialize.accept(place);
        places.put(place.getId(), place);
    }

    private Long nextCategoryId() {
        return categoryIdGenerator.getAndIncrement();
    }

    private Long nextPlaceId() {
        return placeIdGenerator.getAndIncrement();
    }
}
