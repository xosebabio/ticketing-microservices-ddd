package org.xbs.catalog.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DateTimeRange Value Object")
class DateTimeRangeTest {

    @Test
    @DisplayName("should create with valid dates")
    void shouldCreateWithValidDates() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);

        DateTimeRange range = new DateTimeRange(start, end);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
    }

    @Test
    @DisplayName("should throw when start is null")
    void shouldThrowWhenStartIsNull() {
        assertThatThrownBy(() -> new DateTimeRange(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date cannot be null");
    }

    @Test
    @DisplayName("should throw when end is null")
    void shouldThrowWhenEndIsNull() {
        assertThatThrownBy(() -> new DateTimeRange(LocalDateTime.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date cannot be null");
    }

    @Test
    @DisplayName("should throw when end is before start")
    void shouldThrowWhenEndBeforeStart() {
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = start.minusHours(1);

        assertThatThrownBy(() -> new DateTimeRange(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date must be after start date");
    }

    @Test
    @DisplayName("should throw when end equals start")
    void shouldThrowWhenEndEqualsStart() {
        LocalDateTime date = LocalDateTime.now().plusDays(1);

        assertThatThrownBy(() -> new DateTimeRange(date, date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date must be after start date");
    }

    @Test
    @DisplayName("should be equal with same dates")
    void shouldBeEqualWithSameDates() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);

        DateTimeRange r1 = new DateTimeRange(start, end);
        DateTimeRange r2 = new DateTimeRange(start, end);

        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    @DisplayName("should not be equal with different dates")
    void shouldNotBeEqualWithDifferentDates() {
        LocalDateTime start1 = LocalDateTime.now().plusDays(1);
        LocalDateTime end1 = start1.plusHours(3);
        LocalDateTime start2 = LocalDateTime.now().plusDays(2);
        LocalDateTime end2 = start2.plusHours(3);

        DateTimeRange r1 = new DateTimeRange(start1, end1);
        DateTimeRange r2 = new DateTimeRange(start2, end2);

        assertThat(r1).isNotEqualTo(r2);
    }
}
