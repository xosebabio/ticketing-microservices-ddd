package org.xbs.catalog.infraestructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.xbs.catalog.api.CatalogApi;

@RestController
public class CatalogController implements CatalogApi {

    @Override
    public ResponseEntity<String> getEvents() {
        return ResponseEntity.ok("It works!");
    }
}
