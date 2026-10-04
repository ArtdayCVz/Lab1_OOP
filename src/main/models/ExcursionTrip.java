package main.models;

import java.util.List;

public class ExcursionTrip extends Trip {

    private final List<String> attractions;
    private final boolean guideIncluded;

    public ExcursionTrip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan,
            List<String> attractions, boolean guideIncluded) {
        super(departure, destination, price, durationInDays, transportType, mealPlan);
        this.attractions = List.copyOf(attractions);
        this.guideIncluded = guideIncluded;
    }

    public List<String> getAttractions() {
        return attractions;
    }

    public boolean isGuideIncluded() {
        return guideIncluded;
    }

    @Override
    public TripType getType() {
        return TripType.EXCURSION;
    }

    @Override
    protected String getDetails() {
        return "Attractions: " + attractions + ". Guide: " + guideIncluded;
    }
}
