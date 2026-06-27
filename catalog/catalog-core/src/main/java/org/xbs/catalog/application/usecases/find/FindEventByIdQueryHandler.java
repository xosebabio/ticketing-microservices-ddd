package org.xbs.catalog.application.usecases.find;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xbs.catalog.application.assembler.EventAssembler;
import org.xbs.catalog.application.dto.EventDto;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.shared.application.QueryHandler;

@Component
@RequiredArgsConstructor
public class FindEventByIdQueryHandler implements QueryHandler<FindEventByIdQuery, EventDto> {

    private final EventRepository eventRepository;

    @Override
    public EventDto handle(FindEventByIdQuery query) {
        Event event = eventRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + query.id()));

        return EventAssembler.toDto(event);
    }
}
