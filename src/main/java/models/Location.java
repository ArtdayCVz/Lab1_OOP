package models;

import java.util.Locale;
import java.util.Objects;

public final class Location {

    private final String city;
    private final String country;

    public Location(String city, String country) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City can't be empty");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country can't be empty");
        }
        this.city = city.trim();
        this.country = country.trim();
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location other = (Location) o;
        return city.equalsIgnoreCase(other.city)
                && country.equalsIgnoreCase(other.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city.toLowerCase(Locale.ROOT),
                country.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
