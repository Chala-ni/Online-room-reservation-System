package com.oceanview.resort.dao;

import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.ReservationStatus;
import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Reservation entity.
 * Handles all database operations related to room reservations.
 */
public class ReservationDAO {

    private static final String SELECT_WITH_JOINS =
        "SELECT r.*, g.name AS guest_name, g.contact_number AS guest_contact, " +
        "rm.room_number, rm.room_type, rm.rate_per_night " +
        "FROM reservations r " +
        "JOIN guests g ON r.guest_id = g.id " +
        "JOIN rooms rm ON r.room_id = rm.id ";

    public Reservation findById(int id) throws SQLException {
        String sql = SELECT_WITH_JOINS + "WHERE r.id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        }
        return null;
    }

    public Reservation findByReservationNumber(String reservationNumber) throws SQLException {
        String sql = SELECT_WITH_JOINS + "WHERE r.reservation_number = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reservationNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        }
        return null;
    }

    public List<Reservation> findAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "ORDER BY r.created_at DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    public List<Reservation> findByStatus(ReservationStatus status) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE r.status = ? ORDER BY r.check_in_date";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    public List<Reservation> findByGuestId(int guestId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE r.guest_id = ? ORDER BY r.check_in_date DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    /**
     * Find reservations with check-in date on a specific date.
     */
    public List<Reservation> findByCheckInDate(Date date) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE r.check_in_date = ? AND r.status = 'CONFIRMED' ORDER BY rm.room_number";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    /**
     * Find reservations with check-out date on a specific date.
     */
    public List<Reservation> findByCheckOutDate(Date date) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE r.check_out_date = ? AND r.status = 'CHECKED_IN' ORDER BY rm.room_number";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    /**
     * Search reservations by guest name.
     */
    public List<Reservation> searchByGuestName(String guestName) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE g.name LIKE ? ORDER BY r.created_at DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + guestName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    /**
     * Find reservations within a date range.
     */
    public List<Reservation> findByDateRange(Date startDate, Date endDate) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + 
                     "WHERE r.check_in_date >= ? AND r.check_in_date <= ? ORDER BY r.check_in_date";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    public int create(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (reservation_number, guest_id, room_id, check_in_date, " +
                     "check_out_date, num_guests, special_requests, status, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reservation.getReservationNumber());
            stmt.setInt(2, reservation.getGuestId());
            stmt.setInt(3, reservation.getRoomId());
            stmt.setDate(4, reservation.getCheckInDate());
            stmt.setDate(5, reservation.getCheckOutDate());
            stmt.setInt(6, reservation.getNumGuests());
            stmt.setString(7, reservation.getSpecialRequests());
            stmt.setString(8, reservation.getStatus().name());
            stmt.setInt(9, reservation.getCreatedBy());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET guest_id = ?, room_id = ?, check_in_date = ?, " +
                     "check_out_date = ?, num_guests = ?, special_requests = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservation.getGuestId());
            stmt.setInt(2, reservation.getRoomId());
            stmt.setDate(3, reservation.getCheckInDate());
            stmt.setDate(4, reservation.getCheckOutDate());
            stmt.setInt(5, reservation.getNumGuests());
            stmt.setString(6, reservation.getSpecialRequests());
            stmt.setString(7, reservation.getStatus().name());
            stmt.setInt(8, reservation.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int reservationId, ReservationStatus status) throws SQLException {
        String sql = "UPDATE reservations SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, reservationId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Check if a room is available for the given date range (excluding a specific reservation).
     */
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut, int excludeReservationId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations " +
                     "WHERE room_id = ? AND status IN ('CONFIRMED', 'CHECKED_IN') " +
                     "AND check_in_date < ? AND check_out_date > ? AND id != ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, checkOut);
            stmt.setDate(3, checkIn);
            stmt.setInt(4, excludeReservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return false;
    }

    public int getActiveCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status IN ('CONFIRMED', 'CHECKED_IN')";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getTodayCheckInsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE check_in_date = CURDATE() AND status = 'CONFIRMED'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getTodayCheckOutsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE check_out_date = CURDATE() AND status = 'CHECKED_IN'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setReservationNumber(rs.getString("reservation_number"));
        reservation.setGuestId(rs.getInt("guest_id"));
        reservation.setRoomId(rs.getInt("room_id"));
        reservation.setCheckInDate(rs.getDate("check_in_date"));
        reservation.setCheckOutDate(rs.getDate("check_out_date"));
        reservation.setNumGuests(rs.getInt("num_guests"));
        reservation.setSpecialRequests(rs.getString("special_requests"));
        reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));
        reservation.setCreatedBy(rs.getInt("created_by"));
        reservation.setCreatedAt(rs.getTimestamp("created_at"));
        reservation.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Joined fields
        try {
            reservation.setGuestName(rs.getString("guest_name"));
            reservation.setGuestContact(rs.getString("guest_contact"));
            reservation.setRoomNumber(rs.getString("room_number"));
            reservation.setRoomType(rs.getString("room_type"));
            reservation.setRatePerNight(rs.getDouble("rate_per_night"));
        } catch (SQLException e) {
            // Joined fields may not be present in all queries
        }

        return reservation;
    }
}
