package com.oceanview.resort.dao;

import com.oceanview.resort.model.Bill;
import com.oceanview.resort.model.enums.PaymentStatus;
import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Bill entity.
 * Handles all database operations related to billing.
 */
public class BillDAO {

    private static final String SELECT_WITH_JOINS =
        "SELECT b.*, r.reservation_number, g.name AS guest_name, " +
        "rm.room_number, rm.room_type " +
        "FROM bills b " +
        "JOIN reservations r ON b.reservation_id = r.id " +
        "JOIN guests g ON r.guest_id = g.id " +
        "JOIN rooms rm ON r.room_id = rm.id ";

    public Bill findById(int id) throws SQLException {
        String sql = SELECT_WITH_JOINS + "WHERE b.id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBill(rs);
            }
        }
        return null;
    }

    public Bill findByReservationId(int reservationId) throws SQLException {
        String sql = SELECT_WITH_JOINS + "WHERE b.reservation_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBill(rs);
            }
        }
        return null;
    }

    public List<Bill> findAll() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "ORDER BY b.generated_at DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
        }
        return bills;
    }

    /**
     * Generate a bill using the stored procedure.
     */
    public void generateBill(int reservationId) throws SQLException {
        String sql = "{CALL sp_calculate_bill(?)}";
        try (Connection conn = DBConnection.getInstance().getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, reservationId);
            stmt.execute();
        }
    }

    public int create(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (reservation_id, num_nights, rate_per_night, subtotal, " +
                     "service_charge, tourism_levy, total_amount, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bill.getReservationId());
            stmt.setInt(2, bill.getNumNights());
            stmt.setDouble(3, bill.getRatePerNight());
            stmt.setDouble(4, bill.getSubtotal());
            stmt.setDouble(5, bill.getServiceCharge());
            stmt.setDouble(6, bill.getTourismLevy());
            stmt.setDouble(7, bill.getTotalAmount());
            stmt.setString(8, bill.getPaymentStatus().name());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean updatePaymentStatus(int billId, PaymentStatus status) throws SQLException {
        String sql = "UPDATE bills SET payment_status = ?, paid_at = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setTimestamp(2, status == PaymentStatus.PAID ? new Timestamp(System.currentTimeMillis()) : null);
            stmt.setInt(3, billId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Get total revenue (all paid bills).
     */
    public double getTotalRevenue() throws SQLException {
        String sql = "SELECT IFNULL(SUM(total_amount), 0) FROM bills WHERE payment_status = 'PAID'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    /**
     * Get monthly revenue for a given year and month.
     */
    public double getMonthlyRevenue(int year, int month) throws SQLException {
        String sql = "SELECT IFNULL(SUM(b.total_amount), 0) FROM bills b " +
                     "JOIN reservations r ON b.reservation_id = r.id " +
                     "WHERE YEAR(r.check_in_date) = ? AND MONTH(r.check_in_date) = ? " +
                     "AND r.status IN ('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT')";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("id"));
        bill.setReservationId(rs.getInt("reservation_id"));
        bill.setNumNights(rs.getInt("num_nights"));
        bill.setRatePerNight(rs.getDouble("rate_per_night"));
        bill.setSubtotal(rs.getDouble("subtotal"));
        bill.setServiceCharge(rs.getDouble("service_charge"));
        bill.setTourismLevy(rs.getDouble("tourism_levy"));
        bill.setTotalAmount(rs.getDouble("total_amount"));
        bill.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
        bill.setGeneratedAt(rs.getTimestamp("generated_at"));
        bill.setPaidAt(rs.getTimestamp("paid_at"));

        // Joined fields
        try {
            bill.setReservationNumber(rs.getString("reservation_number"));
            bill.setGuestName(rs.getString("guest_name"));
            bill.setRoomNumber(rs.getString("room_number"));
            bill.setRoomType(rs.getString("room_type"));
        } catch (SQLException e) {
            // Joined fields may not be present
        }

        return bill;
    }
}
