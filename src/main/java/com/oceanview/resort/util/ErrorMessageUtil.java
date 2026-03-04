package com.oceanview.resort.util;

import java.sql.SQLException;

/**
 * Utility class for translating technical error messages to user-friendly messages.
 */
public class ErrorMessageUtil {

    /**
     * Translate SQLException to a user-friendly message.
     */
    public static String translateSQLException(SQLException e) {
        String message = e.getMessage();
        int errorCode = e.getErrorCode();
        
        // MySQL error codes
        switch (errorCode) {
            case 1062: // Duplicate entry
                return translateDuplicateKeyError(message);
            case 1451: // Cannot delete - foreign key constraint
                return "Cannot delete this record because it's being used by other data. Please remove related records first.";
            case 1452: // Cannot add - foreign key constraint
                return "Cannot save this record because it references missing data. Please check your selections.";
            case 1406: // Data too long
                return "One of the fields contains too much data. Please shorten your input.";
            case 1048: // Column cannot be null
                return "A required field is missing. Please fill in all required fields.";
            case 1045: // Access denied
            case 1044:
                return "Unable to access the database. Please contact the administrator.";
            case 1049: // Unknown database
            case 2002: // Connection refused
            case 2003:
            case 2013:
                return "Unable to connect to the database. Please try again later or contact the administrator.";
            default:
                // Check message patterns for generic handling
                if (message != null) {
                    String lowerMsg = message.toLowerCase();
                    if (lowerMsg.contains("duplicate entry") || lowerMsg.contains("unique constraint")) {
                        return translateDuplicateKeyError(message);
                    }
                    if (lowerMsg.contains("foreign key") || lowerMsg.contains("constraint")) {
                        return "Cannot complete this operation due to related data. Please check for existing references.";
                    }
                    if (lowerMsg.contains("connection") || lowerMsg.contains("timeout")) {
                        return "Database connection issue. Please try again later.";
                    }
                }
                // Generic fallback - don't expose technical details
                return "An unexpected error occurred. Please try again or contact the administrator.";
        }
    }

    /**
     * Extract a more readable message for duplicate key errors.
     */
    private static String translateDuplicateKeyError(String message) {
        if (message == null) {
            return "A record with this value already exists.";
        }
        
        String lowerMsg = message.toLowerCase();
        
        // Try to identify which field caused the duplicate
        if (lowerMsg.contains("room_number") || lowerMsg.contains("rooms.room_number")) {
            return "A room with this room number already exists. Please use a different room number.";
        }
        if (lowerMsg.contains("username") || lowerMsg.contains("users.username")) {
            return "This username is already taken. Please choose a different username.";
        }
        if (lowerMsg.contains("email") || lowerMsg.contains("users.email") || lowerMsg.contains("guests.email")) {
            return "This email address is already registered. Please use a different email.";
        }
        if (lowerMsg.contains("nic") || lowerMsg.contains("passport")) {
            return "A guest with this NIC/Passport number already exists.";
        }
        
        // Generic duplicate message
        return "A record with this value already exists. Please use a different value.";
    }

    /**
     * Check if the exception is related to a duplicate key constraint.
     */
    public static boolean isDuplicateKeyError(SQLException e) {
        return e.getErrorCode() == 1062 || 
               (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate entry"));
    }

    /**
     * Check if the exception is related to a foreign key constraint.
     */
    public static boolean isForeignKeyError(SQLException e) {
        int code = e.getErrorCode();
        return code == 1451 || code == 1452 ||
               (e.getMessage() != null && e.getMessage().toLowerCase().contains("foreign key"));
    }
}
