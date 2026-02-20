package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.model.Reservation;

/**
 * Email Notifier - Concrete Observer that sends email notifications.
 * Implements ReservationObserver to receive reservation events.
 */
public class EmailNotifier implements ReservationObserver {

    @Override
    public void onReservationCreated(Reservation reservation) {
        System.out.println("[EMAIL] Sending confirmation email for reservation: " + 
                           reservation.getReservationNumber());
        // In production, this would use Jakarta Mail API to send actual emails
        // EmailService.sendConfirmationEmail(reservation);
    }

    @Override
    public void onReservationUpdated(Reservation reservation, String oldStatus, String newStatus) {
        System.out.println("[EMAIL] Sending status update email for reservation: " + 
                           reservation.getReservationNumber() + 
                           " (" + oldStatus + " -> " + newStatus + ")");
    }

    @Override
    public void onReservationCancelled(Reservation reservation) {
        System.out.println("[EMAIL] Sending cancellation email for reservation: " + 
                           reservation.getReservationNumber());
    }
}
