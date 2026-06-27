package org.xbs.catalog.application.usecases.modify;

import org.xbs.shared.application.Command;

import java.time.LocalDateTime;
import java.util.UUID;

public record ModifyEventCommand(
        UUID id,
        String name,
        String description,
        String category,
        int capacity,
        LocalDateTime dateTime,
        LocalDateTime endDateTime,
        String venueName,
        String address,
        String city
) implements Command {
}
