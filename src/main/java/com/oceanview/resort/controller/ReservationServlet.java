package com.oceanview.resort.controller;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.User;
import com.oceanview.resort.model.enums.ReservationStatus;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.service.GuestService;
import com.oceanview.resort.service.ReservationService;
import com.oceanview.resort.service.RoomService;
import com.oceanview.resort.util.DateUtil;
import com.oceanview.resort.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Reservation Servlet - handles all reservation CRUD operations.
 */
@WebServlet("/reservations/*")
public class ReservationServlet extends HttpServlet {

    private final ReservationService reservationService = new ReservationService();
    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listReservations(request, response);
            } else if (pathInfo.equals("/new")) {
                showCreateForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                viewReservation(request, response);
            } else if (pathInfo.equals("/checkin")) {
                checkIn(request, response);
            } else if (pathInfo.equals("/checkout")) {
                checkOut(request, response);
            } else if (pathInfo.equals("/cancel")) {
                cancelReservation(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/new")) {
                createReservation(request, response);
            } else if (pathInfo.equals("/edit")) {
                updateReservation(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listReservations(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String search = request.getParameter("search");
        String statusFilter = request.getParameter("status");
        List<Reservation> reservations;

        if (search != null && !search.trim().isEmpty()) {
            reservations = reservationService.searchByGuestName(search.trim());
            request.setAttribute("search", search);
        } else if (statusFilter != null && !statusFilter.isEmpty()) {
            ReservationStatus status = ReservationStatus.valueOf(statusFilter);
            reservations = reservationService.findByStatus(status);
            request.setAttribute("statusFilter", statusFilter);
        } else {
            reservations = reservationService.findAll();
        }

        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/views/reservation/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Guest> guests = guestService.findAll();
        List<Room> rooms = roomService.findByStatus(RoomStatus.AVAILABLE);
        request.setAttribute("guests", guests);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/WEB-INF/views/reservation/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationService.findById(id);

        if (reservation == null) {
            response.sendRedirect(request.getContextPath() + "/reservations?error=Reservation+not+found");
            return;
        }

        List<Guest> guests = guestService.findAll();
        List<Room> rooms = roomService.findByStatus(RoomStatus.AVAILABLE);
        // Also include the currently assigned room
        Room currentRoom = roomService.findById(reservation.getRoomId());
        if (currentRoom != null && !rooms.contains(currentRoom)) {
            rooms.add(0, currentRoom);
        }

        request.setAttribute("reservation", reservation);
        request.setAttribute("guests", guests);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/WEB-INF/views/reservation/form.jsp").forward(request, response);
    }

    private void viewReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationService.findById(id);

        if (reservation == null) {
            response.sendRedirect(request.getContextPath() + "/reservations?error=Reservation+not+found");
            return;
        }

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/WEB-INF/views/reservation/view.jsp").forward(request, response);
    }

    private void createReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        // Extract form data
        String guestIdStr = request.getParameter("guestId");
        String roomIdStr = request.getParameter("roomId");
        String checkInStr = request.getParameter("checkInDate");
        String checkOutStr = request.getParameter("checkOutDate");
        String numGuestsStr = request.getParameter("numGuests");
        String specialRequests = request.getParameter("specialRequests");

        // Validate
        StringBuilder errors = new StringBuilder();
        if (guestIdStr == null || guestIdStr.isEmpty()) errors.append("Guest is required. ");
        if (roomIdStr == null || roomIdStr.isEmpty()) errors.append("Room is required. ");
        if (!ValidationUtil.isValidDate(checkInStr)) errors.append("Valid check-in date is required. ");
        if (!ValidationUtil.isValidDate(checkOutStr)) errors.append("Valid check-out date is required. ");
        if (numGuestsStr == null || numGuestsStr.isEmpty()) errors.append("Number of guests is required. ");

        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            showCreateForm(request, response);
            return;
        }

        Date checkIn = Date.valueOf(checkInStr);
        Date checkOut = Date.valueOf(checkOutStr);

        if (!ValidationUtil.isValidDateRange(checkIn, checkOut)) {
            request.setAttribute("error", "Check-out date must be after check-in date.");
            showCreateForm(request, response);
            return;
        }

        User currentUser = (User) request.getSession().getAttribute("user");

        try {
            Reservation reservation = reservationService.createReservation(
                Integer.parseInt(guestIdStr),
                Integer.parseInt(roomIdStr),
                checkIn,
                checkOut,
                Integer.parseInt(numGuestsStr),
                specialRequests,
                currentUser.getId()
            );

            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + reservation.getId()
                    + "&success=Reservation+created+successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
            showCreateForm(request, response);
        }
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Reservation existing = reservationService.findById(id);

        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/reservations?error=Reservation+not+found");
            return;
        }

        // Update fields
        existing.setGuestId(Integer.parseInt(request.getParameter("guestId")));
        existing.setRoomId(Integer.parseInt(request.getParameter("roomId")));
        existing.setCheckInDate(Date.valueOf(request.getParameter("checkInDate")));
        existing.setCheckOutDate(Date.valueOf(request.getParameter("checkOutDate")));
        existing.setNumGuests(Integer.parseInt(request.getParameter("numGuests")));
        existing.setSpecialRequests(request.getParameter("specialRequests"));

        if (!ValidationUtil.isValidDateRange(existing.getCheckInDate(), existing.getCheckOutDate())) {
            request.setAttribute("error", "Check-out date must be after check-in date.");
            showEditForm(request, response);
            return;
        }

        reservationService.update(existing);
        response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                + "&success=Reservation+updated+successfully");
    }

    private void checkIn(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        try {
            reservationService.checkIn(id, currentUser.getId());
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&success=Guest+checked+in+successfully");
        } catch (IllegalStateException e) {
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        try {
            reservationService.checkOut(id, currentUser.getId());
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&success=Guest+checked+out+successfully");
        } catch (IllegalStateException e) {
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void cancelReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        try {
            reservationService.cancel(id, currentUser.getId());
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&success=Reservation+cancelled+successfully");
        } catch (IllegalStateException e) {
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + id
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
