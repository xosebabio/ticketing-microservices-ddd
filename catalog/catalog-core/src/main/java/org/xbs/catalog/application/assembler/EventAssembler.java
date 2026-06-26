package org.xbs.catalog.application.assembler;

import lombok.experimental.UtilityClass;
import org.xbs.catalog.application.dto.EventDto;
import org.xbs.catalog.domain.entities.Event;

@UtilityClass
public class EventAssembler {

    public EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCategory(),
                event.getCapacity().getValue(),
                event.getDateTimeRange().getStart(),
                event.getDateTimeRange().getEnd(),
                event.getVenue().getName(),
                event.getVenue().getAddress(),
                event.getVenue().getCity(),
                event.getStatus(),
                event.getCreatedAt(),
                event.getModifiedAt()
        );
    }
}
