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
@DisplayName("FindAllNonDraftEventsQueryHandler")
class FindAllNonDraftEventsQueryHandlerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private FindAllNonDraftEventsQueryHandler handler;

    @Test
    @DisplayName("should return list of EventDto for non-draft events")
    void shouldReturnListOfEventDtoForNonDraftEvents() {
        Event published = DomainTestData.publishedEvent();
        Event cancelled = DomainTestData.publishedEvent().cancel("reason").event();
        when(eventRepository.findAllNonDraft()).thenReturn(List.of(published, cancelled));

        FindAllNonDraftEventsQuery query = new FindAllNonDraftEventsQuery();
        List<EventDto> result = handler.handle(query);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(published.getId());
        assertThat(result.get(0).name()).isEqualTo(published.getName());
        assertThat(result.get(0).status()).isEqualTo(Event.Status.PUBLISHED);
        assertThat(result.get(1).status()).isEqualTo(Event.Status.CANCELLED);
    }

    @Test
    @DisplayName("should return empty list when no non-draft events exist")
    void shouldReturnEmptyListWhenNoNonDraftEventsExist() {
        when(eventRepository.findAllNonDraft()).thenReturn(List.of());

        FindAllNonDraftEventsQuery query = new FindAllNonDraftEventsQuery();
        List<EventDto> result = handler.handle(query);

        assertThat(result).isEmpty();
    }
}
