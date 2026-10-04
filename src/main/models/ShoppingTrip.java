package main.models;

import java.util.List;

public class ShoppingTrip extends Trip {

    private final List<String> shoppingCenters;
    private final boolean taxFree;

    public ShoppingTrip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan,
            List<String> shoppingCenters, boolean taxFree) {
        super(departure, destination, price, durationInDays, transportType, mealPlan);
        this.shoppingCenters = List.copyOf(shoppingCenters);
        this.taxFree = taxFree;
    }

    public List<String> getShoppingCenters() {
        return shoppingCenters;
    }

    public boolean isTaxFree() {
        return taxFree;
    }

    @Override
    public TripType getType() {
        return TripType.SHOPPING;
    }

    @Override
    protected String getDetails() {
        return "Shopping centers: " + shoppingCenters + ". Tax free: " + taxFree;
    }
}
