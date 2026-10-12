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
    public TripType getType() {
        return TripType.RECREATION;
    }

    @Override
    protected String getDetails() {
        return "Pool: " + hasPool;
    }
}
