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
    private final AtomicLong adminIdGenerator = new AtomicLong(1);

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
        createRostovPlaces();
        createSpbPlaces();
    }

    private void createRostovPlaces() {

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

    private void addCafeSlots(Place place) {
        LocalDate today = LocalDate.now();
        for (int day = 0; day < 7; day++) {
            for (int hour = 8; hour < 22; hour += 2) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(hour, 0));
                slot.setEndTime(slot.getStartTime().plusHours(2));
                slot.setPrice(place.getAvgCheck() * 0.8);
                slot.setTableNumber("Столик №" + ((day * 7 + hour / 2) % 10 + 1));
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    private void addRestaurantSlots(Place place, Double price, String tableType) {
        LocalDate today = LocalDate.now();
        for (int day = 0; day < 7; day++) {
            for (int hour = 12; hour < 23; hour += 2) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setPlaceId(place.getId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(hour, 0));
                slot.setEndTime(slot.getStartTime().plusHours(2));
                slot.setPrice(price);
                slot.setTableNumber(tableType + " №" + (day * 6) + hour/2 + 1);
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    private void addBeautySalonSlots(Place place) {
        LocalDate today = LocalDate.now();
        String[] masters = {"Мария", "Анна", "Елена", "София", "Олига"};
        String[] services = {"Стрижка", "Окрашивание", "Маникюр", "Педикюр", "Макияж"};

        for (int day = 0; day < 7; day++) {
            for (int i = 0; i < 12; i ++) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setPlaceId(place.getId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(9, 0).plusHours(i));
                slot.setEndTime(slot.getStartTime().plusHours(1));
                slot.setPrice(2000.0 + (i * 200));
                slot.setMasterName(masters[day % masters.length]);
                slot.setEventName(services[i % services.length]);
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    private void addBarberShopSlots(Place place) {
        LocalDate today = LocalDate.now();
        String[] barbers = {"Александр", "Дмитрий", "Иван", "Сергей", "Максим"};
        String[] services = {"Стрижка", "Бритье", "Уход за бородой", "Комплекс"};

        for (int day = 0; day < 7; day++) {
            for (int i = 0; i < 10; i++) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setPlaceId(place.getId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(0, 8).plusHours(i));
                slot.setEndTime(slot.getStartTime().plusHours(1));
                slot.setPrice(1800.0 + (i * 100));
                slot.setMasterName(barbers[day % barbers.length]);
                slot.setEventName(services[i % services.length]);
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    private void addParkSlots(Place place) {
        LocalDate today = LocalDate.now();
        String[] events = {"Вечерняя прогулка", "Утренняя пробежка", "Пикник", "Фотосессия"};

        for (int day = 0; day < 3; day++) {
            BookingSlot slot = new BookingSlot();
            slot.setId(nextSlotId());
            slot.setPlaceId(place.getId());
            slot.setDate(today.plusDays(day));
            slot.setStartTime(LocalTime.of(10, 0));
            slot.setEndTime(LocalTime.of(18, 0));
            slot.setPrice(0.0);
            slot.setEventName(events[day % events.length]);
            slot.setAvailable(true);
            place.getBookingSlots().add(slot);
        }
    }

    private void addMuseumSlots(Place place) {
        LocalDate today = LocalDate.now();
        String[] tours = {
                "Обзорная экскурсия",
                "Экскурсия для детей",
                "Тематическая выставка/ярмарка",
                "Аудиогид",
                "Экскурсии для иностранцев"
        };

        for (int day = 0; day < 7; day++) {
            for (int i = 0; i < 4; i++) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setPlaceId(place.getId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(10, 0).plusHours(i * 3));
                slot.setEndTime(slot.getStartTime().plusHours(2));
                slot.setPrice(500.00 + (i * 300));
                slot.setEventName(tours[day % tours.length]);
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    private void addActivitySlots(Place place, String activity, Double price) {
        LocalDate today = LocalDate.now();

        for (int day = 0; day < 7; day++) {
            for (int i = 0; i < 6; i++) {
                BookingSlot slot = new BookingSlot();
                slot.setId(nextSlotId());
                slot.setPlaceId(place.getId());
                slot.setDate(today.plusDays(day));
                slot.setStartTime(LocalTime.of(8,0).plusHours(i * 2));
                slot.setEndTime(slot.getStartTime().plusHours(2));
                slot.setPrice(price);
                slot.setEventName(activity + " - сеанс " + (i + 1));
                slot.setAvailable(true);
                place.getBookingSlots().add(slot);
            }
        }
    }

    // Создание тестовых пользователей
    private void initTestUsers() {
        User testUser = new User();
        testUser.setId(nextUserId());
        testUser.setEmail("test-email@mail.ru");
        testUser.setPassword("123456");
        testUser.setCity("Rostov");
        testUser.setTheme("LIGHT");
        testUser.setEnabled(true);
        testUser.getEnabledRandomCategories().addAll(Arrays.asList(

        ));
        testUser.getFavoritePlaceIds().addAll(Arrays.asList(1L, 3L, 5L));
        users.put(testUser.getId(), testUser);

        User admin = new User();
        admin.setId(nextAdminId());
        admin.setEmail("admin-email@mail.ru");
        admin.setPassword("admin123");
        admin.setCity("Rostov");
        admin.setTheme("DARK");
        admin.setEnabled(true);
        users.put(admin.getId(), admin);
    }

    // Здесь будет тестовые бронирования

    private void createTestBooking(Long userId, Long placeId, Long slotId, String status) {
        Booking booking = new Booking();
        booking.setId(nextBookingId());
        booking.setUserId(userId);
        booking.setPlaceId(placeId);
        booking.setSlotId(slotId);
        booking.setStatus(status);
        booking.setQrCodeUrl("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=KAIROS-" + booking.getId());


    }

    private Long nextBookingId() {
        return bookingIdGenerator.getAndIncrement();
    }

    private Long nextAdminId() {
        return adminIdGenerator.getAndIncrement();
    }

    private Long nextUserId() {
        return userIdGenerator.getAndIncrement();
    }

    private Long nextCategoryId() {
        return categoryIdGenerator.getAndIncrement();
    }

    private Long nextPlaceId() {
        return placeIdGenerator.getAndIncrement();
    }

    private Long nextSlotId() {
        return slotIdGenerator.getAndIncrement();
    }

    public User findUserById(Long id) {
        return users.get(id);
    }

    public User findUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    private User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(nextUserId());
        }
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
