package org.xbs.catalog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import org.xbs.catalog.api.dto.CancelEventRequest;
import org.xbs.catalog.api.dto.CreateEventRequest;
import org.xbs.catalog.api.dto.EventResponse;
import org.xbs.catalog.api.dto.EventSummaryResponse;
import org.xbs.catalog.api.dto.ModifyEventRequest;

import java.util.List;
import java.util.UUID;

@HttpExchange("/api/v1/catalog")
public interface CatalogApi {

    @GetExchange("/events")
    ResponseEntity<String> getEvents();

    @GetExchange("/events/{id}")
    ResponseEntity<EventResponse> getEventById(@PathVariable UUID id);

    @GetExchange("/events/active")
    ResponseEntity<List<EventSummaryResponse>> getActiveEvents();

    @GetExchange("/events/all")
    ResponseEntity<List<EventResponse>> getAllEvents();

    @PostExchange("/events")
    ResponseEntity<Void> createEvent(@RequestBody CreateEventRequest createEventRequest);

    @PutExchange("/events/{id}")
    ResponseEntity<Void> modifyEvent(@PathVariable UUID id, @RequestBody ModifyEventRequest modifyEventRequest);

    @DeleteExchange("/events/{id}")
    ResponseEntity<Void> deleteEvent(@PathVariable UUID id);

    @PostExchange("/events/{id}/publish")
    ResponseEntity<Void> publishEvent(@PathVariable UUID id);

    @PostExchange("/events/{id}/cancel")
    ResponseEntity<Void> cancelEvent(@PathVariable UUID id, @RequestBody CancelEventRequest cancelEventRequest);

}
