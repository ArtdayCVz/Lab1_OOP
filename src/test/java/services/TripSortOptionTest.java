package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import models.CruiseTrip;
import models.Location;
import models.MealPlan;
import models.RecreationTrip;
import models.ShoppingTrip;
import models.TransportType;
import models.Trip;

import org.junit.jupiter.api.Test;

class TripSortOptionTest {

    private final Location from = new Location("Kyiv", "Ukraine");
    private final Trip shopping = new ShoppingTrip(from, new Location("Warsaw", "Poland"),
            250, 3, TransportType.BUS, MealPlan.NONE, List.of("Mall"), true);
    private final Trip beachCheap = new RecreationTrip(from, new Location("Antalya", "Turkey"),
            800, 7, TransportType.PLANE, MealPlan.ALL_INCLUSIVE, true);
    private final Trip beachPricey = new RecreationTrip(from, new Location("Hurghada", "Egypt"),
            950, 10, TransportType.PLANE, MealPlan.FULL_BOARD, true);
    private final Trip cruise = new CruiseTrip(from, new Location("Barcelona", "Spain"),
            1500, 10, TransportType.SHIP, MealPlan.ALL_INCLUSIVE, "Balcony", true,
            List.of("Rome"));

    private List<Trip> sorted(TripSortOption option) {
        List<Trip> list = new ArrayList<>(List.of(cruise, beachPricey, shopping, beachCheap));
        list.sort(option.getComparator());
        return list;
    }

    @Test
    void sortsByDestinationAlphabetically() {
        assertEquals(List.of(beachCheap, cruise, beachPricey, shopping),
                sorted(TripSortOption.DESTINATION_ASC));
    }

    @Test
    void sortsByTypeThenPrice() {
        // enum order: RECREATION, EXCURSION, TREATMENT, SHOPPING, CRUISE
        assertEquals(List.of(beachCheap, beachPricey, shopping, cruise),
                sorted(TripSortOption.TYPE_THEN_PRICE));
    }

    @Test
    void priceComparatorReadsOnlyPrice() {
        Trip cheap = mock(Trip.class);
        Trip pricey = mock(Trip.class);
        when(cheap.getPrice()).thenReturn(100.0);
        when(pricey.getPrice()).thenReturn(900.0);

        assertTrue(TripSortOption.PRICE_ASC.getComparator().compare(cheap, pricey) < 0);
        verify(cheap).getPrice();
        verify(cheap, never()).getDurationInDays();
        verify(cheap, never()).getPricePerDay();
    }

    @Test
    void pricePerDayComparatorUsesPricePerDay() {
        Trip perDayLow = mock(Trip.class);
        Trip perDayHigh = mock(Trip.class);
        when(perDayLow.getPricePerDay()).thenReturn(50.0);
        when(perDayHigh.getPricePerDay()).thenReturn(200.0);

        assertTrue(TripSortOption.PRICE_PER_DAY.getComparator()
                .compare(perDayHigh, perDayLow) > 0);
        verify(perDayLow).getPricePerDay();
        verify(perDayLow, never()).getPrice();
    }
}