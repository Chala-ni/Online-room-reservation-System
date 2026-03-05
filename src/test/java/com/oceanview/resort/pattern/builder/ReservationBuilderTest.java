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
                .setGuestId(1)
                .setRoomId(1)
                .setCheckInDate(Date.valueOf("2025-07-01"))
                .setCheckOutDate(Date.valueOf("2025-07-05"))
                .setNumGuests(2)
                .setCreatedBy(1)
                .build();

        assertNotNull(reservation);
        assertEquals(1, reservation.getGuestId());
        assertEquals(1, reservation.getRoomId());
        assertEquals(Date.valueOf("2025-07-01"), reservation.getCheckInDate());
        assertEquals(Date.valueOf("2025-07-05"), reservation.getCheckOutDate());
        assertEquals(2, reservation.getNumGuests());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    @DisplayName("Should include special requests when provided")
    void buildWithSpecialRequests() {
        Reservation reservation = new ReservationBuilder()
                .setGuestId(1)
                .setRoomId(1)
                .setCheckInDate(Date.valueOf("2025-07-01"))
                .setCheckOutDate(Date.valueOf("2025-07-05"))
                .setNumGuests(2)
                .setSpecialRequests("Ocean view preferred")
                .setCreatedBy(1)
                .build();

        assertEquals("Ocean view preferred", reservation.getSpecialRequests());
    }

    @Test
    @DisplayName("Should throw exception when guest ID is missing")
    void missingGuestId() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .setRoomId(1)
                    .setCheckInDate(Date.valueOf("2025-07-01"))
                    .setCheckOutDate(Date.valueOf("2025-07-05"))
                    .setNumGuests(2)
                    .setCreatedBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when room ID is missing")
    void missingRoomId() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .setGuestId(1)
                    .setCheckInDate(Date.valueOf("2025-07-01"))
                    .setCheckOutDate(Date.valueOf("2025-07-05"))
                    .setNumGuests(2)
                    .setCreatedBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when dates are missing")
    void missingDates() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .setGuestId(1)
                    .setRoomId(1)
                    .setNumGuests(2)
                    .setCreatedBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when check-out is before check-in")
    void invalidDateRange() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservationBuilder()
                    .setGuestId(1)
                    .setRoomId(1)
                    .setCheckInDate(Date.valueOf("2025-07-05"))
                    .setCheckOutDate(Date.valueOf("2025-07-01"))
                    .setNumGuests(2)
                    .setCreatedBy(1)
                    .build();
        });
    }

    @Test
    @DisplayName("Should set default numGuests to 1 if not specified")
    void defaultNumGuests() {
        Reservation reservation = new ReservationBuilder()
                .setGuestId(1)
                .setRoomId(1)
                .setCheckInDate(Date.valueOf("2025-07-01"))
                .setCheckOutDate(Date.valueOf("2025-07-05"))
                .setCreatedBy(1)
                .build();

        assertEquals(1, reservation.getNumGuests());
    }
}
