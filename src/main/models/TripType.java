package main.models;

public enum TripType {
    RECREATION("Recreation"),
    EXCURSION("Excursion"),
    TREATMENT("Treatment"),
    SHOPPING("Shopping"),
    CRUISE("Cruise");

    private final String displayName;

    TripType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
