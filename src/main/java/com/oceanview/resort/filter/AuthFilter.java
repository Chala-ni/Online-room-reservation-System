package com.oceanview.resort.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Front Controller Pattern - Authentication Filter.
 * Intercepts all requests and ensures only authenticated users
 * can access protected resources.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
        "/login", "/css/", "/js/", "/images/", "/favicon.ico"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Allow public paths without authentication
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is authenticated
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Save the requested URL for redirect after login
            String requestedUrl = httpRequest.getRequestURI();
            if (httpRequest.getQueryString() != null) {
                requestedUrl += "?" + httpRequest.getQueryString();
            }
            
            if (session == null) {
                session = httpRequest.getSession(true);
            }
            session.setAttribute("redirectUrl", requestedUrl);
            
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // User is authenticated, continue with the request
        chain.doFilter(request, response);
    }

    /**
     * Check if the requested path is publicly accessible.
     */
    private boolean isPublicPath(String path) {
        if (path.equals("/") || path.isEmpty()) {
            return true;
        }
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}
