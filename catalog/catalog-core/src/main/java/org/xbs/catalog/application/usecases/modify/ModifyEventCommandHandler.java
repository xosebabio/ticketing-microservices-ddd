package org.xbs.catalog.application.usecases.modify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;
import org.xbs.shared.application.CommandHandler;
import org.xbs.shared.domain.DomainEvent;
import org.xbs.shared.messaging.EventPublisher;

@Component
@RequiredArgsConstructor
public class ModifyEventCommandHandler implements CommandHandler<ModifyEventCommand> {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void handle(ModifyEventCommand command) {
        Event event = eventRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + command.id()));

        Event.EventResult result = event.modifyEvent(
                command.name(),
                Event.Category.valueOf(command.category().toUpperCase()),
                new Capacity(command.capacity()),
                new DateTimeRange(command.dateTime(), command.endDateTime()),
                command.description(),
                new Venue(command.venueName(), command.address(), command.city())
        );

        eventRepository.save(result.event());

        for (DomainEvent domainEvent : result.domainEvents()) {
            eventPublisher.publish(domainEvent);
        }
    }
}
