package org.xbs.catalog.application.usecases.find;

import org.xbs.shared.application.Query;

import java.util.UUID;

public record FindEventByIdQuery(UUID id) implements Query {
}
