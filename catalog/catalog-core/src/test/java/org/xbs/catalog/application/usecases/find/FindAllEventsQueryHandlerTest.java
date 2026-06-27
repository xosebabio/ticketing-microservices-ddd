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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindAllEventsQueryHandler")
class FindAllEventsQueryHandlerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private FindAllEventsQueryHandler handler;

    @Test
    @DisplayName("should return list of EventDto for all events")
    void shouldReturnListOfEventDtoForAllEvents() {
        Event draft = DomainTestData.draftEvent();
        Event published = DomainTestData.publishedEvent();
        when(eventRepository.findAll()).thenReturn(List.of(draft, published));

        FindAllEventsQuery query = new FindAllEventsQuery();
        List<EventDto> result = handler.handle(query);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(draft.getId());
        assertThat(result.get(0).name()).isEqualTo(draft.getName());
        assertThat(result.get(0).status()).isEqualTo(Event.Status.DRAFT);
        assertThat(result.get(1).id()).isEqualTo(published.getId());
        assertThat(result.get(1).status()).isEqualTo(Event.Status.PUBLISHED);
    }

    @Test
    @DisplayName("should return empty list when no events exist")
    void shouldReturnEmptyListWhenNoEventsExist() {
        when(eventRepository.findAll()).thenReturn(List.of());

        FindAllEventsQuery query = new FindAllEventsQuery();
        List<EventDto> result = handler.handle(query);

        assertThat(result).isEmpty();
    }
}
