package com.oceanview.resort.controller;

import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.service.ReportService;
import com.oceanview.resort.service.BillingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Report Servlet - generates various reports.
 */
@WebServlet("/reports/*")
public class ReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();
    private final BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin role
        String role = (String) request.getSession().getAttribute("userRole");
        if (!"ADMIN".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Admin+access+required");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                showReportMenu(request, response);
            } else if (pathInfo.equals("/reservations")) {
                reservationReport(request, response);
            } else if (pathInfo.equals("/revenue")) {
                revenueReport(request, response);
            } else if (pathInfo.equals("/occupancy")) {
                occupancyReport(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showReportMenu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/report/index.jsp").forward(request, response);
    }

    private void reservationReport(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String startStr = request.getParameter("startDate");
        String endStr = request.getParameter("endDate");

        if (startStr != null && endStr != null && !startStr.isEmpty() && !endStr.isEmpty()) {
            Date start = Date.valueOf(startStr);
            Date end = Date.valueOf(endStr);
            List<Reservation> reservations = reportService.getReservationsByDateRange(start, end);
            request.setAttribute("reservations", reservations);
            request.setAttribute("startDate", startStr);
            request.setAttribute("endDate", endStr);
        }

        request.getRequestDispatcher("/WEB-INF/views/report/reservations.jsp").forward(request, response);
    }

    private void revenueReport(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        LocalDate now = LocalDate.now();
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");

        int year = yearStr != null && !yearStr.isEmpty() ? Integer.parseInt(yearStr) : now.getYear();
        int month = monthStr != null && !monthStr.isEmpty() ? Integer.parseInt(monthStr) : now.getMonthValue();

        double monthlyRevenue = billingService.getMonthlyRevenue(year, month);
        double totalRevenue = billingService.getTotalRevenue();

        request.setAttribute("monthlyRevenue", monthlyRevenue);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);

        request.getRequestDispatcher("/WEB-INF/views/report/revenue.jsp").forward(request, response);
    }

    private void occupancyReport(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        // Reuse dashboard stats for occupancy data
        request.setAttribute("stats", reportService.getDashboardStats());
        request.getRequestDispatcher("/WEB-INF/views/report/occupancy.jsp").forward(request, response);
    }
}
