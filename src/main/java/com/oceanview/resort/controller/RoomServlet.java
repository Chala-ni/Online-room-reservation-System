package com.oceanview.resort.controller;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.model.enums.RoomType;
import com.oceanview.resort.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Room Servlet - handles all room management operations.
 * Admin-only operations for create/edit/delete.
 */
@WebServlet("/rooms/*")
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listRooms(request, response);
            } else if (pathInfo.equals("/new")) {
                showCreateForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                showEditForm(request, response);
            } else if (pathInfo.equals("/delete")) {
                deleteRoom(request, response);
            } else if (pathInfo.equals("/status")) {
                updateRoomStatus(request, response);
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
                createRoom(request, response);
            } else if (pathInfo.equals("/edit")) {
                updateRoom(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String typeFilter = request.getParameter("type");
        String statusFilter = request.getParameter("status");
        List<Room> rooms;

        if (typeFilter != null && !typeFilter.isEmpty()) {
            rooms = roomService.findByType(RoomType.valueOf(typeFilter));
            request.setAttribute("typeFilter", typeFilter);
        } else if (statusFilter != null && !statusFilter.isEmpty()) {
            rooms = roomService.findByStatus(RoomStatus.valueOf(statusFilter));
            request.setAttribute("statusFilter", statusFilter);
        } else {
            rooms = roomService.findAll();
        }

        request.setAttribute("rooms", rooms);
        request.setAttribute("roomTypes", RoomType.values());
        request.setAttribute("roomStatuses", RoomStatus.values());
        request.getRequestDispatcher("/WEB-INF/views/room/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin role
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Admin+access+required");
            return;
        }

        request.setAttribute("roomTypes", RoomType.values());
        request.setAttribute("roomStatuses", RoomStatus.values());
        request.getRequestDispatcher("/WEB-INF/views/room/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Admin+access+required");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Room room = roomService.findById(id);

        if (room == null) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Room+not+found");
            return;
        }

        request.setAttribute("room", room);
        request.setAttribute("roomTypes", RoomType.values());
        request.setAttribute("roomStatuses", RoomStatus.values());
        request.getRequestDispatcher("/WEB-INF/views/room/form.jsp").forward(request, response);
    }

    private void createRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Admin+access+required");
            return;
        }

        String roomNumber = request.getParameter("roomNumber");
        String roomTypeStr = request.getParameter("roomType");

        if (roomNumber == null || roomNumber.trim().isEmpty() || roomTypeStr == null) {
            request.setAttribute("error", "Room number and type are required.");
            showCreateForm(request, response);
            return;
        }

        try {
            Room room = roomService.createRoom(roomNumber.trim(), RoomType.valueOf(roomTypeStr));
            
            // Override rate and description if provided
            String rateStr = request.getParameter("ratePerNight");
            if (rateStr != null && !rateStr.isEmpty()) {
                room.setRatePerNight(Double.parseDouble(rateStr));
            }
            String description = request.getParameter("description");
            if (description != null && !description.isEmpty()) {
                room.setDescription(description);
            }

            roomService.update(room);
            response.sendRedirect(request.getContextPath() + "/rooms?success=Room+created+successfully");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            showCreateForm(request, response);
        }
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Admin+access+required");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Room room = roomService.findById(id);

        if (room == null) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Room+not+found");
            return;
        }

        room.setRoomNumber(request.getParameter("roomNumber"));
        room.setRoomType(RoomType.valueOf(request.getParameter("roomType")));
        room.setRatePerNight(Double.parseDouble(request.getParameter("ratePerNight")));
        room.setMaxOccupancy(Integer.parseInt(request.getParameter("maxOccupancy")));
        room.setDescription(request.getParameter("description"));

        String statusStr = request.getParameter("status");
        if (statusStr != null && !statusStr.isEmpty()) {
            room.setStatus(RoomStatus.valueOf(statusStr));
        }

        roomService.update(room);
        response.sendRedirect(request.getContextPath() + "/rooms?success=Room+updated+successfully");
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Admin+access+required");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            roomService.delete(id);
            response.sendRedirect(request.getContextPath() + "/rooms?success=Room+deleted+successfully");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/rooms?error=Cannot+delete+room+with+existing+reservations");
        }
    }

    private void updateRoomStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String statusStr = request.getParameter("newStatus");

        if (statusStr != null) {
            roomService.updateStatus(id, RoomStatus.valueOf(statusStr));
        }

        response.sendRedirect(request.getContextPath() + "/rooms?success=Room+status+updated");
    }

    private boolean isAdmin(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole");
        return "ADMIN".equals(role);
    }
}
