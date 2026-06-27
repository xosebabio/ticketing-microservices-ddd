package org.xbs.catalog.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.xbs.catalog.api.CatalogApi;
import org.xbs.catalog.api.dto.CreateEventRequest;
import org.xbs.catalog.api.dto.ModifyEventRequest;
import org.xbs.catalog.application.usecases.create.CreateEventCommand;
import org.xbs.catalog.application.usecases.create.CreateEventCommandHandler;
import org.xbs.catalog.application.usecases.delete.DeleteEventCommand;
import org.xbs.catalog.application.usecases.delete.DeleteEventCommandHandler;
import org.xbs.catalog.application.usecases.modify.ModifyEventCommand;
import org.xbs.catalog.application.usecases.modify.ModifyEventCommandHandler;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.shared.messaging.EventPublisher;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CatalogController implements CatalogApi {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    @Override
    public ResponseEntity<String> getEvents() {
        return ResponseEntity.ok("It works!");
    }

    @Override
    public ResponseEntity<Void> createEvent(CreateEventRequest createEventRequest) {
        CreateEventCommand createEventCommand = new CreateEventCommand(
                createEventRequest.name(),
                createEventRequest.description(),
                createEventRequest.category(),
                createEventRequest.capacity(),
                createEventRequest.dateTime(),
                createEventRequest.endDateTime(),
                createEventRequest.venueName(),
                createEventRequest.address(),
                createEventRequest.city()
        );

        CreateEventCommandHandler createEventCommandHandler =
                new CreateEventCommandHandler(eventRepository, eventPublisher);

        createEventCommandHandler.handle(createEventCommand);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> modifyEvent(UUID id, ModifyEventRequest modifyEventRequest) {
        ModifyEventCommand modifyEventCommand = new ModifyEventCommand(
                id,
                modifyEventRequest.name(),
                modifyEventRequest.description(),
                modifyEventRequest.category(),
                modifyEventRequest.capacity(),
                modifyEventRequest.dateTime(),
                modifyEventRequest.endDateTime(),
                modifyEventRequest.venueName(),
                modifyEventRequest.address(),
                modifyEventRequest.city()
        );

        ModifyEventCommandHandler modifyEventCommandHandler =
                new ModifyEventCommandHandler(eventRepository, eventPublisher);

        modifyEventCommandHandler.handle(modifyEventCommand);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteEvent(UUID id) {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(id);

        DeleteEventCommandHandler deleteEventCommandHandler =
                new DeleteEventCommandHandler(eventRepository, eventPublisher);

        deleteEventCommandHandler.handle(deleteEventCommand);
        return ResponseEntity.ok().build();
    }
}
