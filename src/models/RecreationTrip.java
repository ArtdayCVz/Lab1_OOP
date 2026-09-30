package models;

public class RecreationTrip extends Trip {

    private final boolean hasPool;

    public RecreationTrip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan,
            boolean hasPool) {
        super(departure, destination, price, durationInDays, transportType, mealPlan);
        this.hasPool = hasPool;
    }

    public boolean hasPool() {
        return hasPool;
    }

    @Override
    public String getTripInfo() {
        return "Recreation: " + getDeparture() + " -> " + getDestination()
                + ", " + getDurationInDays() + " days, " + getPrice() + "$"
                + ", " + getTransportType().getDisplayName()
                + ", " + getMealPlan().getDisplayName()
                + ". Pool: " + hasPool;
    }
}