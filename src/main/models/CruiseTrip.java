package main.models;

import java.util.List;

public class CruiseTrip extends Trip {

    private final String cabinType;
    private final boolean hasPool;
    private final List<String> portsOfCall;

    public CruiseTrip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan,
            String cabinType, boolean hasPool, List<String> portsOfCall) {
        super(departure, destination, price, durationInDays, transportType, mealPlan);
        this.cabinType = cabinType;
        this.hasPool = hasPool;
        this.portsOfCall = List.copyOf(portsOfCall);
    }

    public String getCabinType() {
        return cabinType;
    }

    public boolean hasPool() {
        return hasPool;
    }

    public List<String> getPortsOfCall() {
        return portsOfCall;
    }

    @Override
    public TripType getType() {
        return TripType.CRUISE;
    }

    @Override
    protected String getDetails() {
        return "Cabin: " + cabinType + ". Pool: " + hasPool
                + ". Route: " + portsOfCall;
    }
}
