<%@ tag description="Page Layout" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="active" required="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <c:if test="${not empty sessionScope.user}">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
                <i class="bi bi-water"></i> Ocean View Resort
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link ${active == 'dashboard' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/dashboard">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'reservations' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/reservations">
                            <i class="bi bi-calendar-check"></i> Reservations
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'guests' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/guests">
                            <i class="bi bi-people"></i> Guests
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'rooms' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/rooms">
                            <i class="bi bi-door-open"></i> Rooms
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'bills' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/bills">
                            <i class="bi bi-receipt"></i> Bills
                        </a>
                    </li>
                    <c:if test="${sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link ${active == 'reports' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/reports">
                            <i class="bi bi-graph-up"></i> Reports
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'users' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/users">
                            <i class="bi bi-person-gear"></i> Users
                        </a>
                    </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link ${active == 'help' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/help">
                            <i class="bi bi-question-circle"></i> Help
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" 
                           data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${sessionScope.fullName}
                            <span class="badge bg-light text-primary">${sessionScope.userRole}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    </c:if>

    <!-- Main Content -->
    <main class="container-fluid py-4">
        <!-- Flash Messages -->
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> ${param.success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> ${param.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <jsp:doBody/>
    </main>

    <!-- Footer -->
    <footer class="footer mt-auto py-3 bg-light">
        <div class="container text-center">
            <span class="text-muted">&copy; 2025 Ocean View Resort. All rights reserved.</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
