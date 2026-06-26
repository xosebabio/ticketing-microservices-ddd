package org.xbs.catalog.domain.events;

import org.xbs.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventCreated(
        UUID eventId,
        String eventName,
        LocalDateTime occurredAt
) implements DomainEvent {
}
