package test.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import main.models.CruiseTrip;
import main.models.ExcursionTrip;
import main.models.Location;
import main.models.MealPlan;
import main.models.RecreationTrip;
import main.models.ShoppingTrip;
import main.models.TransportType;
import main.models.TreatmentTrip;
import main.models.TripType;

import org.junit.jupiter.api.Test;

class TripTest {

    private static final Location FROM = new Location("Kyiv", "Ukraine");
    private static final Location TO = new Location("Antalya", "Turkey");

    private RecreationTrip recreation(double price, int days) {
        return new RecreationTrip(FROM, TO, price, days, TransportType.PLANE,
                MealPlan.ALL_INCLUSIVE, true);
    }

    @Test
    void validatesArguments() {
        assertThrows(IllegalArgumentException.class, () -> recreation(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> recreation(100, 0));
        assertThrows(IllegalArgumentException.class, () -> new RecreationTrip(null,
                TO, 1, 1, TransportType.BUS, MealPlan.NONE, false));
        assertThrows(NullPointerException.class, () -> new RecreationTrip(FROM, TO,
                1, 1, null, MealPlan.NONE, false));
        assertThrows(NullPointerException.class, () -> new RecreationTrip(FROM, TO,
                1, 1, TransportType.BUS, null, false));
    }

    @Test
    void calculatesPricePerDay() {
        assertEquals(100.0, recreation(700, 7).getPricePerDay(), 0.0001);
    }

    @Test
    void recreationInfoAndGetters() {
        RecreationTrip t = recreation(800, 7);
        assertEquals(TripType.RECREATION, t.getType());
        assertTrue(t.hasPool());
        assertEquals(FROM, t.getDeparture());
        assertEquals(TO, t.getDestination());
        assertEquals(TransportType.PLANE, t.getTransportType());
        assertEquals(MealPlan.ALL_INCLUSIVE, t.getMealPlan());
        assertEquals("Recreation: Kyiv, Ukraine -> Antalya, Turkey, 7 days, 800.00$, "
                + "Plane, All inclusive. Pool: true", t.getTripInfo());
    }

    @Test
    void cruiseInfoAndGetters() {
        CruiseTrip t = new CruiseTrip(FROM, TO, 1500.5, 10, TransportType.SHIP,
                MealPlan.FULL_BOARD, "Balcony", false, List.of("Rome", "Nice"));
        assertEquals(TripType.CRUISE, t.getType());
        assertEquals("Balcony", t.getCabinType());
        assertEquals(List.of("Rome", "Nice"), t.getPortsOfCall());
        assertEquals(false, t.hasPool());
        assertTrue(t.getTripInfo().endsWith(
                "Cabin: Balcony. Pool: false. Route: [Rome, Nice]"));
    }

    @Test
    void excursionInfoAndGetters() {
        ExcursionTrip t = new ExcursionTrip(FROM, TO, 450, 4, TransportType.BUS,
                MealPlan.BREAKFAST, List.of("Castle"), true);
        assertEquals(TripType.EXCURSION, t.getType());
        assertEquals(List.of("Castle"), t.getAttractions());
        assertTrue(t.isGuideIncluded());
        assertTrue(t.getTripInfo().endsWith("Attractions: [Castle]. Guide: true"));
    }

    @Test
    void treatmentInfoAndValidation() {
        TreatmentTrip t = new TreatmentTrip(FROM, TO, 600, 14, TransportType.TRAIN,
                MealPlan.FULL_BOARD, " Spa ", true);
        assertEquals(TripType.TREATMENT, t.getType());
        assertEquals("Spa", t.getTreatmentProfile());
        assertTrue(t.hasMedicalSupervision());
        assertTrue(t.getTripInfo().endsWith("Profile: Spa. Medical supervision: true"));
        assertThrows(IllegalArgumentException.class, () -> new TreatmentTrip(FROM, TO,
                1, 1, TransportType.BUS, MealPlan.NONE, " ", false));
    }

    @Test
    void shoppingInfoAndGetters() {
        ShoppingTrip t = new ShoppingTrip(FROM, TO, 250, 3, TransportType.BUS,
                MealPlan.NONE, List.of("Mall"), true);
        assertEquals(TripType.SHOPPING, t.getType());
        assertEquals(List.of("Mall"), t.getShoppingCenters());
        assertTrue(t.isTaxFree());
        assertTrue(t.getTripInfo().endsWith("Shopping centers: [Mall]. Tax free: true"));
    }

    @Test
    void enumsExposeDisplayNames() {
        assertEquals("Cruise", TripType.CRUISE.getDisplayName());
        assertEquals("Bus", TransportType.BUS.getDisplayName());
        assertEquals("Half board", MealPlan.HALF_BOARD.getDisplayName());
    }
}
