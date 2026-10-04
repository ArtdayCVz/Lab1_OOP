package main.models;

import java.util.Locale;
import java.util.Objects;

public abstract class Trip {

    private final Location departure;
    private final Location destination;
    private final double price;
    private final int durationInDays;
    private final TransportType transportType;
    private final MealPlan mealPlan;

    protected Trip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan) {
        if (departure == null || destination == null) {
            throw new IllegalArgumentException("Locations must not be null");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        if (durationInDays <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.durationInDays = durationInDays;
        this.transportType = Objects.requireNonNull(transportType,
                "Transport type must not be null");
        this.mealPlan = Objects.requireNonNull(mealPlan,
                "Meal plan must not be null");
    }

    public Location getDeparture() {
        return departure;
    }

    public Location getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public double getPricePerDay() {
        return price / durationInDays;
    }

    public abstract TripType getType();

    protected abstract String getDetails();

    public String getTripInfo() {
        return String.format(Locale.US, "%s: %s -> %s, %d days, %.2f$, %s, %s. %s",
                getType().getDisplayName(), departure, destination,
                durationInDays, price, transportType.getDisplayName(),
                mealPlan.getDisplayName(), getDetails());
    }
}
