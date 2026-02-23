package com.oceanview.resort.service;

import com.oceanview.resort.dao.BillDAO;
import com.oceanview.resort.dao.ReservationDAO;
import com.oceanview.resort.dao.RoomDAO;
import com.oceanview.resort.dao.GuestDAO;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.dto.DashboardDTO;
import com.oceanview.resort.model.enums.RoomStatus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for generating reports and dashboard statistics.
 */
public class ReportService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final GuestDAO guestDAO = new GuestDAO();
    private final BillDAO billDAO = new BillDAO();

    /**
     * Get dashboard statistics.
     */
    public DashboardDTO getDashboardStats() throws SQLException {
        DashboardDTO dto = new DashboardDTO();

        // Room stats
        dto.setTotalRooms(roomDAO.getTotalCount());
        dto.setAvailableRooms(roomDAO.getCountByStatus(RoomStatus.AVAILABLE));
        dto.setOccupiedRooms(roomDAO.getCountByStatus(RoomStatus.OCCUPIED));
        dto.setMaintenanceRooms(roomDAO.getCountByStatus(RoomStatus.MAINTENANCE));

        // Calculate occupancy rate
        int totalUsable = dto.getTotalRooms() - dto.getMaintenanceRooms();
        if (totalUsable > 0) {
            dto.setOccupancyRate(Math.round((dto.getOccupiedRooms() * 100.0 / totalUsable) * 100.0) / 100.0);
        }

        // Reservation stats
        dto.setTodayCheckIns(reservationDAO.getTodayCheckInsCount());
        dto.setTodayCheckOuts(reservationDAO.getTodayCheckOutsCount());
        dto.setTotalActiveReservations(reservationDAO.getActiveCount());

        // Revenue stats
        dto.setTotalRevenue(billDAO.getTotalRevenue());
        LocalDate now = LocalDate.now();
        dto.setMonthlyRevenue(billDAO.getMonthlyRevenue(now.getYear(), now.getMonthValue()));

        // Guest stats
        dto.setTotalGuests(guestDAO.getTotalCount());

        return dto;
    }

    /**
     * Get today's check-in reservations.
     */
    public List<Reservation> getTodayCheckIns() throws SQLException {
        return reservationDAO.findByCheckInDate(new java.sql.Date(System.currentTimeMillis()));
    }

    /**
     * Get today's check-out reservations.
     */
    public List<Reservation> getTodayCheckOuts() throws SQLException {
        return reservationDAO.findByCheckOutDate(new java.sql.Date(System.currentTimeMillis()));
    }

    /**
     * Get reservations within a date range for reporting.
     */
    public List<Reservation> getReservationsByDateRange(java.sql.Date start, java.sql.Date end) throws SQLException {
        return reservationDAO.findByDateRange(start, end);
    }

    /**
     * Get all reservations for a specific guest (guest history report).
     */
    public List<Reservation> getGuestHistory(int guestId) throws SQLException {
        return reservationDAO.findByGuestId(guestId);
    }
}
