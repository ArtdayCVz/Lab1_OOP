package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import models.Trip;
import models.TripType;
import services.TripFilter;
import services.TripManager;
import services.TripSortOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TripManagerTest {

    private TripManager manager;
    private Trip cheapShort;
    private Trip midLong;
    private Trip expensive;

    private Trip mockTrip(String info, double price, int days, TripType type) {
        Trip t = mock(Trip.class);
        when(t.getTripInfo()).thenReturn(info);
        when(t.getPrice()).thenReturn(price);
        when(t.getDurationInDays()).thenReturn(days);
        when(t.getPricePerDay()).thenReturn(price / days);
        when(t.getType()).thenReturn(type);
        return t;
    }

    @BeforeEach
    void setUp() {
        manager = new TripManager();
        cheapShort = mockTrip("A", 100, 2, TripType.SHOPPING);
        midLong = mockTrip("B", 600, 10, TripType.TREATMENT);
        expensive = mockTrip("C", 2000, 5, TripType.CRUISE);
        manager.addTrip(expensive);
        manager.addTrip(cheapShort);
        manager.addTrip(midLong);
    }

    @Test
    void rejectsNullTrip() {
        assertThrows(IllegalArgumentException.class, () -> manager.addTrip(null));
    }

    @Test
    void tripListIsReadOnly() {
        assertEquals(3, manager.getTrips().size());
        assertThrows(UnsupportedOperationException.class,
                () -> manager.getTrips().add(cheapShort));
    }

    @Test
    void sortsByEveryOption() {
        List<Trip> all = manager.getTrips();
        assertEquals(List.of(cheapShort, midLong, expensive),
                manager.sort(all, TripSortOption.PRICE_ASC));
        assertEquals(List.of(expensive, midLong, cheapShort),
                manager.sort(all, TripSortOption.PRICE_DESC));
        assertEquals(List.of(cheapShort, expensive, midLong),
                manager.sort(all, TripSortOption.DURATION_ASC));
        assertEquals(List.of(midLong, expensive, cheapShort),
                manager.sort(all, TripSortOption.DURATION_DESC));
        assertEquals(List.of(cheapShort, midLong, expensive),
                manager.sort(all, TripSortOption.PRICE_PER_DAY));
    }

    @Test
    void sortDoesNotChangeOriginalOrder() {
        manager.sort(manager.getTrips(), TripSortOption.PRICE_ASC);
        assertEquals(expensive, manager.getTrips().get(0));
    }

    @Test
    void filtersTrips() {
        TripFilter f = TripFilter.builder().maxPrice(600).build();
        assertEquals(List.of(cheapShort, midLong), manager.filter(f));
    }

    @Test
    void findTripsFiltersThenSorts() {
        TripFilter f = TripFilter.builder().minPrice(400).build();
        assertEquals(List.of(expensive, midLong),
                manager.findTrips(f, TripSortOption.PRICE_DESC));
    }

    @Test
    void printsTripInfo() {
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            manager.printAllTrips();
        } finally {
            System.setOut(original);
        }
        String text = out.toString();
        assertEquals(true, text.contains("List of trips:"));
        assertEquals(true, text.contains("A") && text.contains("B") && text.contains("C"));
    }
}
