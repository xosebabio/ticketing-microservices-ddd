package org.xbs.catalog.application.usecases.cancel;

import org.xbs.shared.application.Command;

import java.util.UUID;

public record CancelEventCommand(UUID id, String reason) implements Command {
}
