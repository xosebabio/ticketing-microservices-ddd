package org.xbs.catalog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.xbs.catalog.api.dto.CreateEventRequest;

@HttpExchange("/api/v1/catalog")
public interface CatalogApi {

    @GetExchange("/events")
    ResponseEntity<String> getEvents();

    @PostExchange("/events")
    ResponseEntity<Void> createEvent(@RequestBody CreateEventRequest createEventRequest);

}
