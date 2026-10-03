package app;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.MealPlan;
import models.TransportType;
import models.Trip;
import models.TripType;
import services.TripFilter;
import services.TripManager;
import services.TripSortOption;

public class ConsoleMenu {

    private final TripManager manager;
    private final Scanner in;
    private final PrintStream out;
    private TripFilter filter = TripFilter.builder().build();
    private TripSortOption sortOption = TripSortOption.PRICE_ASC;

    public ConsoleMenu(TripManager manager, Scanner in, PrintStream out) {
        this.manager = manager;
        this.in = in;
        this.out = out;
    }

    public void run() {
        while (true) {
            printMenu();
            String choice = readLine();
            if (choice == null || "0".equals(choice.trim())) {
                out.println("Bye!");
                return;
            }
            handle(choice.trim());
        }
    }

    private void printMenu() {
        out.println();
        out.println("=== Trip selection (sorting: " + sortOption + ") ===");
        out.println("1. Show all trips");
        out.println("2. Set filter");
        out.println("3. Reset filter");
        out.println("4. Choose sorting");
        out.println("5. Show selection");
        out.println("0. Exit");
        out.print("> ");
        out.flush();
    }

    private void handle(String choice) {
        switch (choice) {
            case "1" -> showTrips(manager.getTrips());
            case "2" -> setFilter();
            case "3" -> resetFilter();
            case "4" -> chooseSorting();
            case "5" -> showTrips(manager.findTrips(filter, sortOption));
            default -> out.println("Unknown option: " + choice);
        }
    }

    private void showTrips(List<Trip> trips) {
        if (trips.isEmpty()) {
            out.println("No trips found.");
            return;
        }
        int number = 1;
        for (Trip trip : trips) {
            out.println(number++ + ". " + trip.getTripInfo());
        }
    }

    private void setFilter() {
        out.println("Press Enter to skip a criterion.");
        try {
            TripFilter.Builder builder = TripFilter.builder();
            Double minPrice = readDouble("Min price");
            if (minPrice != null) {
                builder.minPrice(minPrice);
            }
            Double maxPrice = readDouble("Max price");
            if (maxPrice != null) {
                builder.maxPrice(maxPrice);
            }
            Integer minDays = readInt("Min days");
            if (minDays != null) {
                builder.minDays(minDays);
            }
            Integer maxDays = readInt("Max days");
            if (maxDays != null) {
                builder.maxDays(maxDays);
            }
            for (TripType type : readChoices("Trip types", TripType.values())) {
                builder.type(type);
            }
            for (TransportType t : readChoices("Transport", TransportType.values())) {
                builder.transport(t);
            }
            for (MealPlan m : readChoices("Meal plans", MealPlan.values())) {
                builder.mealPlan(m);
            }
            String country = prompt("Destination country");
            if (!country.isBlank()) {
                builder.country(country.trim());
            }
            filter = builder.build();
            out.println("Filter applied.");
        } catch (IllegalArgumentException e) {
            out.println("Invalid input: " + e.getMessage() + ". Filter unchanged.");
        }
    }

    private void resetFilter() {
        filter = TripFilter.builder().build();
        out.println("Filter reset.");
    }

    private void chooseSorting() {
        TripSortOption[] options = TripSortOption.values();
        for (int i = 0; i < options.length; i++) {
            out.println((i + 1) + ". " + options[i]);
        }
        String answer = prompt("Sorting number").trim();
        try {
            int index = Integer.parseInt(answer);
            if (index < 1 || index > options.length) {
                throw new IllegalArgumentException("No such option: " + index);
            }
            sortOption = options[index - 1];
            out.println("Sorting set to " + sortOption);
        } catch (IllegalArgumentException e) {
            out.println("Invalid input: " + e.getMessage() + ". Sorting unchanged.");
        }
    }

    private Double readDouble(String label) {
        String text = prompt(label).trim();
        return text.isEmpty() ? null : Double.valueOf(text);
    }

    private Integer readInt(String label) {
        String text = prompt(label).trim();
        return text.isEmpty() ? null : Integer.valueOf(text);
    }

    private <E extends Enum<E>> List<E> readChoices(String label, E[] values) {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            options.append(i + 1).append('=').append(values[i]).append(' ');
        }
        String text = prompt(label + " [" + options.toString().trim()
                + "], comma separated").trim();
        if (text.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(n -> {
                    if (n < 1 || n > values.length) {
                        throw new IllegalArgumentException("No such option: " + n);
                    }
                    return values[n - 1];
                })
                .toList();
    }

    private String prompt(String label) {
        out.print(label + ": ");
        out.flush();
        String line = readLine();
        return line == null ? "" : line;
    }

    private String readLine() {
        return in.hasNextLine() ? in.nextLine() : null;
    }
}
