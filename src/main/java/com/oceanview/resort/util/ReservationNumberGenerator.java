package com.oceanview.resort.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique reservation numbers in the format: RES-YYYYMMDD-XXXX
 * Example: RES-20260301-0001
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

        // Reset counter for a new day
        if (!today.equals(lastDate)) {
            counter.set(0);
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
        int seq = counter.incrementAndGet();
        return String.format("RES-%s-%04d", dateStr, seq);
    }
}
