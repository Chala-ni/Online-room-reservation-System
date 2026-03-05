package com.oceanview.resort.controller;

import com.oceanview.resort.dao.AuditLogDAO;
import com.oceanview.resort.model.AuditLog;
import com.oceanview.resort.util.ErrorMessageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Audit Log Servlet - admin-only view of system audit trail.
 */
@WebServlet("/audit/*")
public class AuditLogServlet extends HttpServlet {

    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Admin only
        String role = (String) request.getSession().getAttribute("userRole");
        if (!"ADMIN".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Admin+access+required");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            String filterType = request.getParameter("type");
            List<AuditLog> logs;

            if (filterType != null && !filterType.isEmpty()) {
                logs = auditLogDAO.findByEntityType(filterType);
                request.setAttribute("filterType", filterType);
            } else {
                logs = auditLogDAO.findAll();
            }

            request.setAttribute("logs", logs);
            request.getRequestDispatcher("/WEB-INF/views/audit/list.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", ErrorMessageUtil.translateSQLException(e));
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
