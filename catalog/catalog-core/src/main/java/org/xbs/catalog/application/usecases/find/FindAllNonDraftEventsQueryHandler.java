package org.xbs.catalog.application.usecases.find;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xbs.catalog.application.assembler.EventAssembler;
import org.xbs.catalog.application.dto.EventDto;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.shared.application.QueryHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllNonDraftEventsQueryHandler implements QueryHandler<FindAllNonDraftEventsQuery, List<EventDto>> {

    private final EventRepository eventRepository;

    @Override
    public List<EventDto> handle(FindAllNonDraftEventsQuery query) {
        List<Event> events = eventRepository.findAllNonDraft();

        return events.stream()
                .map(EventAssembler::toDto)
                .toList();
    }
}
