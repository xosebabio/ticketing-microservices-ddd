package org.xbs.catalog.domain.valueobjects;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Venue {
    private final String name;
    private final String address;
    private final String city;

    public Venue(String name, String address, String city) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Venue name cannot be blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Venue address cannot be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("Venue city cannot be blank");
        }
        this.name = name;
        this.address = address;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Objects.equals(name, venue.name) &&
               Objects.equals(address, venue.address) &&
               Objects.equals(city, venue.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city);
    }
}
