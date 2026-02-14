package com.oceanview.resort.model;

import com.oceanview.resort.model.enums.ReservationStatus;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Represents a room reservation at Ocean View Resort.
 */
public class Reservation {
    private int id;
    private String reservationNumber;
    private int guestId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private int numGuests;
    private String specialRequests;
    private ReservationStatus status;
    private int createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Joined fields for display (not stored in reservations table)
    private String guestName;
    private String guestContact;
    private String roomNumber;
    private String roomType;
    private double ratePerNight;

    public Reservation() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getNumGuests() { return numGuests; }
    public void setNumGuests(int numGuests) { this.numGuests = numGuests; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // Joined display fields
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getGuestContact() { return guestContact; }
    public void setGuestContact(String guestContact) { this.guestContact = guestContact; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(double ratePerNight) { this.ratePerNight = ratePerNight; }

    /**
     * Calculate the number of nights for this reservation.
     */
    public long getNumNights() {
        if (checkInDate != null && checkOutDate != null) {
            return (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Reservation{id=" + id + ", number='" + reservationNumber + 
               "', guestId=" + guestId + ", roomId=" + roomId + 
               ", checkIn=" + checkInDate + ", checkOut=" + checkOutDate + 
               ", status=" + status + "}";
    }
}
