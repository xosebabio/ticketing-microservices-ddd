package org.xbs.catalog.application.usecases.delete;

import org.xbs.shared.application.Command;

import java.util.UUID;

public record DeleteEventCommand(UUID id) implements Command {
}
