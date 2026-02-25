package com.oceanview.resort.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanview.resort.model.Guest;
import com.oceanview.resort.service.GuestService;
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
 * REST API Servlet for Guest operations.
 */
@WebServlet("/api/guests/*")
public class GuestApiServlet extends HttpServlet {

    private final GuestService guestService = new GuestService();
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
                // GET /api/guests?search=...
                String search = request.getParameter("search");
                List<Guest> guests;

                if (search != null && !search.isEmpty()) {
                    guests = guestService.searchByName(search);
                } else {
                    guests = guestService.findAll();
                }
                out.print(gson.toJson(guests));

            } else {
                // GET /api/guests/{id}
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    Guest guest = guestService.findById(id);
                    if (guest != null) {
                        out.print(gson.toJson(guest));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(errorJson("Guest not found"));
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(errorJson("Invalid guest ID"));
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
