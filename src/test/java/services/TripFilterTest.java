package services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import models.Location;
import models.MealPlan;
import models.RecreationTrip;
import models.TransportType;
import models.Trip;
import models.TripType;

import org.junit.jupiter.api.Test;

class TripFilterTest {

    private final Trip trip = new RecreationTrip(new Location("Kyiv", "Ukraine"),
            new Location("Antalya", "Turkey"), 800, 7, TransportType.PLANE,
            MealPlan.ALL_INCLUSIVE, true);

    @Test
    void emptyFilterMatchesEverything() {
        assertTrue(TripFilter.builder().build().matches(trip));
    }

    @Test
    void filtersByPriceRange() {
        assertTrue(TripFilter.builder().minPrice(800).maxPrice(800).build().matches(trip));
        assertFalse(TripFilter.builder().maxPrice(799).build().matches(trip));
        assertFalse(TripFilter.builder().minPrice(801).build().matches(trip));
    }

    @Test
    void filtersByDuration() {
        assertTrue(TripFilter.builder().minDays(7).maxDays(7).build().matches(trip));
        assertFalse(TripFilter.builder().minDays(8).build().matches(trip));
        assertFalse(TripFilter.builder().maxDays(6).build().matches(trip));
    }

    @Test
    void filtersByTypeTransportMealAndCountry() {
        assertTrue(TripFilter.builder().type(TripType.RECREATION)
                .type(TripType.CRUISE).build().matches(trip));
        assertFalse(TripFilter.builder().type(TripType.CRUISE).build().matches(trip));
        assertFalse(TripFilter.builder().transport(TransportType.BUS).build().matches(trip));
        assertTrue(TripFilter.builder().transport(TransportType.PLANE).build().matches(trip));
        assertFalse(TripFilter.builder().mealPlan(MealPlan.NONE).build().matches(trip));
        assertTrue(TripFilter.builder().mealPlan(MealPlan.ALL_INCLUSIVE).build().matches(trip));
        assertTrue(TripFilter.builder().country("turkey").build().matches(trip));
        assertFalse(TripFilter.builder().country("Spain").build().matches(trip));
    }

    @Test
    void rejectsInvalidRanges() {
        assertThrows(IllegalArgumentException.class,
                () -> TripFilter.builder().minPrice(10).maxPrice(5).build());
        assertThrows(IllegalArgumentException.class,
                () -> TripFilter.builder().minDays(5).maxDays(2).build());
    }
}
