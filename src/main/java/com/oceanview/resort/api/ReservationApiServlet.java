package com.oceanview.resort.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.ReservationStatus;
import com.oceanview.resort.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
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
            out.print(errorJson("Database error: " + e.getMessage()));
        }
    }

    private String errorJson(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return gson.toJson(error);
    }
}
