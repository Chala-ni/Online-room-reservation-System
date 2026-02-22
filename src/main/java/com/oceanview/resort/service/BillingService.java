package com.oceanview.resort.service;

import com.oceanview.resort.dao.BillDAO;
import com.oceanview.resort.dao.ReservationDAO;
import com.oceanview.resort.model.Bill;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.PaymentStatus;
import com.oceanview.resort.pattern.strategy.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for billing operations.
 * Uses Strategy pattern to apply different billing calculations based on room type.
 */
public class BillingService {

    private final BillDAO billDAO = new BillDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    /**
     * Generate a bill for a reservation using the Strategy pattern.
     * The billing strategy is selected based on the room type.
     */
    public Bill generateBill(int reservationId) throws SQLException {
        Reservation reservation = reservationDAO.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        // Check if bill already exists
        Bill existingBill = billDAO.findByReservationId(reservationId);
        if (existingBill != null) {
            return existingBill;
        }

        // Select billing strategy based on room type
        BillingStrategy strategy = getBillingStrategy(reservation.getRoomType());

        int numNights = (int) reservation.getNumNights();
        double ratePerNight = reservation.getRatePerNight();

        // Calculate using strategy
        double subtotal = strategy.calculateSubtotal(numNights, ratePerNight);
        double serviceCharge = subtotal * strategy.getServiceChargeRate();
        double tourismLevy = subtotal * strategy.getTourismLevyRate();
        double total = subtotal + serviceCharge + tourismLevy;

        // Create bill object
        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setNumNights(numNights);
        bill.setRatePerNight(ratePerNight);
        bill.setSubtotal(Math.round(subtotal * 100.0) / 100.0);
        bill.setServiceCharge(Math.round(serviceCharge * 100.0) / 100.0);
        bill.setTourismLevy(Math.round(tourismLevy * 100.0) / 100.0);
        bill.setTotalAmount(Math.round(total * 100.0) / 100.0);
        bill.setPaymentStatus(PaymentStatus.PENDING);

        // Save to database
        int id = billDAO.create(bill);
        bill.setId(id);

        // Set display fields
        bill.setReservationNumber(reservation.getReservationNumber());
        bill.setGuestName(reservation.getGuestName());
        bill.setRoomNumber(reservation.getRoomNumber());
        bill.setRoomType(reservation.getRoomType());

        return bill;
    }

    /**
     * Select the billing strategy based on room type.
     * Strategy Pattern: different calculation per room type.
     */
    private BillingStrategy getBillingStrategy(String roomType) {
        if (roomType == null) {
            return new StandardRoomBilling();
        }
        switch (roomType.toUpperCase()) {
            case "DELUXE":
                return new DeluxeRoomBilling();
            case "SUITE":
                return new SuiteRoomBilling();
            case "STANDARD":
            default:
                return new StandardRoomBilling();
        }
    }

    /**
     * Mark a bill as paid.
     */
    public boolean markAsPaid(int billId) throws SQLException {
        return billDAO.updatePaymentStatus(billId, PaymentStatus.PAID);
    }

    public Bill findByReservationId(int reservationId) throws SQLException {
        return billDAO.findByReservationId(reservationId);
    }

    public Bill findById(int id) throws SQLException {
        return billDAO.findById(id);
    }

    public List<Bill> findAll() throws SQLException {
        return billDAO.findAll();
    }

    public double getTotalRevenue() throws SQLException {
        return billDAO.getTotalRevenue();
    }

    public double getMonthlyRevenue(int year, int month) throws SQLException {
        return billDAO.getMonthlyRevenue(year, month);
    }
}
