package org.xbs.catalog.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String name,
        String description,
        String category,
        int capacity,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String venueName,
        String venueAddress,
        String venueCity,
        String status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
