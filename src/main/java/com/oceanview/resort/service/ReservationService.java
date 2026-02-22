package com.oceanview.resort.service;

import com.oceanview.resort.dao.ReservationDAO;
import com.oceanview.resort.dao.RoomDAO;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.ReservationStatus;
import com.oceanview.resort.pattern.builder.ReservationBuilder;
import com.oceanview.resort.pattern.observer.AuditLogger;
import com.oceanview.resort.pattern.observer.EmailNotifier;
import com.oceanview.resort.pattern.observer.ReservationSubject;
import com.oceanview.resort.util.ReservationNumberGenerator;
import com.oceanview.resort.util.ValidationUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class for reservation management.
 * Contains business logic for creating, updating, and searching reservations.
 * Uses Builder pattern for reservation creation and Observer pattern for notifications.
 */
public class ReservationService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ReservationSubject subject;

    public ReservationService() {
        // Initialize Observer pattern - register observers
        subject = new ReservationSubject();
        subject.addObserver(new EmailNotifier());
        subject.addObserver(new AuditLogger());
    }

    /**
     * Create a new reservation using the Builder pattern.
     * Validates input, checks availability, generates reservation number.
     */
    public Reservation createReservation(int guestId, int roomId, Date checkIn, Date checkOut,
                                          int numGuests, String specialRequests, int createdBy) 
                                          throws SQLException, IllegalArgumentException {
        
        // Validate dates
        if (!ValidationUtil.isValidCheckInDate(checkIn)) {
            throw new IllegalArgumentException("Check-in date must be today or in the future");
        }
        if (!ValidationUtil.isValidCheckOutDate(checkIn, checkOut)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        if (!ValidationUtil.isValidStayDuration(checkIn, checkOut)) {
            throw new IllegalArgumentException("Stay duration must be between 1 and 30 nights");
        }

        // Check room availability
        if (!reservationDAO.isRoomAvailable(roomId, checkIn, checkOut, 0)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        // Validate guest count against room max occupancy
        Room room = roomDAO.findById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        if (!ValidationUtil.isValidNumGuests(numGuests, room.getMaxOccupancy())) {
            throw new IllegalArgumentException("Number of guests exceeds room capacity (" + room.getMaxOccupancy() + ")");
        }

        // Build reservation using Builder pattern
        String reservationNumber = ReservationNumberGenerator.generate();
        Reservation reservation = new ReservationBuilder()
                .setReservationNumber(reservationNumber)
                .setGuestId(guestId)
                .setRoomId(roomId)
                .setCheckInDate(checkIn)
                .setCheckOutDate(checkOut)
                .setNumGuests(numGuests)
                .setSpecialRequests(specialRequests)
                .setStatus(ReservationStatus.CONFIRMED)
                .setCreatedBy(createdBy)
                .build();

        // Save to database
        int id = reservationDAO.create(reservation);
        reservation.setId(id);

        // Notify observers (email, audit log)
        subject.notifyReservationCreated(reservation);

        return reservation;
    }

    /**
     * Update reservation status (check-in, check-out, cancel).
     */
    public boolean updateStatus(int reservationId, ReservationStatus newStatus) throws SQLException {
        Reservation reservation = reservationDAO.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        String oldStatus = reservation.getStatus().name();
        
        // Validate status transitions
        validateStatusTransition(reservation.getStatus(), newStatus);

        boolean result = reservationDAO.updateStatus(reservationId, newStatus);

        if (result) {
            reservation.setStatus(newStatus);
            if (newStatus == ReservationStatus.CANCELLED) {
                subject.notifyReservationCancelled(reservation);
            } else {
                subject.notifyReservationUpdated(reservation, oldStatus, newStatus.name());
            }
        }

        return result;
    }

    /**
     * Validate that a status transition is allowed.
     */
    private void validateStatusTransition(ReservationStatus current, ReservationStatus target) {
        switch (current) {
            case CONFIRMED:
                if (target != ReservationStatus.CHECKED_IN && target != ReservationStatus.CANCELLED) {
                    throw new IllegalArgumentException("Confirmed reservations can only be checked-in or cancelled");
                }
                break;
            case CHECKED_IN:
                if (target != ReservationStatus.CHECKED_OUT) {
                    throw new IllegalArgumentException("Checked-in reservations can only be checked-out");
                }
                break;
            case CHECKED_OUT:
            case CANCELLED:
                throw new IllegalArgumentException("Cannot change status of a " + current.getDisplayName() + " reservation");
        }
    }

    public Reservation findById(int id) throws SQLException {
        return reservationDAO.findById(id);
    }

    public Reservation findByReservationNumber(String number) throws SQLException {
        return reservationDAO.findByReservationNumber(number);
    }

    public List<Reservation> findAll() throws SQLException {
        return reservationDAO.findAll();
    }

    public List<Reservation> findByStatus(ReservationStatus status) throws SQLException {
        return reservationDAO.findByStatus(status);
    }

    public List<Reservation> findByGuestId(int guestId) throws SQLException {
        return reservationDAO.findByGuestId(guestId);
    }

    public List<Reservation> searchByGuestName(String name) throws SQLException {
        return reservationDAO.searchByGuestName(name);
    }

    public List<Reservation> findByDateRange(Date start, Date end) throws SQLException {
        return reservationDAO.findByDateRange(start, end);
    }

    public List<Reservation> findTodayCheckIns() throws SQLException {
        return reservationDAO.findByCheckInDate(new Date(System.currentTimeMillis()));
    }

    public List<Reservation> findTodayCheckOuts() throws SQLException {
        return reservationDAO.findByCheckOutDate(new Date(System.currentTimeMillis()));
    }

    public int getActiveCount() throws SQLException {
        return reservationDAO.getActiveCount();
    }
}
