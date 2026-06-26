package org.xbs.catalog.domain.repository;

import org.xbs.catalog.domain.entities.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    Optional<Event> findById(UUID id);
    List<Event> findAll();
    void save(Event event);
    void delete(UUID id);
}
