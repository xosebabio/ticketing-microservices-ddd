package org.xbs.catalog.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Capacity Value Object")
class CapacityTest {

    @Test
    @DisplayName("should create with positive value")
    void shouldCreateWithPositiveValue() {
        Capacity capacity = new Capacity(100);
        assertThat(capacity.getValue()).isEqualTo(100);
    }

    @Test
    @DisplayName("should throw when zero")
    void shouldThrowWhenZero() {
        assertThatThrownBy(() -> new Capacity(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Capacity must be greater than 0");
    }

    @Test
    @DisplayName("should throw when negative")
    void shouldThrowWhenNegative() {
        assertThatThrownBy(() -> new Capacity(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Capacity must be greater than 0");
    }

    @Test
    @DisplayName("should be equal with same value")
    void shouldBeEqualWithSameValue() {
        Capacity c1 = new Capacity(100);
        Capacity c2 = new Capacity(100);
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
    }

    @Test
    @DisplayName("should not be equal with different value")
    void shouldNotBeEqualWithDifferentValue() {
        Capacity c1 = new Capacity(100);
        Capacity c2 = new Capacity(200);
        assertThat(c1).isNotEqualTo(c2);
    }
}
