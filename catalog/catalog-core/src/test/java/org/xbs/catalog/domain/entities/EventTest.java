package org.xbs.catalog.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.xbs.catalog.domain.events.EventCancelled;
import org.xbs.catalog.domain.events.EventDeleted;
import org.xbs.catalog.domain.events.EventPublished;
import org.xbs.catalog.domain.events.EventSoldOut;
import org.xbs.catalog.domain.testdata.DomainTestData;
import org.xbs.catalog.domain.valueobjects.Capacity;
import org.xbs.catalog.domain.valueobjects.DateTimeRange;
import org.xbs.catalog.domain.valueobjects.Venue;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Event Aggregate")
class EventTest {

    @Nested
    @DisplayName("modifyEvent")
    class ModifyEvent {

        @Test
        @DisplayName("should update fields and preserve ID and status")
        void shouldUpdateFieldsAndPreserveIdAndStatus() {
            Event original = DomainTestData.draftEvent();
            Venue newVenue = new Venue("Palacio Vistalegre", "Plaza de Vistalegre 15", "Madrid");
            Capacity newCapacity = new Capacity(8000);
            DateTimeRange newRange = new DateTimeRange(
                    LocalDateTime.now().plusDays(60),
                    LocalDateTime.now().plusDays(60).plusHours(4)
            );

            Event.EventResult result = original.modifyEvent(
                    "Nuevo Nombre",
                    Event.Category.TALK,
                    newCapacity,
                    newRange,
                    "Nueva descripción",
                    newVenue
            );

            Event modified = result.event();
            assertThat(modified.getId()).isEqualTo(original.getId());
            assertThat(modified.getStatus()).isEqualTo(Event.Status.DRAFT);
            assertThat(modified.getName()).isEqualTo("Nuevo Nombre");
            assertThat(modified.getCategory()).isEqualTo(Event.Category.TALK);
            assertThat(modified.getCapacity()).isEqualTo(newCapacity);
            assertThat(modified.getDateTimeRange()).isEqualTo(newRange);
            assertThat(modified.getDescription()).isEqualTo("Nueva descripción");
            assertThat(modified.getVenue()).isEqualTo(newVenue);
        }

        @Test
        @DisplayName("should update modifiedAt on modify")
        void shouldUpdateModifiedAtOnModify() throws InterruptedException {
            Event original = DomainTestData.draftEvent();
            Thread.sleep(10);

            Event.EventResult result = original.modifyEvent(
                    "Nuevo Nombre",
                    original.getCategory(),
                    original.getCapacity(),
                    original.getDateTimeRange(),
                    original.getDescription(),
                    original.getVenue()
            );

            assertThat(result.event().getModifiedAt()).isAfterOrEqualTo(original.getModifiedAt());
        }

        @Test
        @DisplayName("should return empty domain events on modify")
        void shouldReturnEmptyEventsOnModify() {
            Event original = DomainTestData.draftEvent();

            Event.EventResult result = original.modifyEvent(
                    "Nuevo Nombre",
                    original.getCategory(),
                    original.getCapacity(),
                    original.getDateTimeRange(),
                    original.getDescription(),
                    original.getVenue()
            );

            assertThat(result.domainEvents()).isEmpty();
        }
    }

    @Nested
    @DisplayName("publish")
    class Publish {

        @Test
        @DisplayName("should change status to PUBLISHED")
        void shouldChangeStatusToPublished() {
            Event draft = DomainTestData.draftEvent();

            Event.EventResult result = draft.publish();

            assertThat(result.event().getStatus()).isEqualTo(Event.Status.PUBLISHED);
        }

        @Test
        @DisplayName("should emit EventPublished domain event")
        void shouldEmitEventPublished() {
            Event draft = DomainTestData.draftEvent();

            Event.EventResult result = draft.publish();

            assertThat(result.domainEvents()).hasSize(1);
            assertThat(result.domainEvents().get(0)).isInstanceOf(EventPublished.class);

            EventPublished published = (EventPublished) result.domainEvents().get(0);
            assertThat(published.eventId()).isEqualTo(draft.getId());
            assertThat(published.eventName()).isEqualTo(draft.getName());
        }

        @Test
        @DisplayName("should preserve fields on publish")
        void shouldPreserveFieldsOnPublish() {
            Event draft = DomainTestData.draftEvent();

            Event.EventResult result = draft.publish();
            Event published = result.event();

            assertThat(published.getId()).isEqualTo(draft.getId());
            assertThat(published.getName()).isEqualTo(draft.getName());
            assertThat(published.getCategory()).isEqualTo(draft.getCategory());
            assertThat(published.getCapacity()).isEqualTo(draft.getCapacity());
            assertThat(published.getDateTimeRange()).isEqualTo(draft.getDateTimeRange());
            assertThat(published.getDescription()).isEqualTo(draft.getDescription());
            assertThat(published.getVenue()).isEqualTo(draft.getVenue());
            assertThat(published.getCreatedAt()).isEqualTo(draft.getCreatedAt());
        }
    }

    @Nested
    @DisplayName("cancel")
    class Cancel {

        @Test
        @DisplayName("should change status to CANCELLED")
        void shouldChangeStatusToCancelled() {
            Event event = DomainTestData.publishedEvent();

            Event.EventResult result = event.cancel("Motivo de cancelación");

            assertThat(result.event().getStatus()).isEqualTo(Event.Status.CANCELLED);
        }

        @Test
        @DisplayName("should emit EventCancelled with reason")
        void shouldEmitEventCancelledWithReason() {
            Event event = DomainTestData.publishedEvent();
            String reason = "Motivo de cancelación";

            Event.EventResult result = event.cancel(reason);

            assertThat(result.domainEvents()).hasSize(1);
            assertThat(result.domainEvents().get(0)).isInstanceOf(EventCancelled.class);

            EventCancelled cancelled = (EventCancelled) result.domainEvents().get(0);
            assertThat(cancelled.eventId()).isEqualTo(event.getId());
            assertThat(cancelled.eventName()).isEqualTo(event.getName());
            assertThat(cancelled.reason()).isEqualTo(reason);
        }

        @Test
        @DisplayName("should preserve fields on cancel")
        void shouldPreserveFieldsOnCancel() {
            Event event = DomainTestData.publishedEvent();

            Event.EventResult result = event.cancel("Motivo");
            Event cancelled = result.event();

            assertThat(cancelled.getId()).isEqualTo(event.getId());
            assertThat(cancelled.getName()).isEqualTo(event.getName());
            assertThat(cancelled.getCategory()).isEqualTo(event.getCategory());
            assertThat(cancelled.getCapacity()).isEqualTo(event.getCapacity());
            assertThat(cancelled.getDateTimeRange()).isEqualTo(event.getDateTimeRange());
            assertThat(cancelled.getDescription()).isEqualTo(event.getDescription());
            assertThat(cancelled.getVenue()).isEqualTo(event.getVenue());
            assertThat(cancelled.getCreatedAt()).isEqualTo(event.getCreatedAt());
        }
    }

    @Nested
    @DisplayName("soldOut")
    class SoldOut {

        @Test
        @DisplayName("should change status to SOLD_OUT")
        void shouldChangeStatusToSoldOut() {
            Event event = DomainTestData.publishedEvent();

            Event.EventResult result = event.soldOut();

            assertThat(result.event().getStatus()).isEqualTo(Event.Status.SOLD_OUT);
        }

        @Test
        @DisplayName("should emit EventSoldOut domain event")
        void shouldEmitEventSoldOut() {
            Event event = DomainTestData.publishedEvent();

            Event.EventResult result = event.soldOut();

            assertThat(result.domainEvents()).hasSize(1);
            assertThat(result.domainEvents().get(0)).isInstanceOf(EventSoldOut.class);

            EventSoldOut soldOut = (EventSoldOut) result.domainEvents().get(0);
            assertThat(soldOut.eventId()).isEqualTo(event.getId());
            assertThat(soldOut.eventName()).isEqualTo(event.getName());
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("should allow delete for DRAFT events")
        void shouldAllowDeleteForDraftEvents() {
            Event draft = DomainTestData.draftEvent();

            Event.EventResult result = draft.delete();

            assertThat(result.domainEvents()).hasSize(1);
            assertThat(result.domainEvents().get(0)).isInstanceOf(EventDeleted.class);

            EventDeleted deleted = (EventDeleted) result.domainEvents().get(0);
            assertThat(deleted.eventId()).isEqualTo(draft.getId());
            assertThat(deleted.eventName()).isEqualTo(draft.getName());
        }

        @Test
        @DisplayName("should allow delete for CANCELLED events")
        void shouldAllowDeleteForCancelledEvents() {
            Event cancelled = DomainTestData.publishedEvent().cancel("reason").event();

            Event.EventResult result = cancelled.delete();

            assertThat(result.domainEvents()).hasSize(1);
            assertThat(result.domainEvents().get(0)).isInstanceOf(EventDeleted.class);
        }

        @Test
        @DisplayName("should throw exception when deleting PUBLISHED events")
        void shouldThrowExceptionWhenDeletingPublishedEvents() {
            Event published = DomainTestData.publishedEvent();

            assertThatThrownBy(published::delete)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Only DRAFT or CANCELLED events can be deleted");
        }

        @Test
        @DisplayName("should throw exception when deleting SOLD_OUT events")
        void shouldThrowExceptionWhenDeletingSoldOutEvents() {
            Event soldOut = DomainTestData.publishedEvent().soldOut().event();

            assertThatThrownBy(soldOut::delete)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Only DRAFT or CANCELLED events can be deleted");
        }

        @Test
        @DisplayName("should emit EventDeleted with correct data")
        void shouldEmitEventDeletedWithCorrectData() {
            Event draft = DomainTestData.draftEvent();

            Event.EventResult result = draft.delete();

            EventDeleted deleted = (EventDeleted) result.domainEvents().get(0);
            assertThat(deleted.eventId()).isEqualTo(draft.getId());
            assertThat(deleted.eventName()).isEqualTo(draft.getName());
            assertThat(deleted.deletedAt()).isNotNull();
        }
    }
}
