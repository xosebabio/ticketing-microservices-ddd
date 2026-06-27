package org.xbs.catalog.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.xbs.catalog.api.CatalogApi;
import org.xbs.catalog.api.dto.CancelEventRequest;
import org.xbs.catalog.api.dto.CreateEventRequest;
import org.xbs.catalog.api.dto.EventResponse;
import org.xbs.catalog.api.dto.EventSummaryResponse;
import org.xbs.catalog.api.dto.ModifyEventRequest;
import org.xbs.catalog.application.dto.EventDto;
import org.xbs.catalog.application.usecases.cancel.CancelEventCommand;
import org.xbs.catalog.application.usecases.cancel.CancelEventCommandHandler;
import org.xbs.catalog.application.usecases.create.CreateEventCommand;
import org.xbs.catalog.application.usecases.create.CreateEventCommandHandler;
import org.xbs.catalog.application.usecases.delete.DeleteEventCommand;
import org.xbs.catalog.application.usecases.delete.DeleteEventCommandHandler;
import org.xbs.catalog.application.usecases.find.FindAllEventsQuery;
import org.xbs.catalog.application.usecases.find.FindAllEventsQueryHandler;
import org.xbs.catalog.application.usecases.find.FindAllNonDraftEventsQuery;
import org.xbs.catalog.application.usecases.find.FindAllNonDraftEventsQueryHandler;
import org.xbs.catalog.application.usecases.find.FindEventByIdQuery;
import org.xbs.catalog.application.usecases.find.FindEventByIdQueryHandler;
import org.xbs.catalog.application.usecases.modify.ModifyEventCommand;
import org.xbs.catalog.application.usecases.modify.ModifyEventCommandHandler;
import org.xbs.catalog.application.usecases.publish.PublishEventCommand;
import org.xbs.catalog.application.usecases.publish.PublishEventCommandHandler;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.shared.messaging.EventPublisher;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CatalogController implements CatalogApi {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;
    private final FindEventByIdQueryHandler findEventByIdQueryHandler;
    private final FindAllNonDraftEventsQueryHandler findAllNonDraftEventsQueryHandler;
    private final FindAllEventsQueryHandler findAllEventsQueryHandler;

    @Override
    public ResponseEntity<String> getEvents() {
        return ResponseEntity.ok("It works!");
    }

    @Override
    public ResponseEntity<EventResponse> getEventById(UUID id) {
        FindEventByIdQuery query = new FindEventByIdQuery(id);
        EventDto eventDto = findEventByIdQueryHandler.handle(query);
        return ResponseEntity.ok(EventResponseMapper.toEventResponse(eventDto));
    }

    @Override
    public ResponseEntity<List<EventSummaryResponse>> getActiveEvents() {
        FindAllNonDraftEventsQuery query = new FindAllNonDraftEventsQuery();
        List<EventDto> events = findAllNonDraftEventsQueryHandler.handle(query);
        List<EventSummaryResponse> responses = events.stream()
                .map(EventResponseMapper::toEventSummaryResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        FindAllEventsQuery query = new FindAllEventsQuery();
        List<EventDto> events = findAllEventsQueryHandler.handle(query);
        List<EventResponse> responses = events.stream()
                .map(EventResponseMapper::toEventResponse)
                .toList();
        return ResponseEntity.ok(responses);
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

    @Override
    public ResponseEntity<Void> publishEvent(UUID id) {
        PublishEventCommand publishEventCommand = new PublishEventCommand(id);

        PublishEventCommandHandler publishEventCommandHandler =
                new PublishEventCommandHandler(eventRepository, eventPublisher);

        publishEventCommandHandler.handle(publishEventCommand);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelEvent(UUID id, CancelEventRequest cancelEventRequest) {
        CancelEventCommand cancelEventCommand = new CancelEventCommand(id, cancelEventRequest.reason());

        CancelEventCommandHandler cancelEventCommandHandler =
                new CancelEventCommandHandler(eventRepository, eventPublisher);

        cancelEventCommandHandler.handle(cancelEventCommand);
        return ResponseEntity.ok().build();
    }
}
