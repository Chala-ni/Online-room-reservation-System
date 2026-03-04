package com.oceanview.resort.util;

import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique reservation numbers in the format: RES-YYYYMMDD-XXXX
 * Example: RES-20260301-0001
 * 
 * The counter is initialized from the database to handle server restarts.
 */
public class ReservationNumberGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static String lastDate = "";

    /**
     * Generate a unique reservation number.
     * Format: RES-YYYYMMDD-XXXX where XXXX is a sequential counter per day.
     */
    public static synchronized String generate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Reset counter for a new day or initialize from database
        if (!today.equals(lastDate)) {
            int maxSeq = getMaxSequenceForDate(today);
            counter.set(maxSeq);
            lastDate = today;
        }

        int seq = counter.incrementAndGet();
        return String.format("RES-%s-%04d", today, seq);
    }

    /**
     * Generate from a specific date (used for testing or manual entry).
     */
    public static synchronized String generate(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int maxSeq = getMaxSequenceForDate(dateStr);
        return String.format("RES-%s-%04d", dateStr, maxSeq + 1);
    }

    /**
     * Query the database to get the maximum sequence number used for a given date.
     * This ensures we don't generate duplicates after server restart.
     */
    private static int getMaxSequenceForDate(String dateStr) {
        String pattern = "RES-" + dateStr + "-%";
        String sql = "SELECT MAX(CAST(SUBSTRING(reservation_number, 14) AS UNSIGNED)) AS max_seq " +
                     "FROM reservations WHERE reservation_number LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pattern);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_seq"); // Returns 0 if null
            }
        } catch (Exception e) {
            // Log error but don't fail - return 0 as fallback
            System.err.println("Warning: Could not query max reservation sequence: " + e.getMessage());
        }
        return 0;
    }
}
