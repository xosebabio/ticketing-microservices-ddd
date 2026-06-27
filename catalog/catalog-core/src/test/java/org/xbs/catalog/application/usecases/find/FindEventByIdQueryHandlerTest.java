package org.xbs.catalog.application.usecases.find;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xbs.catalog.application.dto.EventDto;
import org.xbs.catalog.domain.entities.Event;
import org.xbs.catalog.domain.repository.EventRepository;
import org.xbs.catalog.domain.testdata.DomainTestData;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindEventByIdQueryHandler")
class FindEventByIdQueryHandlerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private FindEventByIdQueryHandler handler;

    @Test
    @DisplayName("should return EventDto when event exists")
    void shouldReturnEventDtoWhenEventExists() {
        Event event = DomainTestData.publishedEvent();
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        FindEventByIdQuery query = new FindEventByIdQuery(event.getId());
        EventDto result = handler.handle(query);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(event.getId());
        assertThat(result.name()).isEqualTo(event.getName());
        assertThat(result.description()).isEqualTo(event.getDescription());
        assertThat(result.category()).isEqualTo(event.getCategory());
        assertThat(result.capacity()).isEqualTo(event.getCapacity().getValue());
        assertThat(result.status()).isEqualTo(event.getStatus());
    }

    @Test
    @DisplayName("should throw exception when event not found")
    void shouldThrowExceptionWhenEventNotFound() {
        UUID id = UUID.randomUUID();
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        FindEventByIdQuery query = new FindEventByIdQuery(id);

        assertThatThrownBy(() -> handler.handle(query))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Event not found with id: " + id);
    }
}
