package org.xbs.catalog.domain.testdata;

import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public final class DomainTestData {

    private DomainTestData() {}

    public static Venue validVenue() {
        return new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");
    }

    public static Capacity validCapacity() {
        return new Capacity(5000);
    }

    public static DateTimeRange futureDateTimeRange() {
        LocalDateTime start = LocalDateTime.now().plusDays(30);
        LocalDateTime end = start.plusHours(3);
        return new DateTimeRange(start, end);
    }

    public static Event draftEvent() {
        return new Event(
                UUID.randomUUID(),
                "Concierto Test",
                Event.Category.MUSIC,
                validCapacity(),
                futureDateTimeRange(),
                "Descripción del concierto",
                validVenue(),
                Event.Status.DRAFT,
                LocalDateTime.now(ZoneId.of("UTC")),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
    }

    public static Event publishedEvent() {
        return new Event(
                UUID.randomUUID(),
                "Concierto Test",
                Event.Category.MUSIC,
                validCapacity(),
                futureDateTimeRange(),
                "Descripción del concierto",
                validVenue(),
                Event.Status.PUBLISHED,
                LocalDateTime.now(ZoneId.of("UTC")),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
    }
}
