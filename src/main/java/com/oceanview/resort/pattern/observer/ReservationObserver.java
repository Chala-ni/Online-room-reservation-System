package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.model.Reservation;

/**
 * Observer Pattern Interface - Listens for reservation events.
 * 
 * Design Pattern: Observer (Behavioral)
 * - Defines a one-to-many dependency between objects
 * - When a reservation event occurs, all registered observers are notified
 * - New observers can be added without modifying the subject
 * 
 * Observers in this system:
 * - EmailNotifier: sends email confirmations to guests
 * - AuditLogger: logs all reservation actions to the audit trail
 */
public interface ReservationObserver {

    /**
     * Called when a new reservation is created.
     */
    void onReservationCreated(Reservation reservation);

    /**
     * Called when a reservation status is updated.
     */
    void onReservationUpdated(Reservation reservation, String oldStatus, String newStatus);

    /**
     * Called when a reservation is cancelled.
     */
    void onReservationCancelled(Reservation reservation);
}
