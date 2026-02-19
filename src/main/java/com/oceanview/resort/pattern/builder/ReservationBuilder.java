package com.oceanview.resort.pattern.builder;

import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.ReservationStatus;

import java.sql.Date;

/**
 * Builder Pattern - Constructs complex Reservation objects step-by-step.
 * 
 * Design Pattern: Builder (Creational)
 * - Avoids telescoping constructors (Reservation has many fields)
 * - Makes object creation code readable and maintainable
 * - Allows optional fields to be set fluently
 * 
 * Usage:
 *   Reservation res = new ReservationBuilder()
 *       .setGuestId(1)
 *       .setRoomId(2)
 *       .setCheckInDate(Date.valueOf("2026-03-01"))
 *       .setCheckOutDate(Date.valueOf("2026-03-05"))
 *       .setNumGuests(2)
 *       .setCreatedBy(1)
 *       .build();
 */
public class ReservationBuilder {

    private final Reservation reservation;

    public ReservationBuilder() {
        this.reservation = new Reservation();
        // Set defaults
        this.reservation.setStatus(ReservationStatus.CONFIRMED);
        this.reservation.setNumGuests(1);
    }

    public ReservationBuilder setReservationNumber(String reservationNumber) {
        reservation.setReservationNumber(reservationNumber);
        return this;
    }

    public ReservationBuilder setGuestId(int guestId) {
        reservation.setGuestId(guestId);
        return this;
    }

    public ReservationBuilder setRoomId(int roomId) {
        reservation.setRoomId(roomId);
        return this;
    }

    public ReservationBuilder setCheckInDate(Date checkInDate) {
        reservation.setCheckInDate(checkInDate);
        return this;
    }

    public ReservationBuilder setCheckOutDate(Date checkOutDate) {
        reservation.setCheckOutDate(checkOutDate);
        return this;
    }

    public ReservationBuilder setNumGuests(int numGuests) {
        reservation.setNumGuests(numGuests);
        return this;
    }

    public ReservationBuilder setSpecialRequests(String specialRequests) {
        reservation.setSpecialRequests(specialRequests);
        return this;
    }

    public ReservationBuilder setStatus(ReservationStatus status) {
        reservation.setStatus(status);
        return this;
    }

    public ReservationBuilder setCreatedBy(int createdBy) {
        reservation.setCreatedBy(createdBy);
        return this;
    }

    /**
     * Builds and returns the Reservation object.
     * Validates required fields before returning.
     *
     * @return the constructed Reservation
     * @throws IllegalStateException if required fields are missing
     */
    public Reservation build() {
        validate();
        return reservation;
    }

    /**
     * Validates that all required fields have been set.
     */
    private void validate() {
        if (reservation.getGuestId() <= 0) {
            throw new IllegalStateException("Guest ID is required");
        }
        if (reservation.getRoomId() <= 0) {
            throw new IllegalStateException("Room ID is required");
        }
        if (reservation.getCheckInDate() == null) {
            throw new IllegalStateException("Check-in date is required");
        }
        if (reservation.getCheckOutDate() == null) {
            throw new IllegalStateException("Check-out date is required");
        }
        if (reservation.getCheckOutDate().before(reservation.getCheckInDate()) || 
            reservation.getCheckOutDate().equals(reservation.getCheckInDate())) {
            throw new IllegalStateException("Check-out date must be after check-in date");
        }
        if (reservation.getNumGuests() < 1) {
            throw new IllegalStateException("Number of guests must be at least 1");
        }
    }
}
