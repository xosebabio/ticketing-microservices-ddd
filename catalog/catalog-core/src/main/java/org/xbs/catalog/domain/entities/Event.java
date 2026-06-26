package org.xbs.catalog.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {

    enum Category{
        MUSIC,
        TALK,
        COMEDY,
        SCIENCE
    }

    enum Status{
        DRAFT,
        PUBLISHED,
        CANCELLED,
        SOLD_OUT
    }

    private UUID id;

    private String name;

    private Category category;

    private Integer capacity;

    private LocalDateTime dateTime;

    private LocalDateTime endDateTime;

    private String description;

    private String venue;

    private Status status;

}
