package org.xbs.catalog.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Venue Value Object")
class VenueTest {

    @Test
    @DisplayName("should create with valid fields")
    void shouldCreateWithValidFields() {
        Venue venue = new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");

        assertThat(venue.getName()).isEqualTo("WiZink Center");
        assertThat(venue.getAddress()).isEqualTo("Calle de Francisco Silvela 82");
        assertThat(venue.getCity()).isEqualTo("Madrid");
    }

    @Test
    @DisplayName("should throw when name is null")
    void shouldThrowWhenNameIsNull() {
        assertThatThrownBy(() -> new Venue(null, "address", "city"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue name cannot be blank");
    }

    @Test
    @DisplayName("should throw when name is blank")
    void shouldThrowWhenNameIsBlank() {
        assertThatThrownBy(() -> new Venue("   ", "address", "city"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue name cannot be blank");
    }

    @Test
    @DisplayName("should throw when address is null")
    void shouldThrowWhenAddressIsNull() {
        assertThatThrownBy(() -> new Venue("name", null, "city"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue address cannot be blank");
    }

    @Test
    @DisplayName("should throw when address is blank")
    void shouldThrowWhenAddressIsBlank() {
        assertThatThrownBy(() -> new Venue("name", "", "city"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue address cannot be blank");
    }

    @Test
    @DisplayName("should throw when city is null")
    void shouldThrowWhenCityIsNull() {
        assertThatThrownBy(() -> new Venue("name", "address", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue city cannot be blank");
    }

    @Test
    @DisplayName("should throw when city is blank")
    void shouldThrowWhenCityIsBlank() {
        assertThatThrownBy(() -> new Venue("name", "address", "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Venue city cannot be blank");
    }

    @Test
    @DisplayName("should be equal with same values")
    void shouldBeEqualWithSameValues() {
        Venue v1 = new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");
        Venue v2 = new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");

        assertThat(v1).isEqualTo(v2);
        assertThat(v1.hashCode()).isEqualTo(v2.hashCode());
    }

    @Test
    @DisplayName("should not be equal with different values")
    void shouldNotBeEqualWithDifferentValues() {
        Venue v1 = new Venue("WiZink Center", "Calle de Francisco Silvela 82", "Madrid");
        Venue v2 = new Venue("Palacio Vistalegre", "Plaza de Vistalegre 15", "Madrid");

        assertThat(v1).isNotEqualTo(v2);
    }
}
