package com.oceanview.resort.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.User;
import com.oceanview.resort.model.enums.ReservationStatus;
import com.oceanview.resort.service.ReservationService;
import com.oceanview.resort.util.ErrorMessageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Servlet for Reservation operations.
 */
@WebServlet("/api/reservations/*")
public class ReservationApiServlet extends HttpServlet {

    private final ReservationService reservationService = new ReservationService();
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/reservations?status=...&search=...
                String status = request.getParameter("status");
                String search = request.getParameter("search");
                List<Reservation> reservations;

                if (search != null && !search.isEmpty()) {
                    reservations = reservationService.searchByGuestName(search);
                } else if (status != null && !status.isEmpty()) {
                    reservations = reservationService.findByStatus(ReservationStatus.valueOf(status));
                } else {
                    reservations = reservationService.findAll();
                }
                out.print(gson.toJson(reservations));

            } else {
                // GET /api/reservations/{id}
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    Reservation reservation = reservationService.findById(id);
                    if (reservation != null) {
                        out.print(gson.toJson(reservation));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(errorJson("Reservation not found"));
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(errorJson("Invalid reservation ID"));
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson(ErrorMessageUtil.translateSQLException(e)));
        }
    }

    /**
     * POST /api/reservations - Create a new reservation.
     * Expects JSON body with: guestId, roomId, checkIn, checkOut, numGuests, specialRequests
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(errorJson("Authentication required"));
            return;
        }

        try {
            // Read JSON body
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            // Extract and validate required fields
            if (!json.has("guestId") || !json.has("roomId") || 
                !json.has("checkIn") || !json.has("checkOut")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(errorJson("Missing required fields: guestId, roomId, checkIn, checkOut"));
                return;
            }

            int guestId = json.get("guestId").getAsInt();
            int roomId = json.get("roomId").getAsInt();
            Date checkIn = Date.valueOf(json.get("checkIn").getAsString());
            Date checkOut = Date.valueOf(json.get("checkOut").getAsString());
            int numGuests = json.has("numGuests") ? json.get("numGuests").getAsInt() : 1;
            String specialRequests = json.has("specialRequests") ? 
                    json.get("specialRequests").getAsString() : null;

            // Get current user ID for audit trail
            User currentUser = (User) session.getAttribute("user");
            int createdBy = currentUser.getId();

            // Create reservation using service layer
            Reservation reservation = reservationService.createReservation(
                    guestId, roomId, checkIn, checkOut, numGuests, specialRequests, createdBy);

            // Return created reservation with 201 status
            response.setStatus(HttpServletResponse.SC_CREATED);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Reservation created successfully");
            result.put("reservation", reservation);
            out.print(gson.toJson(result));

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson(e.getMessage()));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson(ErrorMessageUtil.translateSQLException(e)));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Invalid request format: " + e.getMessage()));
        }
    }

    /**
     * PUT /api/reservations/{id}/status - Update reservation status.
     * Expects JSON body with: status (CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED)
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(errorJson("Authentication required"));
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Reservation ID required"));
            return;
        }

        try {
            // Parse reservation ID from path
            String[] pathParts = pathInfo.substring(1).split("/");
            int reservationId = Integer.parseInt(pathParts[0]);

            // Read JSON body
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            if (!json.has("status")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(errorJson("Status field is required"));
                return;
            }

            ReservationStatus newStatus = ReservationStatus.valueOf(
                    json.get("status").getAsString().toUpperCase());

            // Update status using service layer
            boolean updated = reservationService.updateStatus(reservationId, newStatus);

            if (updated) {
                Reservation reservation = reservationService.findById(reservationId);
                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("message", "Reservation status updated to " + newStatus);
                result.put("reservation", reservation);
                out.print(gson.toJson(result));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(errorJson("Failed to update reservation status"));
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Invalid reservation ID"));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson(e.getMessage()));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson(ErrorMessageUtil.translateSQLException(e)));
        }
    }

    private String errorJson(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return gson.toJson(error);
    }
}
