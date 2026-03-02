package com.oceanview.resort.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DateUtil utility.
 */
class DateUtilTest {

    @Test
    @DisplayName("ParseDate should return valid Date for correct format")
    void parseDateValid() {
        Date date = DateUtil.parseDate("2025-06-15");
        assertNotNull(date);
        assertEquals(Date.valueOf("2025-06-15"), date);
    }

    @Test
    @DisplayName("ParseDate should return null for invalid format")
    void parseDateInvalid() {
        assertNull(DateUtil.parseDate("invalid"));
        assertNull(DateUtil.parseDate(null));
        assertNull(DateUtil.parseDate(""));
    }

    @Test
    @DisplayName("FormatForDisplay should format date correctly")
    void formatForDisplay() {
        Date date = Date.valueOf("2025-06-15");
        String formatted = DateUtil.formatForDisplay(date);
        assertNotNull(formatted);
        // Should contain day, month, year in some readable format
        assertFalse(formatted.isEmpty());
    }

    @Test
    @DisplayName("FormatForInput should return yyyy-MM-dd format")
    void formatForInput() {
        Date date = Date.valueOf("2025-06-15");
        String formatted = DateUtil.formatForInput(date);
        assertEquals("2025-06-15", formatted);
    }

    @Test
    @DisplayName("Today should return current date")
    void todayDate() {
        Date today = DateUtil.today();
        assertNotNull(today);
        assertEquals(Date.valueOf(LocalDate.now()), today);
    }

    @Test
    @DisplayName("CalculateNights should return correct number of nights")
    void calculateNights() {
        Date checkIn = Date.valueOf("2025-06-15");
        Date checkOut = Date.valueOf("2025-06-18");
        assertEquals(3, DateUtil.calculateNights(checkIn, checkOut));
    }

    @Test
    @DisplayName("CalculateNights for same day should return 0")
    void calculateNightsSameDay() {
        Date date = Date.valueOf("2025-06-15");
        assertEquals(0, DateUtil.calculateNights(date, date));
    }
}
