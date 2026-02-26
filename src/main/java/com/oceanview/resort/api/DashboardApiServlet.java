package com.oceanview.resort.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanview.resort.model.dto.DashboardDTO;
import com.oceanview.resort.service.ReportService;
import com.oceanview.resort.service.BillingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Servlet for Dashboard statistics.
 * Used by JavaScript on the dashboard page for dynamic updates.
 */
@WebServlet("/api/dashboard")
public class DashboardApiServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();
    private final Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            DashboardDTO stats = reportService.getDashboardStats();
            out.print(gson.toJson(stats));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unable to load dashboard data: " + e.getMessage());
            out.print(gson.toJson(error));
        }
    }
}
