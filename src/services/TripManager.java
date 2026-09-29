package services;

import models.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripManager {
    private List<Trip> trips;

    public TripManager() {
        this.trips = new ArrayList<>();
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public void printAllTrips() {
        System.out.println("List of trips ");
        for (Trip trip : trips) {
            System.out.println(trip.getTripInfo());
        }
    }
}