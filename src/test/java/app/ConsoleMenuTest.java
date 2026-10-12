package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import app.ConsoleMenu;
import models.CruiseTrip;
import models.Location;
import models.MealPlan;
import models.RecreationTrip;
import models.ShoppingTrip;
import models.TransportType;
import services.TripManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleMenuTest {

    private TripManager manager;

    @BeforeEach
    void setUp() {
        Location from = new Location("Kyiv", "Ukraine");
        manager = new TripManager();
        manager.addTrip(new CruiseTrip(from, new Location("Barcelona", "Spain"), 1500, 10,
                TransportType.SHIP, MealPlan.ALL_INCLUSIVE, "Balcony", true, List.of("Rome")));
        manager.addTrip(new RecreationTrip(from, new Location("Antalya", "Turkey"), 800, 7,
                TransportType.PLANE, MealPlan.ALL_INCLUSIVE, true));
        manager.addTrip(new ShoppingTrip(from, new Location("Warsaw", "Poland"), 250, 3,
                TransportType.BUS, MealPlan.NONE, List.of("Mall"), true));
    }

    private String run(String... lines) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Scanner scanner = new Scanner(String.join("\n", lines) + "\n");
        new ConsoleMenu(manager, scanner, new PrintStream(buffer)).run();
        return buffer.toString();
    }

    @Test
    void showsAllTripsAndExits() {
        String text = run("1", "0");
        assertTrue(text.contains("Barcelona") && text.contains("Antalya")
                && text.contains("Warsaw"));
        assertTrue(text.contains("Bye!"));
    }

    @Test
    void exitsOnEndOfInput() {
        assertTrue(run().contains("Bye!"));
    }

    @Test
    void reportsUnknownOption() {
        assertTrue(run("42", "0").contains("Unknown option: 42"));
    }

    @Test
    void appliesCustomPriceFilter() {
        // min, max, minDays, maxDays, types, transport, meals, country
        String text = run("2", "", "300", "", "", "", "", "", "", "5", "0");
        assertTrue(text.contains("Filter applied."));
        String selection = text.substring(text.indexOf("Filter applied."));
        assertTrue(selection.contains("Warsaw"));
        assertEquals(false, selection.contains("Antalya"));
    }

    @Test
    void appliesTypeFilterByNumber() {
        String text = run("2", "", "", "", "", "1", "", "", "", "5", "0");
        String selection = text.substring(text.indexOf("Filter applied."));
        assertTrue(selection.contains("Antalya"));
        assertEquals(false, selection.contains("Warsaw"));
    }

    @Test
    void invalidNumberKeepsFilterUnchanged() {
        String text = run("2", "abc", "5", "0");
        assertTrue(text.contains("Filter unchanged."));
        assertTrue(text.contains("Barcelona") && text.contains("Warsaw"));
    }

    @Test
    void outOfRangeChoiceIsRejected() {
        String text = run("2", "", "", "", "", "9", "0");
        assertTrue(text.contains("No such option: 9"));
    }

    @Test
    void invalidRangeIsRejected() {
        String text = run("2", "500", "100", "", "", "", "", "", "", "0");
        assertTrue(text.contains("Filter unchanged."));
    }

    @Test
    void resetsFilter() {
        String text = run("2", "", "300", "", "", "", "", "", "", "3", "5", "0");
        String afterReset = text.substring(text.indexOf("Filter reset."));
        assertTrue(afterReset.contains("Barcelona"));
    }

    @Test
    void emptySelectionIsReported() {
        String text = run("2", "", "10", "", "", "", "", "", "", "5", "0");
        assertTrue(text.contains("No trips found."));
    }

    @Test
    void appliesCustomSorting() {
        String text = run("4", "2", "5", "0");
        assertTrue(text.contains("Sorting set to PRICE_DESC"));
        String selection = text.substring(text.indexOf("Sorting set to"));
        assertTrue(selection.indexOf("Barcelona") < selection.indexOf("Warsaw"));
    }

    @Test
    void invalidSortingKeepsPreviousOne() {
        assertTrue(run("4", "99", "0").contains("Sorting unchanged."));
        assertTrue(run("4", "x", "0").contains("Sorting unchanged."));
    }
}
