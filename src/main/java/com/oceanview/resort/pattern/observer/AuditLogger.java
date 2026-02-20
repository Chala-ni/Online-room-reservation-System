package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.dao.AuditLogDAO;
import com.oceanview.resort.model.AuditLog;
import com.oceanview.resort.model.Reservation;

/**
 * Audit Logger - Concrete Observer that logs reservation events to the database.
 * Implements ReservationObserver to maintain an audit trail.
 */
public class AuditLogger implements ReservationObserver {

    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    public void onReservationCreated(Reservation reservation) {
        try {
            AuditLog log = new AuditLog(
                "CREATE",
                "RESERVATION",
                reservation.getId(),
                reservation.getCreatedBy(),
                "Reservation " + reservation.getReservationNumber() + " created"
            );
            auditLogDAO.create(log);
        } catch (Exception e) {
            System.err.println("Failed to log audit entry: " + e.getMessage());
        }
    }

    @Override
    public void onReservationUpdated(Reservation reservation, String oldStatus, String newStatus) {
        try {
            AuditLog log = new AuditLog(
                "STATUS_CHANGE",
                "RESERVATION",
                reservation.getId(),
                reservation.getCreatedBy(),
                "Reservation " + reservation.getReservationNumber() + 
                " status changed from " + oldStatus + " to " + newStatus
            );
            auditLogDAO.create(log);
        } catch (Exception e) {
            System.err.println("Failed to log audit entry: " + e.getMessage());
        }
    }

    @Override
    public void onReservationCancelled(Reservation reservation) {
        try {
            AuditLog log = new AuditLog(
                "CANCEL",
                "RESERVATION",
                reservation.getId(),
                reservation.getCreatedBy(),
                "Reservation " + reservation.getReservationNumber() + " cancelled"
            );
            auditLogDAO.create(log);
        } catch (Exception e) {
            System.err.println("Failed to log audit entry: " + e.getMessage());
        }
    }
}
