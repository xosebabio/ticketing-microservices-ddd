package org.xbs.catalog.domain.entities;

import lombok.Value;
import org.xbs.shared.domain.DomainEvent;
import org.xbs.catalog.domain.events.EventCancelled;
import org.xbs.catalog.domain.events.EventDeleted;
import org.xbs.catalog.domain.events.EventModified;
import org.xbs.catalog.domain.events.EventPublished;
import org.xbs.catalog.domain.events.EventSoldOut;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Value
public class Event {

    public enum Category {
        MUSIC,
        TALK,
        COMEDY,
        SCIENCE
    }

    public enum Status {
        DRAFT,
        PUBLISHED,
        CANCELLED,
        SOLD_OUT
    }

    public record EventResult(Event event, List<DomainEvent> domainEvents) {}

    UUID id;
    String name;
    Category category;
    Capacity capacity;
    DateTimeRange dateTimeRange;
    String description;
    Venue venue;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public EventResult modifyEvent(
            String name,
            Category category,
            Capacity capacity,
            DateTimeRange dateTimeRange,
            String description,
            Venue venue
    ) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        Event modified = new Event(
                this.id,
                name,
                category,
                capacity,
                dateTimeRange,
                description,
                venue,
                this.status,
                this.createdAt,
                now
        );
        EventModified event = new EventModified(this.id, name, now);
        return new EventResult(modified, List.of(event));
    }

    public EventResult publish() {
        if (this.status != Status.DRAFT) {
            throw new IllegalStateException("Only DRAFT events can be published");
        }
        Event published = new Event(
                this.id,
                this.name,
                this.category,
                this.capacity,
                this.dateTimeRange,
                this.description,
                this.venue,
                Status.PUBLISHED,
                this.createdAt,
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        EventPublished event = new EventPublished(this.id, this.name, LocalDateTime.now(ZoneId.of("UTC")));
        return new EventResult(published, List.of(event));
    }

    public EventResult cancel(String reason) {
        if (this.status != Status.PUBLISHED && this.status != Status.SOLD_OUT) {
            throw new IllegalStateException("Only PUBLISHED or SOLD_OUT events can be cancelled");
        }
        Event cancelled = new Event(
                this.id,
                this.name,
                this.category,
                this.capacity,
                this.dateTimeRange,
                this.description,
                this.venue,
                Status.CANCELLED,
                this.createdAt,
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        EventCancelled event = new EventCancelled(this.id, this.name, LocalDateTime.now(ZoneId.of("UTC")), reason);
        return new EventResult(cancelled, List.of(event));
    }

    public EventResult soldOut() {
        Event soldOut = new Event(
                this.id,
                this.name,
                this.category,
                this.capacity,
                this.dateTimeRange,
                this.description,
                this.venue,
                Status.SOLD_OUT,
                this.createdAt,
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        EventSoldOut event = new EventSoldOut(this.id, this.name, LocalDateTime.now(ZoneId.of("UTC")));
        return new EventResult(soldOut, List.of(event));
    }

    public EventResult delete() {
        if (this.status != Status.DRAFT && this.status != Status.CANCELLED) {
            throw new IllegalStateException("Only DRAFT or CANCELLED events can be deleted");
        }
        EventDeleted event = new EventDeleted(this.id, this.name, LocalDateTime.now(ZoneId.of("UTC")));
        return new EventResult(this, List.of(event));
    }
}
