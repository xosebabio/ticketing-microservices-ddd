package org.xbs.catalog.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventCancelled(
        UUID eventId,
        String eventName,
        LocalDateTime cancelledAt,
        String reason
) implements DomainEvent {
}
