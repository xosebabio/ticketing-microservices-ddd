package org.xbs.catalog.application.usecases.create;

import org.xbs.shared.application.Command;

import java.time.LocalDateTime;

public record CreateEventCommand(
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
