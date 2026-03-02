package com.oceanview.resort.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValidationUtil - TDD approach.
 * Tests written to verify all validation methods.
 */
class ValidationUtilTest {

    // ============================================================
    // Name Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Name Validation")
    class NameValidation {

        @Test
        @DisplayName("Valid name should return true")
        void validName() {
            assertTrue(ValidationUtil.isValidName("John Doe"));
            assertTrue(ValidationUtil.isValidName("Alice"));
            assertTrue(ValidationUtil.isValidName("Mary Jane Watson"));
        }

        @Test
        @DisplayName("Null or empty name should return false")
        void nullOrEmptyName() {
            assertFalse(ValidationUtil.isValidName(null));
            assertFalse(ValidationUtil.isValidName(""));
            assertFalse(ValidationUtil.isValidName("   "));
        }

        @Test
        @DisplayName("Single character name should return false")
        void singleCharName() {
            assertFalse(ValidationUtil.isValidName("A"));
        }

        @Test
        @DisplayName("Name with numbers should return false")
        void nameWithNumbers() {
            assertFalse(ValidationUtil.isValidName("John123"));
        }
    }

    // ============================================================
    // Email Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Email Validation")
    class EmailValidation {

        @Test
        @DisplayName("Valid email addresses should return true")
        void validEmails() {
            assertTrue(ValidationUtil.isValidEmail("test@example.com"));
            assertTrue(ValidationUtil.isValidEmail("user.name@domain.co.uk"));
            assertTrue(ValidationUtil.isValidEmail("alice@company.org"));
        }

        @Test
        @DisplayName("Invalid email addresses should return false")
        void invalidEmails() {
            assertFalse(ValidationUtil.isValidEmail(null));
            assertFalse(ValidationUtil.isValidEmail(""));
            assertFalse(ValidationUtil.isValidEmail("notanemail"));
            assertFalse(ValidationUtil.isValidEmail("missing@"));
            assertFalse(ValidationUtil.isValidEmail("@nodomain.com"));
        }
    }

    // ============================================================
    // Contact Number Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Contact Number Validation")
    class ContactNumberValidation {

        @Test
        @DisplayName("Valid phone numbers should return true")
        void validNumbers() {
            assertTrue(ValidationUtil.isValidContactNumber("+44 1234 567890"));
            assertTrue(ValidationUtil.isValidContactNumber("0771234567"));
            assertTrue(ValidationUtil.isValidContactNumber("+1-555-123-4567"));
        }

        @Test
        @DisplayName("Invalid phone numbers should return false")
        void invalidNumbers() {
            assertFalse(ValidationUtil.isValidContactNumber(null));
            assertFalse(ValidationUtil.isValidContactNumber(""));
            assertFalse(ValidationUtil.isValidContactNumber("abc"));
            assertFalse(ValidationUtil.isValidContactNumber("12"));
        }
    }

    // ============================================================
    // Address Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Address Validation")
    class AddressValidation {

        @Test
        @DisplayName("Valid address should return true")
        void validAddress() {
            assertTrue(ValidationUtil.isValidAddress("123 Main Street, London"));
            assertTrue(ValidationUtil.isValidAddress("Ocean View Resort, Suite 101"));
        }

        @Test
        @DisplayName("Short address should return false")
        void shortAddress() {
            assertFalse(ValidationUtil.isValidAddress("Hi"));
            assertFalse(ValidationUtil.isValidAddress(null));
        }
    }

    // ============================================================
    // NIC/Passport Validation Tests
    // ============================================================
    @Nested
    @DisplayName("NIC/Passport Validation")
    class NicPassportValidation {

        @Test
        @DisplayName("Valid NIC/Passport should return true")
        void validNicPassport() {
            assertTrue(ValidationUtil.isValidNicOrPassport("AB1234567"));
            assertTrue(ValidationUtil.isValidNicOrPassport("123456789V"));
            assertTrue(ValidationUtil.isValidNicOrPassport("P12345678"));
        }

        @Test
        @DisplayName("Invalid NIC/Passport should return false")
        void invalidNicPassport() {
            assertFalse(ValidationUtil.isValidNicOrPassport(null));
            assertFalse(ValidationUtil.isValidNicOrPassport(""));
            assertFalse(ValidationUtil.isValidNicOrPassport("AB"));
        }
    }

    // ============================================================
    // Username Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Username Validation")
    class UsernameValidation {

        @Test
        @DisplayName("Valid usernames should return true")
        void validUsernames() {
            assertTrue(ValidationUtil.isValidUsername("admin"));
            assertTrue(ValidationUtil.isValidUsername("receptionist1"));
            assertTrue(ValidationUtil.isValidUsername("user_name"));
        }

        @Test
        @DisplayName("Invalid usernames should return false")
        void invalidUsernames() {
            assertFalse(ValidationUtil.isValidUsername(null));
            assertFalse(ValidationUtil.isValidUsername(""));
            assertFalse(ValidationUtil.isValidUsername("ab")); // too short
        }
    }

    // ============================================================
    // Password Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Password Validation")
    class PasswordValidation {

        @Test
        @DisplayName("Valid passwords should return true")
        void validPasswords() {
            assertTrue(ValidationUtil.isValidPassword("admin123"));
            assertTrue(ValidationUtil.isValidPassword("secureP@ss"));
            assertTrue(ValidationUtil.isValidPassword("123456"));
        }

        @Test
        @DisplayName("Short passwords should return false")
        void shortPasswords() {
            assertFalse(ValidationUtil.isValidPassword(null));
            assertFalse(ValidationUtil.isValidPassword(""));
            assertFalse(ValidationUtil.isValidPassword("12345")); // too short
        }
    }

    // ============================================================
    // Date Validation Tests
    // ============================================================
    @Nested
    @DisplayName("Date Validation")
    class DateValidation {

        @Test
        @DisplayName("Valid date format should return true")
        void validDates() {
            assertTrue(ValidationUtil.isValidDate("2025-06-15"));
            assertTrue(ValidationUtil.isValidDate("2025-12-31"));
        }

        @Test
        @DisplayName("Invalid date format should return false")
        void invalidDates() {
            assertFalse(ValidationUtil.isValidDate(null));
            assertFalse(ValidationUtil.isValidDate(""));
            assertFalse(ValidationUtil.isValidDate("15-06-2025"));
            assertFalse(ValidationUtil.isValidDate("not-a-date"));
        }

        @Test
        @DisplayName("Valid date range should return true")
        void validDateRange() {
            java.sql.Date checkIn = java.sql.Date.valueOf("2025-06-15");
            java.sql.Date checkOut = java.sql.Date.valueOf("2025-06-18");
            assertTrue(ValidationUtil.isValidDateRange(checkIn, checkOut));
        }

        @Test
        @DisplayName("Invalid date range should return false")
        void invalidDateRange() {
            java.sql.Date date1 = java.sql.Date.valueOf("2025-06-18");
            java.sql.Date date2 = java.sql.Date.valueOf("2025-06-15");
            assertFalse(ValidationUtil.isValidDateRange(date1, date2));
            assertFalse(ValidationUtil.isValidDateRange(date1, date1));
        }
    }
}
