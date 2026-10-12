package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import models.CruiseTrip;
import models.Location;
import models.MealPlan;
import models.RecreationTrip;
import models.ShoppingTrip;
import models.TransportType;
import models.Trip;
import services.TripSortOption;

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
}
