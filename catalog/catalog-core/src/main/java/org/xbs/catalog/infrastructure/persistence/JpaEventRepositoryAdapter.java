package org.xbs.catalog.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaEventRepositoryAdapter implements EventRepository {

    private final JpaEventRepository jpaEventRepository;

    @Override
    public Optional<Event> findById(UUID id) {
        return jpaEventRepository.findById(id)
                .map(EventMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return jpaEventRepository.findAll().stream()
                .map(EventMapper::toDomain)
                .toList();
    }

    @Override
    public void save(Event event) {
        EventJpaEntity entity = EventMapper.toEntity(event);
        jpaEventRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaEventRepository.deleteById(id);
    }
}
