package models;
public abstract class Trip{
    private double price;
    private int durationInDays;
    private String waysOfTransportation;
    private String food;
    public Trip(double price, int durationInDays, String waysOfTransportation, String food){ 
            this.price=price;
            this.durationInDays=durationInDays;
            this.waysOfTransportation=waysOfTransportation;
            this.food=food;

    }
    public double getPrice(){return price;}
    public int getDurationInDays(){return durationInDays;}
    public String getWaysOfTransportation(){return waysOfTransportation;}
    public String getFood(){return food;}

    public abstract String getTripInfo();
}