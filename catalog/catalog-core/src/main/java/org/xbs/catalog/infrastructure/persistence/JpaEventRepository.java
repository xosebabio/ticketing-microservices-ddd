package org.xbs.catalog.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEventRepository extends JpaRepository<EventJpaEntity, UUID> {
}
