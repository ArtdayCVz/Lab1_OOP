package main.services;

import java.util.Comparator;

import main.models.Trip;

public enum TripSortOption {
    PRICE_ASC(Comparator.comparingDouble(Trip::getPrice)),
    PRICE_DESC(Comparator.comparingDouble(Trip::getPrice).reversed()),
    DURATION_ASC(Comparator.comparingInt(Trip::getDurationInDays)),
    DURATION_DESC(Comparator.comparingInt(Trip::getDurationInDays).reversed()),
    PRICE_PER_DAY(Comparator.comparingDouble(Trip::getPricePerDay)),
    DESTINATION_ASC(Comparator.comparing(
            (Trip t) -> t.getDestination().toString(), String.CASE_INSENSITIVE_ORDER)),
    TYPE_THEN_PRICE(Comparator.comparing((Trip t) -> t.getType())
            .thenComparingDouble(Trip::getPrice));

    private final Comparator<Trip> comparator;

    TripSortOption(Comparator<Trip> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Trip> getComparator() {
        return comparator;
    }
}
