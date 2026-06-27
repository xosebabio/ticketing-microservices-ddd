package org.xbs.catalog.api.dto;

import java.time.LocalDateTime;

public record ModifyEventRequest(
        String name,
        String description,
        String category,
        int capacity,
        LocalDateTime dateTime,
        LocalDateTime endDateTime,
        String venueName,
        String address,
        String city
) {
}
