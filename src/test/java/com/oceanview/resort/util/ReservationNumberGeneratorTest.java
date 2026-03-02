package com.oceanview.resort.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReservationNumberGenerator.
 */
class ReservationNumberGeneratorTest {

    @Test
    @DisplayName("Generated number should start with RES- prefix")
    void startsWithPrefix() {
        String number = ReservationNumberGenerator.generate();
        assertTrue(number.startsWith("RES-"));
    }

    @Test
    @DisplayName("Generated number should have correct format RES-YYYYMMDD-XXXX")
    void correctFormat() {
        String number = ReservationNumberGenerator.generate();
        // Format: RES-YYYYMMDD-XXXX (total ~18 chars)
        assertTrue(number.matches("RES-\\d{8}-\\d{4}"));
    }

    @Test
    @DisplayName("Two generated numbers should be different")
    void uniqueNumbers() {
        String num1 = ReservationNumberGenerator.generate();
        String num2 = ReservationNumberGenerator.generate();
        assertNotEquals(num1, num2);
    }

    @Test
    @DisplayName("Generated number should not be null or empty")
    void notNullOrEmpty() {
        String number = ReservationNumberGenerator.generate();
        assertNotNull(number);
        assertFalse(number.isEmpty());
    }
}
