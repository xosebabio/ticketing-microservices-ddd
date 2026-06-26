package org.xbs.catalog.infrastructure.persistence;

import lombok.experimental.UtilityClass;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

@UtilityClass
public class EventMapper {

    public EventJpaEntity toEntity(Event event) {
        EventJpaEntity entity = new EventJpaEntity();
        entity.setId(event.getId());
        entity.setName(event.getName());
        entity.setDescription(event.getDescription());
        entity.setCategory(event.getCategory());
        entity.setCapacity(event.getCapacity().getValue());
        entity.setStartDate(event.getDateTimeRange().getStart());
        entity.setEndDate(event.getDateTimeRange().getEnd());
        entity.setVenueName(event.getVenue().getName());
        entity.setVenueAddress(event.getVenue().getAddress());
        entity.setVenueCity(event.getVenue().getCity());
        entity.setStatus(event.getStatus());
        entity.setCreatedAt(event.getCreatedAt());
        entity.setModifiedAt(event.getModifiedAt());
        return entity;
    }

    public Event toDomain(EventJpaEntity entity) {
        Capacity capacity = new Capacity(entity.getCapacity());
        DateTimeRange dateTimeRange = new DateTimeRange(entity.getStartDate(), entity.getEndDate());
        Venue venue = new Venue(entity.getVenueName(), entity.getVenueAddress(), entity.getVenueCity());

        return new Event(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                capacity,
                dateTimeRange,
                entity.getDescription(),
                venue,
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
