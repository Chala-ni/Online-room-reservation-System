package com.oceanview.resort.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.service.RoomService;
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
 * REST API Servlet for Room operations.
 * Provides JSON endpoints for AJAX calls from JSP pages.
 */
@WebServlet("/api/rooms/*")
public class RoomApiServlet extends HttpServlet {

    private final RoomService roomService = new RoomService();
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
                // GET /api/rooms - list all rooms
                String status = request.getParameter("status");
                List<Room> rooms;
                if (status != null && !status.isEmpty()) {
                    rooms = roomService.findByStatus(RoomStatus.valueOf(status));
                } else {
                    rooms = roomService.findAll();
                }
                out.print(gson.toJson(rooms));

            } else if (pathInfo.equals("/available")) {
                // GET /api/rooms/available?checkIn=...&checkOut=...
                String checkIn = request.getParameter("checkIn");
                String checkOut = request.getParameter("checkOut");
                List<Room> rooms;
                if (checkIn != null && checkOut != null) {
                    rooms = roomService.findAvailableForDates(
                            java.sql.Date.valueOf(checkIn),
                            java.sql.Date.valueOf(checkOut));
                } else {
                    rooms = roomService.findByStatus(RoomStatus.AVAILABLE);
                }
                out.print(gson.toJson(rooms));

            } else {
                // GET /api/rooms/{id}
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    Room room = roomService.findById(id);
                    if (room != null) {
                        out.print(gson.toJson(room));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(errorJson("Room not found"));
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(errorJson("Invalid room ID"));
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
