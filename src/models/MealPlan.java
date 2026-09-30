package models;

public enum MealPlan {
    NONE("No meals"),
    BREAKFAST("Breakfast only"),
    HALF_BOARD("Half board"),
    FULL_BOARD("Full board"),
    ALL_INCLUSIVE("All inclusive");

    private final String displayName;

    MealPlan(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}