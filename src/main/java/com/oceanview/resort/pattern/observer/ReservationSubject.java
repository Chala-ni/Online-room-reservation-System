package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject (Observable) in the Observer Pattern.
 * Manages a list of observers and notifies them of reservation events.
 */
public class ReservationSubject {

    private final List<ReservationObserver> observers = new ArrayList<>();

    /**
     * Register an observer to receive notifications.
     */
    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer from the notification list.
     */
    public void removeObserver(ReservationObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers that a reservation was created.
     */
    public void notifyReservationCreated(Reservation reservation) {
        for (ReservationObserver observer : observers) {
            try {
                observer.onReservationCreated(reservation);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e.getMessage());
            }
        }
    }

    /**
     * Notify all observers that a reservation status changed.
     */
    public void notifyReservationUpdated(Reservation reservation, String oldStatus, String newStatus) {
        for (ReservationObserver observer : observers) {
            try {
                observer.onReservationUpdated(reservation, oldStatus, newStatus);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e.getMessage());
            }
        }
    }

    /**
     * Notify all observers that a reservation was cancelled.
     */
    public void notifyReservationCancelled(Reservation reservation) {
        for (ReservationObserver observer : observers) {
            try {
                observer.onReservationCancelled(reservation);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e.getMessage());
            }
        }
    }
}
