package models;

import java.util.List;

public class CruiseTrip extends Trip {
    private String cabinType;
    private boolean hasPool;
    private List<String> portsOfCall;

    public CruiseTrip(double price, int durationInDays, String waysOfTransportation, String food, String cabinType, boolean hasPool, List<String> portsOfCall) {
        super(price, durationInDays, waysOfTransportation, food);
        this.cabinType = cabinType;
        this.hasPool = hasPool;
        this.portsOfCall = portsOfCall;
    }

    @Override 
    public String getTripInfo() {
        return "Vacation: " + getDurationInDays() + " days, " + getPrice() + "$. Cabin: " + cabinType + ". Pool: " + hasPool + ". Route: " + portsOfCall;
    }
}