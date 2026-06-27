package org.xbs.catalog.application.usecases.cancel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.shared.application.CommandHandler;
import org.xbs.shared.domain.DomainEvent;
import org.xbs.shared.messaging.EventPublisher;

@Component
@RequiredArgsConstructor
public class CancelEventCommandHandler implements CommandHandler<CancelEventCommand> {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void handle(CancelEventCommand command) {
        Event event = eventRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + command.id()));

        Event.EventResult result = event.cancel(command.reason());

        eventRepository.save(result.event());

        for (DomainEvent domainEvent : result.domainEvents()) {
            eventPublisher.publish(domainEvent);
        }
    }
}
