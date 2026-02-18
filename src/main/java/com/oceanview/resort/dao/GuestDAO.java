package com.oceanview.resort.dao;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Guest entity.
 * Handles all database operations related to hotel guests.
 */
public class GuestDAO {

    public Guest findById(int id) throws SQLException {
        String sql = "SELECT * FROM guests WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToGuest(rs);
            }
        }
        return null;
    }

    public List<Guest> findAll() throws SQLException {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                guests.add(mapResultSetToGuest(rs));
            }
        }
        return guests;
    }

    /**
     * Search guests by name (partial match).
     */
    public List<Guest> searchByName(String name) throws SQLException {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests WHERE name LIKE ? ORDER BY name";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                guests.add(mapResultSetToGuest(rs));
            }
        }
        return guests;
    }

    /**
     * Find guest by NIC/Passport number.
     */
    public Guest findByNicPassport(String nicPassport) throws SQLException {
        String sql = "SELECT * FROM guests WHERE nic_passport = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nicPassport);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToGuest(rs);
            }
        }
        return null;
    }

    public int create(Guest guest) throws SQLException {
        String sql = "INSERT INTO guests (name, address, contact_number, email, nic_passport, nationality) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getAddress());
            stmt.setString(3, guest.getContactNumber());
            stmt.setString(4, guest.getEmail());
            stmt.setString(5, guest.getNicPassport());
            stmt.setString(6, guest.getNationality());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean update(Guest guest) throws SQLException {
        String sql = "UPDATE guests SET name = ?, address = ?, contact_number = ?, " +
                     "email = ?, nic_passport = ?, nationality = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getAddress());
            stmt.setString(3, guest.getContactNumber());
            stmt.setString(4, guest.getEmail());
            stmt.setString(5, guest.getNicPassport());
            stmt.setString(6, guest.getNationality());
            stmt.setInt(7, guest.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int guestId) throws SQLException {
        String sql = "DELETE FROM guests WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            return stmt.executeUpdate() > 0;
        }
    }

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM guests";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Guest mapResultSetToGuest(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setId(rs.getInt("id"));
        guest.setName(rs.getString("name"));
        guest.setAddress(rs.getString("address"));
        guest.setContactNumber(rs.getString("contact_number"));
        guest.setEmail(rs.getString("email"));
        guest.setNicPassport(rs.getString("nic_passport"));
        guest.setNationality(rs.getString("nationality"));
        guest.setCreatedAt(rs.getTimestamp("created_at"));
        guest.setUpdatedAt(rs.getTimestamp("updated_at"));
        return guest;
    }
}
