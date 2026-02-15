package com.oceanview.resort.model;

import com.oceanview.resort.model.enums.PaymentStatus;
import java.sql.Timestamp;

/**
 * Represents a bill/invoice for a reservation at Ocean View Resort.
 */
public class Bill {
    private int id;
    private int reservationId;
    private int numNights;
    private double ratePerNight;
    private double subtotal;
    private double serviceCharge;
    private double tourismLevy;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private Timestamp generatedAt;
    private Timestamp paidAt;

    // Joined fields for display
    private String reservationNumber;
    private String guestName;
    private String roomNumber;
    private String roomType;

    public Bill() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getNumNights() { return numNights; }
    public void setNumNights(int numNights) { this.numNights = numNights; }

    public double getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(double ratePerNight) { this.ratePerNight = ratePerNight; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getServiceCharge() { return serviceCharge; }
    public void setServiceCharge(double serviceCharge) { this.serviceCharge = serviceCharge; }

    public double getTourismLevy() { return tourismLevy; }
    public void setTourismLevy(double tourismLevy) { this.tourismLevy = tourismLevy; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public Timestamp getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(Timestamp generatedAt) { this.generatedAt = generatedAt; }

    public Timestamp getPaidAt() { return paidAt; }
    public void setPaidAt(Timestamp paidAt) { this.paidAt = paidAt; }

    // Joined display fields
    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    @Override
    public String toString() {
        return "Bill{id=" + id + ", reservationId=" + reservationId + 
               ", nights=" + numNights + ", total=" + totalAmount + 
               ", status=" + paymentStatus + "}";
    }
}
