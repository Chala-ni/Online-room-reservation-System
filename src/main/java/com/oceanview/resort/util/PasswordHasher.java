package com.oceanview.resort.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing passwords using SHA-256.
 * Passwords are never stored in plain text in the database.
 */
public class PasswordHasher {

    /**
     * Hash a plain text password using SHA-256.
     *
     * @param password the plain text password
     * @return the hex-encoded SHA-256 hash
     */
    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verify a plain text password against a stored hash.
     */
    public static boolean verify(String password, String storedHash) {
        String hash = hash(password);
        return hash.equals(storedHash);
    }

    /**
     * Convert a byte array to a hex string.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
