package org.xbs.catalog.application.usecases.create;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.factories.EventFactory;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;
import org.xbs.shared.application.CommandHandler;
import org.xbs.shared.domain.DomainEvent;
import org.xbs.shared.messaging.EventPublisher;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateEventCommandHandler implements CommandHandler<CreateEventCommand> {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void handle(CreateEventCommand command) {
        Event.EventResult result = EventFactory.createEvent(
                command.name(),
                command.description(),
                Event.Category.valueOf(command.category().toUpperCase()),
                new Capacity(command.capacity()),
                new DateTimeRange(command.dateTime(), command.endDateTime()),
                new Venue(command.venueName(), command.address(), command.city())
        );

        eventRepository.save(result.event());

        List<DomainEvent> domainEvents = result.domainEvents();
        for (DomainEvent domainEvent : domainEvents) {
            String topic = resolveTopic(domainEvent);
            eventPublisher.publish(topic, domainEvent);
        }
    }

    private String resolveTopic(DomainEvent domainEvent) {
        return "catalog." + domainEvent.getClass().getSimpleName().toLowerCase();
    }
}
