package models;

public class RecreationTrip extends Trip {
    private boolean hasPool;

    public RecreationTrip(double price, int durationInDays, String waysOfTransportation, String food, boolean hasPool) {
        super(price, durationInDays, waysOfTransportation, food); 
        this.hasPool = hasPool; 
    }

    @Override
    public String getTripInfo() {
        return "Vacation: " + getDurationInDays() + " days, " + getPrice() + "$. Pool: " + hasPool;
    }
}