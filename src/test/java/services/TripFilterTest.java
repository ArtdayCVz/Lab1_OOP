package services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

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

    @Test
    void emptyFilterDoesNotInspectTrip() {
        Trip mockTrip = mock(Trip.class);

        assertTrue(TripFilter.builder().build().matches(mockTrip));
        verifyNoInteractions(mockTrip);
    }

    @Test
    void stopsCheckingAfterFirstFailedCriterion() {
        Trip mockTrip = mock(Trip.class);
        when(mockTrip.getPrice()).thenReturn(100.0);

        assertFalse(TripFilter.builder().minPrice(500).minDays(3).build().matches(mockTrip));
        verify(mockTrip).getPrice();
        verify(mockTrip, never()).getDurationInDays();
    }

    @Test
    void matchesMockedTripByAllCriteria() {
        Trip mockTrip = mock(Trip.class);
        when(mockTrip.getPrice()).thenReturn(300.0);
        when(mockTrip.getDurationInDays()).thenReturn(5);
        when(mockTrip.getType()).thenReturn(TripType.SHOPPING);
        when(mockTrip.getTransportType()).thenReturn(TransportType.BUS);
        when(mockTrip.getMealPlan()).thenReturn(MealPlan.NONE);
        when(mockTrip.getDestination()).thenReturn(new Location("Warsaw", "Poland"));

        TripFilter filter = TripFilter.builder()
                .minPrice(200).maxPrice(400)
                .minDays(3).maxDays(7)
                .type(TripType.SHOPPING)
                .transport(TransportType.BUS)
                .mealPlan(MealPlan.NONE)
                .country("poland")
                .build();

        assertTrue(filter.matches(mockTrip));
        verify(mockTrip).getDestination();
    }
}