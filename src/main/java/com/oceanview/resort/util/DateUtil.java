package com.oceanview.resort.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date operations.
 */
public class DateUtil {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parse a date string (yyyy-MM-dd) to java.sql.Date.
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDate localDate = LocalDate.parse(dateStr.trim(), INPUT_FORMAT);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Format a java.sql.Date to display format (dd MMM yyyy).
     */
    public static String formatForDisplay(Date date) {
        if (date == null) return "";
        return date.toLocalDate().format(DISPLAY_FORMAT);
    }

    /**
     * Format a java.sql.Date to input format (yyyy-MM-dd).
     */
    public static String formatForInput(Date date) {
        if (date == null) return "";
        return date.toLocalDate().format(INPUT_FORMAT);
    }

    /**
     * Get today's date as java.sql.Date.
     */
    public static Date today() {
        return Date.valueOf(LocalDate.now());
    }

    /**
     * Calculate the number of nights between two dates.
     */
    public static long calculateNights(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) return 0;
        return (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
    }
}
