package org.xbs.catalog.domain.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.events.EventCreated;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EventFactory")
class EventFactoryTest {

    private final Venue venue = new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");
    private final Capacity capacity = new Capacity(5000);
    private final DateTimeRange dateTimeRange = new DateTimeRange(
            LocalDateTime.now().plusDays(30),
            LocalDateTime.now().plusDays(30).plusHours(3)
    );

    @Test
    @DisplayName("should create event with DRAFT status and emit EventCreated")
    void shouldCreateEventWithDraftStatus() {
        Event.EventResult result = EventFactory.createEvent(
                "Concierto Test",
                "Descripción del concierto",
                Event.Category.MUSIC,
                capacity,
                dateTimeRange,
                venue
        );

        Event event = result.event();
        assertThat(event.getId()).isNotNull();
        assertThat(event.getName()).isEqualTo("Concierto Test");
        assertThat(event.getDescription()).isEqualTo("Descripción del concierto");
        assertThat(event.getCategory()).isEqualTo(Event.Category.MUSIC);
        assertThat(event.getCapacity()).isEqualTo(capacity);
        assertThat(event.getDateTimeRange()).isEqualTo(dateTimeRange);
        assertThat(event.getVenue()).isEqualTo(venue);
        assertThat(event.getStatus()).isEqualTo(Event.Status.DRAFT);
        assertThat(event.getCreatedAt()).isNotNull();
        assertThat(event.getModifiedAt()).isNotNull();

        assertThat(result.domainEvents()).hasSize(1);
        assertThat(result.domainEvents().get(0)).isInstanceOf(EventCreated.class);
        EventCreated created = (EventCreated) result.domainEvents().get(0);
        assertThat(created.eventId()).isEqualTo(event.getId());
        assertThat(created.eventName()).isEqualTo(event.getName());
    }

    @Test
    @DisplayName("should generate unique IDs for each event")
    void shouldGenerateUniqueIds() {
        Event.EventResult result1 = EventFactory.createEvent("Event 1", "Desc 1", Event.Category.MUSIC, capacity, dateTimeRange, venue);
        Event.EventResult result2 = EventFactory.createEvent("Event 2", "Desc 2", Event.Category.TALK, capacity, dateTimeRange, venue);

        assertThat(result1.event().getId()).isNotEqualTo(result2.event().getId());
    }

    @Test
    @DisplayName("should throw when name is null")
    void shouldThrowWhenNameIsNull() {
        assertThatThrownBy(() -> EventFactory.createEvent(
                null, "Desc", Event.Category.MUSIC, capacity, dateTimeRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event name cannot be blank");
    }

    @Test
    @DisplayName("should throw when name is blank")
    void shouldThrowWhenNameIsBlank() {
        assertThatThrownBy(() -> EventFactory.createEvent(
                "   ", "Desc", Event.Category.MUSIC, capacity, dateTimeRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event name cannot be blank");
    }

    @Test
    @DisplayName("should throw when description is null")
    void shouldThrowWhenDescriptionIsNull() {
        assertThatThrownBy(() -> EventFactory.createEvent(
                "Name", null, Event.Category.MUSIC, capacity, dateTimeRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event description cannot be blank");
    }

    @Test
    @DisplayName("should throw when description is blank")
    void shouldThrowWhenDescriptionIsBlank() {
        assertThatThrownBy(() -> EventFactory.createEvent(
                "Name", "", Event.Category.MUSIC, capacity, dateTimeRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event description cannot be blank");
    }

    @Test
    @DisplayName("should throw when category is null")
    void shouldThrowWhenCategoryIsNull() {
        assertThatThrownBy(() -> EventFactory.createEvent(
                "Name", "Desc", null, capacity, dateTimeRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event category cannot be null");
    }

    @Test
    @DisplayName("should throw when start date is in the past")
    void shouldThrowWhenStartDateInPast() {
        DateTimeRange pastRange = new DateTimeRange(
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1)
        );

        assertThatThrownBy(() -> EventFactory.createEvent(
                "Name", "Desc", Event.Category.MUSIC, capacity, pastRange, venue
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event start date cannot be in the past");
    }
}
