package org.xbs.catalog.application.usecases.publish;

import org.xbs.shared.application.Command;

import java.util.UUID;

public record PublishEventCommand(UUID id) implements Command {
}
