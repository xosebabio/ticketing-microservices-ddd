package org.xbs.catalog.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventPublished(
        UUID eventId,
        String eventName,
        LocalDateTime publishedAt
) implements DomainEvent {
}
