package com.oceanview.resort.controller;

import com.oceanview.resort.model.Bill;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.service.BillingService;
import com.oceanview.resort.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Bill Servlet - handles billing operations.
 */
@WebServlet("/bills/*")
public class BillServlet extends HttpServlet {

    private final BillingService billingService = new BillingService();
    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listBills(request, response);
            } else if (pathInfo.equals("/view")) {
                viewBill(request, response);
            } else if (pathInfo.equals("/generate")) {
                generateBill(request, response);
            } else if (pathInfo.equals("/pay")) {
                markAsPaid(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Bill> bills = billingService.findAll();
        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/WEB-INF/views/bill/list.jsp").forward(request, response);
    }

    private void viewBill(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Bill bill = billingService.findById(id);

        if (bill == null) {
            response.sendRedirect(request.getContextPath() + "/bills?error=Bill+not+found");
            return;
        }

        request.setAttribute("bill", bill);
        request.getRequestDispatcher("/WEB-INF/views/bill/view.jsp").forward(request, response);
    }

    private void generateBill(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        try {
            Reservation reservation = reservationService.findById(reservationId);
            if (reservation == null) {
                response.sendRedirect(request.getContextPath() + "/reservations?error=Reservation+not+found");
                return;
            }

            Bill bill = billingService.generateBill(reservation);
            response.sendRedirect(request.getContextPath() + "/bills/view?id=" + bill.getId()
                    + "&success=Bill+generated+successfully");
        } catch (IllegalStateException e) {
            response.sendRedirect(request.getContextPath() + "/reservations/view?id=" + reservationId
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void markAsPaid(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        billingService.markAsPaid(id);
        response.sendRedirect(request.getContextPath() + "/bills/view?id=" + id
                + "&success=Payment+recorded+successfully");
    }
}
