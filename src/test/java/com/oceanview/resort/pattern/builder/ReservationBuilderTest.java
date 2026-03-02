package com.oceanview.resort.pattern.builder;

import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReservationBuilder (Builder Pattern).
 */
class ReservationBuilderTest {

    @Test
    @DisplayName("Should build a valid reservation with all required fields")
    void buildValidReservation() {
        Reservation reservation = new ReservationBuilder()
                .guestId(1)
                .roomId(1)
                .checkInDate(Date.valueOf("2025-07-01"))
                .checkOutDate(Date.valueOf("2025-07-05"))
                .numGuests(2)
                .createdBy(1)
                .build();

        assertNotNull(reservation);
        assertEquals(1, reservation.getGuestId());
        assertEquals(1, reservation.getRoomId());
        assertEquals(Date.valueOf("2025-07-01"), reservation.getCheckInDate());
        assertEquals(Date.valueOf("2025-07-05"), reservation.getCheckOutDate());
        assertEquals(2, reservation.getNumGuests());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
        assertNotNull(reservation.getReservationNumber());
        assertTrue(reservation.getReservationNumber().startsWith("RES-"));
    }

    @Test
    @DisplayName("Should include special requests when provided")
    void buildWithSpecialRequests() {
        Reservation reservation = new ReservationBuilder()
                .guestId(1)
                .roomId(1)
                .checkInDate(Date.valueOf("2025-07-01"))
                .checkOutDate(Date.valueOf("2025-07-05"))
                .numGuests(2)
                .specialRequests("Ocean view preferred")
                .createdBy(1)
                .build();

        assertEquals("Ocean view preferred", reservation.getSpecialRequests());
    }

    @Test
    @DisplayName("Should throw exception when guest ID is missing")
    void missingGuestId() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .roomId(1)
                    .checkInDate(Date.valueOf("2025-07-01"))
                    .checkOutDate(Date.valueOf("2025-07-05"))
                    .numGuests(2)
                    .createdBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when room ID is missing")
    void missingRoomId() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .guestId(1)
                    .checkInDate(Date.valueOf("2025-07-01"))
                    .checkOutDate(Date.valueOf("2025-07-05"))
                    .numGuests(2)
                    .createdBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when dates are missing")
    void missingDates() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .guestId(1)
                    .roomId(1)
                    .numGuests(2)
                    .createdBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when check-out is before check-in")
    void invalidDateRange() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .guestId(1)
                    .roomId(1)
                    .checkInDate(Date.valueOf("2025-07-05"))
                    .checkOutDate(Date.valueOf("2025-07-01"))
                    .numGuests(2)
                    .createdBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should set default numGuests to 1 if not specified")
    void defaultNumGuests() {
        Reservation reservation = new ReservationBuilder()
                .guestId(1)
                .roomId(1)
                .checkInDate(Date.valueOf("2025-07-01"))
                .checkOutDate(Date.valueOf("2025-07-05"))
                .createdBy(1)
                .build();

        assertEquals(1, reservation.getNumGuests());
    }
}
