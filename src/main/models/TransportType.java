package main.models;

public enum TransportType {
    PLANE("Plane"),
    BUS("Bus"),
    TRAIN("Train"),
    SHIP("Ship"),
    CAR("Car");

    private final String displayName;

    TransportType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}