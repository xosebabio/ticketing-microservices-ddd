package org.xbs.catalog.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventSummaryResponse(
        UUID id,
        String name,
        String status,
        String city,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
