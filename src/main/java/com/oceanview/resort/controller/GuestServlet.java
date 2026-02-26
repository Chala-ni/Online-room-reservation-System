package com.oceanview.resort.controller;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.service.GuestService;
import com.oceanview.resort.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Guest Servlet - handles all guest CRUD operations.
 */
@WebServlet("/guests/*")
public class GuestServlet extends HttpServlet {

    private final GuestService guestService = new GuestService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listGuests(request, response);
            } else if (pathInfo.equals("/new")) {
                showCreateForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                viewGuest(request, response);
            } else if (pathInfo.equals("/delete")) {
                deleteGuest(request, response);
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
                createGuest(request, response);
            } else if (pathInfo.equals("/edit")) {
                updateGuest(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listGuests(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String search = request.getParameter("search");
        List<Guest> guests;

        if (search != null && !search.trim().isEmpty()) {
            guests = guestService.searchByName(search.trim());
            request.setAttribute("search", search);
        } else {
            guests = guestService.findAll();
        }

        request.setAttribute("guests", guests);
        request.getRequestDispatcher("/WEB-INF/views/guest/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/guest/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Guest guest = guestService.findById(id);

        if (guest == null) {
            response.sendRedirect(request.getContextPath() + "/guests?error=Guest+not+found");
            return;
        }

        request.setAttribute("guest", guest);
        request.getRequestDispatcher("/WEB-INF/views/guest/form.jsp").forward(request, response);
    }

    private void viewGuest(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Guest guest = guestService.findById(id);

        if (guest == null) {
            response.sendRedirect(request.getContextPath() + "/guests?error=Guest+not+found");
            return;
        }

        request.setAttribute("guest", guest);
        request.getRequestDispatcher("/WEB-INF/views/guest/view.jsp").forward(request, response);
    }

    private void createGuest(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Guest guest = extractGuestFromRequest(request);
        String error = validateGuest(guest);

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("guest", guest);
            request.getRequestDispatcher("/WEB-INF/views/guest/form.jsp").forward(request, response);
            return;
        }

        guestService.create(guest);
        response.sendRedirect(request.getContextPath() + "/guests?success=Guest+added+successfully");
    }

    private void updateGuest(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Guest guest = extractGuestFromRequest(request);
        guest.setId(id);

        String error = validateGuest(guest);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("guest", guest);
            request.getRequestDispatcher("/WEB-INF/views/guest/form.jsp").forward(request, response);
            return;
        }

        guestService.update(guest);
        response.sendRedirect(request.getContextPath() + "/guests?success=Guest+updated+successfully");
    }

    private void deleteGuest(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            guestService.delete(id);
            response.sendRedirect(request.getContextPath() + "/guests?success=Guest+deleted+successfully");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/guests?error=Cannot+delete+guest+with+existing+reservations");
        }
    }

    private Guest extractGuestFromRequest(HttpServletRequest request) {
        Guest guest = new Guest();
        guest.setName(request.getParameter("name"));
        guest.setAddress(request.getParameter("address"));
        guest.setContactNumber(request.getParameter("contactNumber"));
        guest.setEmail(request.getParameter("email"));
        guest.setNicPassport(request.getParameter("nicPassport"));
        guest.setNationality(request.getParameter("nationality"));
        return guest;
    }

    private String validateGuest(Guest guest) {
        if (!ValidationUtil.isValidName(guest.getName())) return "Please enter a valid name (2-100 characters, letters and spaces only).";
        if (!ValidationUtil.isValidAddress(guest.getAddress())) return "Please enter a valid address (5-255 characters).";
        if (!ValidationUtil.isValidContactNumber(guest.getContactNumber())) return "Please enter a valid phone number.";
        if (!ValidationUtil.isValidEmail(guest.getEmail())) return "Please enter a valid email address.";
        if (!ValidationUtil.isValidNicOrPassport(guest.getNicPassport())) return "Please enter a valid NIC or Passport number.";
        if (guest.getNationality() == null || guest.getNationality().trim().isEmpty()) return "Nationality is required.";
        return null;
    }
}
