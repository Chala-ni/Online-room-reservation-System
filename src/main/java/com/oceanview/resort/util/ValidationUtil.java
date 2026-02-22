package com.oceanview.resort.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Utility class for validating user inputs.
 * Implements server-side validation rules for all entities.
 */
public class ValidationUtil {

    // Regex patterns
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s.'-]{2,100}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern NIC_PATTERN = Pattern.compile("^(\\d{12}|\\d{9}[vVxX])$");
    private static final Pattern PASSPORT_PATTERN = Pattern.compile("^[A-Z]\\d{7,8}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,50}$");
    private static final Pattern ROOM_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,10}$");

    /**
     * Validate guest name: 2-100 chars, letters and spaces only.
     */
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate address: 5-500 chars, non-empty.
     */
    public static boolean isValidAddress(String address) {
        return address != null && address.trim().length() >= 5 && address.trim().length() <= 500;
    }

    /**
     * Validate Sri Lankan phone number: 10 digits starting with 0.
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate email format (optional field - empty is valid).
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email is optional
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate NIC or Passport number.
     * NIC: 12 digits or 9 digits + V/X
     * Passport: Letter followed by 7-8 digits
     */
    public static boolean isValidNicPassport(String nicPassport) {
        if (nicPassport == null || nicPassport.trim().isEmpty()) {
            return false;
        }
        String val = nicPassport.trim();
        return NIC_PATTERN.matcher(val).matches() || PASSPORT_PATTERN.matcher(val).matches();
    }

    /**
     * Validate username: 3-50 chars, alphanumeric + underscore.
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    /**
     * Validate password: min 8 chars, at least 1 uppercase, 1 lowercase, 1 digit.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }

    /**
     * Validate room number: 1-10 alphanumeric chars.
     */
    public static boolean isValidRoomNumber(String roomNumber) {
        return roomNumber != null && ROOM_NUMBER_PATTERN.matcher(roomNumber.trim()).matches();
    }

    /**
     * Validate rate per night: positive, max 99999.99.
     */
    public static boolean isValidRate(double rate) {
        return rate > 0 && rate <= 99999.99;
    }

    /**
     * Validate check-in date: must be today or in the future.
     */
    public static boolean isValidCheckInDate(Date checkInDate) {
        if (checkInDate == null) return false;
        LocalDate today = LocalDate.now();
        LocalDate checkIn = checkInDate.toLocalDate();
        return !checkIn.isBefore(today);
    }

    /**
     * Validate check-out date: must be after check-in date.
     */
    public static boolean isValidCheckOutDate(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) return false;
        return checkOutDate.after(checkInDate);
    }

    /**
     * Validate stay duration: 1 to 30 nights.
     */
    public static boolean isValidStayDuration(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) return false;
        long nights = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
        return nights >= 1 && nights <= 30;
    }

    /**
     * Validate number of guests: 1 to max occupancy.
     */
    public static boolean isValidNumGuests(int numGuests, int maxOccupancy) {
        return numGuests >= 1 && numGuests <= maxOccupancy;
    }

    /**
     * Check if a string is null or empty.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
