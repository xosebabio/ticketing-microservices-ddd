package org.xbs.catalog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/v1/catalog")
public interface CatalogApi {

    @GetExchange("/events")
    ResponseEntity<String> getEvents();
}
