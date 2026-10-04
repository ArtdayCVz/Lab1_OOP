package main.app;

import java.util.List;
import java.util.Scanner;

import main.models.CruiseTrip;
import main.models.ExcursionTrip;
import main.models.Location;
import main.models.MealPlan;
import main.models.RecreationTrip;
import main.models.ShoppingTrip;
import main.models.TransportType;
import main.models.TreatmentTrip;
import main.services.TripManager;

public class Main {

    public static void main(String[] args) {
        TripManager manager = createSampleManager();
        new ConsoleMenu(manager, new Scanner(System.in), System.out).run();
    }

    private static TripManager createSampleManager() {
        Location kyiv = new Location("Kyiv", "Ukraine");
        TripManager manager = new TripManager();
        manager.addTrip(new CruiseTrip(new Location("Odesa", "Ukraine"),
                new Location("Barcelona", "Spain"), 1500.50, 10,
                TransportType.SHIP, MealPlan.ALL_INCLUSIVE, "Balcony", true,
                List.of("Rome", "Carthage", "Nice")));
        manager.addTrip(new RecreationTrip(kyiv, new Location("Antalya", "Turkey"),
                800, 7, TransportType.PLANE, MealPlan.ALL_INCLUSIVE, true));
        manager.addTrip(new RecreationTrip(kyiv, new Location("Hurghada", "Egypt"),
                950, 10, TransportType.PLANE, MealPlan.FULL_BOARD, true));
        manager.addTrip(new ExcursionTrip(kyiv, new Location("Prague", "Czechia"),
                450, 4, TransportType.BUS, MealPlan.BREAKFAST,
                List.of("Charles Bridge", "Prague Castle"), true));
        manager.addTrip(new TreatmentTrip(kyiv, new Location("Truskavets", "Ukraine"),
                600, 14, TransportType.TRAIN, MealPlan.FULL_BOARD,
                "Gastroenterology", true));
        manager.addTrip(new ShoppingTrip(kyiv, new Location("Warsaw", "Poland"),
                250, 3, TransportType.BUS, MealPlan.NONE,
                List.of("Zlote Tarasy", "Arkadia"), true));
        return manager;
    }
}
