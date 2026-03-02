package com.oceanview.resort.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PasswordHasher utility.
 */
class PasswordHasherTest {

    @Test
    @DisplayName("Hashing a password should produce a non-null hex string")
    void hashPassword() {
        String hash = PasswordHasher.hash("admin123");
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
        // SHA-256 produces 64 hex characters
        assertEquals(64, hash.length());
    }

    @Test
    @DisplayName("Same password should produce same hash (deterministic)")
    void samePasswordSameHash() {
        String hash1 = PasswordHasher.hash("testPassword");
        String hash2 = PasswordHasher.hash("testPassword");
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Different passwords should produce different hashes")
    void differentPasswordsDifferentHash() {
        String hash1 = PasswordHasher.hash("password1");
        String hash2 = PasswordHasher.hash("password2");
        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Verify should return true for matching password")
    void verifyMatchingPassword() {
        String hash = PasswordHasher.hash("admin123");
        assertTrue(PasswordHasher.verify("admin123", hash));
    }

    @Test
    @DisplayName("Verify should return false for non-matching password")
    void verifyNonMatchingPassword() {
        String hash = PasswordHasher.hash("admin123");
        assertFalse(PasswordHasher.verify("wrongPassword", hash));
    }

    @Test
    @DisplayName("Null password should throw exception or return false")
    void nullPasswordHandling() {
        assertThrows(Exception.class, () -> PasswordHasher.hash(null));
    }
}
