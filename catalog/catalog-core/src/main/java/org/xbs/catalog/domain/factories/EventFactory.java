package org.xbs.catalog.domain.factories;

import lombok.experimental.UtilityClass;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@UtilityClass
public class EventFactory {

    public Event createEvent(
            String name,
            String description,
            Event.Category category,
            Capacity capacity,
            DateTimeRange dateTimeRange,
            Venue venue
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Event name cannot be blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Event description cannot be blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("Event category cannot be null");
        }
        if (dateTimeRange.getStart().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event start date cannot be in the past");
        }

        return new Event(
                UUID.randomUUID(),
                name,
                category,
                capacity,
                dateTimeRange,
                description,
                venue,
                Event.Status.DRAFT,
                LocalDateTime.now(ZoneId.of("UTC")),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
    }
}
