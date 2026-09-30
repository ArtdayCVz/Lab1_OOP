package models;
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
        this.transportType = transportType;
        this.mealPlan = mealPlan;
    }
    public Location getDeparture(){return departure;}
    public Location getDestination(){return destination;}
    public double getPrice(){return price;}
    public int getDurationInDays(){return durationInDays;}
    public TransportType getTransportType(){return transportType;}
    public MealPlan getMealPlan(){return mealPlan;}

    public abstract String getTripInfo();
}