package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.dao.NotificationDAO;
import com.oceanview.resort.model.Notification;
import com.oceanview.resort.model.Reservation;

/**
 * Email Notifier - Concrete Observer that sends email notifications.
 * Implements ReservationObserver to receive reservation events.
 * Stores notifications in database for UI display.
 */
public class EmailNotifier implements ReservationObserver {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    public void onReservationCreated(Reservation reservation) {
        System.out.println("[EMAIL] Sending confirmation email for reservation: " + 
                           reservation.getReservationNumber());
        // Store notification in database
        try {
            Notification n = new Notification(
                "RESERVATION_CREATED",
                "New Reservation Created",
                "Reservation " + reservation.getReservationNumber() + " has been created successfully.",
                reservation.getId()
            );
            notificationDAO.create(n);
        } catch (Exception e) {
            System.err.println("Failed to save notification: " + e.getMessage());
        }
    }

    @Override
    public void onReservationUpdated(Reservation reservation, String oldStatus, String newStatus) {
        System.out.println("[EMAIL] Sending status update email for reservation: " + 
                           reservation.getReservationNumber() + 
                           " (" + oldStatus + " -> " + newStatus + ")");
        // Store notification in database
        try {
            Notification n = new Notification(
                "STATUS_CHANGE",
                "Reservation Status Updated",
                "Reservation " + reservation.getReservationNumber() + " status changed from " + 
                oldStatus + " to " + newStatus + ".",
                reservation.getId()
            );
            notificationDAO.create(n);
        } catch (Exception e) {
            System.err.println("Failed to save notification: " + e.getMessage());
        }
    }

    @Override
    public void onReservationCancelled(Reservation reservation) {
        System.out.println("[EMAIL] Sending cancellation email for reservation: " + 
                           reservation.getReservationNumber());
        // Store notification in database
        try {
            Notification n = new Notification(
                "CANCELLATION",
                "Reservation Cancelled",
                "Reservation " + reservation.getReservationNumber() + " has been cancelled.",
                reservation.getId()
            );
            notificationDAO.create(n);
        } catch (Exception e) {
            System.err.println("Failed to save notification: " + e.getMessage());
        }
    }
}
