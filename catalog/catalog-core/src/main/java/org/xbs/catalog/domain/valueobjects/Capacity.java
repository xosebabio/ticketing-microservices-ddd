package org.xbs.catalog.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Capacity {
    private final int value;

    public Capacity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.value = value;
    }

}
