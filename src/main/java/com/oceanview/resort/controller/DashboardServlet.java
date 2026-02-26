package com.oceanview.resort.controller;

import com.oceanview.resort.model.dto.DashboardDTO;
import com.oceanview.resort.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Dashboard Servlet - displays the main dashboard with statistics.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            DashboardDTO stats = reportService.getDashboardStats();
            request.setAttribute("stats", stats);
            request.setAttribute("todayCheckIns", reportService.getTodayCheckIns());
            request.setAttribute("todayCheckOuts", reportService.getTodayCheckOuts());
        } catch (SQLException e) {
            request.setAttribute("error", "Unable to load dashboard data: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
