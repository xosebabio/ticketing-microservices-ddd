package org.xbs.catalog.domain.valueobjects;

import lombok.Getter;

@Getter
public class Capacity {
    private final int value;

    public Capacity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capacity capacity = (Capacity) o;
        return value == capacity.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
