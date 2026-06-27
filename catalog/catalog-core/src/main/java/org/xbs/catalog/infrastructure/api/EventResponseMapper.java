package org.xbs.catalog.infrastructure.api;

import lombok.experimental.UtilityClass;
import org.xbs.catalog.api.dto.EventResponse;
import org.xbs.catalog.api.dto.EventSummaryResponse;
import org.xbs.catalog.application.dto.EventDto;

@UtilityClass
public class EventResponseMapper {

    public EventResponse toEventResponse(EventDto dto) {
        return new EventResponse(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.category().name(),
                dto.capacity(),
                dto.startDate(),
                dto.endDate(),
                dto.venueName(),
                dto.venueAddress(),
                dto.venueCity(),
                dto.status().name(),
                dto.createdAt(),
                dto.modifiedAt()
        );
    }

    public EventSummaryResponse toEventSummaryResponse(EventDto dto) {
        return new EventSummaryResponse(
                dto.id(),
                dto.name(),
                dto.status().name(),
                dto.venueCity(),
                dto.startDate(),
                dto.endDate()
        );
    }
}
