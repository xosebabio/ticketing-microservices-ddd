package org.xbs.catalog.application.dto;

import org.xbs.catalog.domain.entities.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        UUID id,
        String name,
        String description,
        Event.Category category,
        int capacity,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String venueName,
        String venueAddress,
        String venueCity,
        Event.Status status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
