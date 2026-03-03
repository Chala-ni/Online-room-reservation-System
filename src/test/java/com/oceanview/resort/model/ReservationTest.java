package com.oceanview.resort.model;

import com.oceanview.resort.model.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Reservation model - specifically the getNumNights() calculation.
 */
class ReservationTest {

    @Test
    @DisplayName("getNumNights should return correct number of nights")
    void numNightsCalculation() {
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Date.valueOf("2025-06-15"));
        reservation.setCheckOutDate(Date.valueOf("2025-06-18"));

        assertEquals(3, reservation.getNumNights());
    }

    @Test
    @DisplayName("Same day check-in and check-out should return 0 nights")
    void sameDayReservation() {
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Date.valueOf("2025-06-15"));
        reservation.setCheckOutDate(Date.valueOf("2025-06-15"));

        assertEquals(0, reservation.getNumNights());
    }

    @Test
    @DisplayName("Single night stay")
    void singleNight() {
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Date.valueOf("2025-06-15"));
        reservation.setCheckOutDate(Date.valueOf("2025-06-16"));

        assertEquals(1, reservation.getNumNights());
    }

    @Test
    @DisplayName("Long stay calculation")
    void longStay() {
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Date.valueOf("2025-06-01"));
        reservation.setCheckOutDate(Date.valueOf("2025-06-30"));

        assertEquals(29, reservation.getNumNights());
    }

    @Test
    @DisplayName("Reservation status default should be null for new object")
    void defaultStatus() {
        Reservation reservation = new Reservation();
        assertNull(reservation.getStatus());
    }

    @Test
    @DisplayName("Setting and getting reservation fields")
    void setGetFields() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("RES-20250615-0001");
        reservation.setGuestId(10);
        reservation.setRoomId(5);
        reservation.setNumGuests(2);
        reservation.setSpecialRequests("Ocean view");
        reservation.setStatus(ReservationStatus.CONFIRMED);

        assertEquals(1, reservation.getId());
        assertEquals("RES-20250615-0001", reservation.getReservationNumber());
        assertEquals(10, reservation.getGuestId());
        assertEquals(5, reservation.getRoomId());
        assertEquals(2, reservation.getNumGuests());
        assertEquals("Ocean view", reservation.getSpecialRequests());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }
}
