package services;

import java.util.EnumSet;
import java.util.Set;

import models.MealPlan;
import models.TransportType;
import models.Trip;
import models.TripType;

public final class TripFilter {

    private final Double minPrice;
    private final Double maxPrice;
    private final Integer minDays;
    private final Integer maxDays;
    private final Set<TripType> types;
    private final Set<TransportType> transports;
    private final Set<MealPlan> mealPlans;
    private final String country;

    private TripFilter(Builder b) {
        this.minPrice = b.minPrice;
        this.maxPrice = b.maxPrice;
        this.minDays = b.minDays;
        this.maxDays = b.maxDays;
        this.types = copy(b.types, TripType.class);
        this.transports = copy(b.transports, TransportType.class);
        this.mealPlans = copy(b.mealPlans, MealPlan.class);
        this.country = b.country;
    }

    private static <E extends Enum<E>> Set<E> copy(Set<E> source, Class<E> type) {
        return source.isEmpty() ? EnumSet.noneOf(type) : EnumSet.copyOf(source);
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean matches(Trip trip) {
        return (minPrice == null || trip.getPrice() >= minPrice)
                && (maxPrice == null || trip.getPrice() <= maxPrice)
                && (minDays == null || trip.getDurationInDays() >= minDays)
                && (maxDays == null || trip.getDurationInDays() <= maxDays)
                && (types.isEmpty() || types.contains(trip.getType()))
                && (transports.isEmpty() || transports.contains(trip.getTransportType()))
                && (mealPlans.isEmpty() || mealPlans.contains(trip.getMealPlan()))
                && (country == null
                        || trip.getDestination().getCountry().equalsIgnoreCase(country));
    }

    public static final class Builder {

        private Double minPrice;
        private Double maxPrice;
        private Integer minDays;
        private Integer maxDays;
        private final Set<TripType> types = EnumSet.noneOf(TripType.class);
        private final Set<TransportType> transports = EnumSet.noneOf(TransportType.class);
        private final Set<MealPlan> mealPlans = EnumSet.noneOf(MealPlan.class);
        private String country;

        public Builder minPrice(double value) {
            this.minPrice = value;
            return this;
        }

        public Builder maxPrice(double value) {
            this.maxPrice = value;
            return this;
        }

        public Builder minDays(int value) {
            this.minDays = value;
            return this;
        }

        public Builder maxDays(int value) {
            this.maxDays = value;
            return this;
        }

        public Builder type(TripType value) {
            this.types.add(value);
            return this;
        }

        public Builder transport(TransportType value) {
            this.transports.add(value);
            return this;
        }

        public Builder mealPlan(MealPlan value) {
            this.mealPlans.add(value);
            return this;
        }

        public Builder country(String value) {
            this.country = value;
            return this;
        }

        public TripFilter build() {
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                throw new IllegalArgumentException("minPrice is greater than maxPrice");
            }
            if (minDays != null && maxDays != null && minDays > maxDays) {
                throw new IllegalArgumentException("minDays is greater than maxDays");
            }
            return new TripFilter(this);
        }
    }
}
