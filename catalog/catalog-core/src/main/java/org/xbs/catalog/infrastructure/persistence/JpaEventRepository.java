package org.xbs.catalog.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xbs.catalog.domain.entities.Event;

import java.util.List;
import java.util.UUID;

public interface JpaEventRepository extends JpaRepository<EventJpaEntity, UUID> {
    List<EventJpaEntity> findByStatusNot(Event.Status status);
}
