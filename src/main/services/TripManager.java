package main.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import main.models.Trip;

public class TripManager {

    private final List<Trip> trips = new ArrayList<>();

    public void addTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip must not be null");
        }
        trips.add(trip);
    }

    public List<Trip> getTrips() {
        return Collections.unmodifiableList(trips);
    }

    public List<Trip> filter(TripFilter filter) {
        return trips.stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

    public List<Trip> sort(List<Trip> source, TripSortOption option) {
        List<Trip> result = new ArrayList<>(source);
        result.sort(option.getComparator());
        return result;
    }

    public List<Trip> findTrips(TripFilter filter, TripSortOption option) {
        return sort(filter(filter), option);
    }

    public void printTrips(List<Trip> list) {
        for (Trip trip : list) {
            System.out.println(trip.getTripInfo());
        }
    }

    public void printAllTrips() {
        System.out.println("List of trips:");
        printTrips(trips);
    }
}
