package org.xbs.catalog.domain.events;

import org.xbs.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventPublished(
        UUID eventId,
        String eventName,
        LocalDateTime publishedAt
) implements DomainEvent {
}
