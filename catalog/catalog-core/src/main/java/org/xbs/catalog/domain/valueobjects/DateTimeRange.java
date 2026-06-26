package org.xbs.catalog.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class DateTimeRange {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (end == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.start = start;
        this.end = end;
    }
}
