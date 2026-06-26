package org.xbs.catalog.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventSoldOut(
        UUID eventId,
        String eventName,
        LocalDateTime soldOutAt
) implements DomainEvent {
}
