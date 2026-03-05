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
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <c:if test="${not empty sessionScope.user}">
    <!-- Sidebar -->
    <aside class="sidebar" id="sidebar">
        <!-- Brand -->
        <div class="sidebar-brand">
            <a href="${pageContext.request.contextPath}/dashboard">
                <i class="bi bi-water"></i>
                <span class="brand-text">Ocean View</span>
            </a>
        </div>

        <!-- Navigation -->
        <nav class="sidebar-nav">
            <div class="nav-section-title">Main</div>
            <ul class="sidebar-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/dashboard" class="${active == 'dashboard' ? 'active' : ''}">
                        <i class="bi bi-grid-1x2"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/reservations" class="${active == 'reservations' ? 'active' : ''}">
                        <i class="bi bi-calendar-check"></i>
                        <span>Reservations</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/guests" class="${active == 'guests' ? 'active' : ''}">
                        <i class="bi bi-people"></i>
                        <span>Guests</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/rooms" class="${active == 'rooms' ? 'active' : ''}">
                        <i class="bi bi-door-open"></i>
                        <span>Rooms</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/bills" class="${active == 'bills' ? 'active' : ''}">
                        <i class="bi bi-receipt"></i>
                        <span>Bills</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/notifications" class="${active == 'notifications' ? 'active' : ''}">
                        <i class="bi bi-bell"></i>
                        <span>Notifications</span>
                    </a>
                </li>
            </ul>

            <c:if test="${sessionScope.userRole == 'ADMIN'}">
            <div class="nav-section-title">Administration</div>
            <ul class="sidebar-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/reports" class="${active == 'reports' ? 'active' : ''}">
                        <i class="bi bi-graph-up"></i>
                        <span>Reports</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/users" class="${active == 'users' ? 'active' : ''}">
                        <i class="bi bi-person-gear"></i>
                        <span>Users</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/audit" class="${active == 'audit' ? 'active' : ''}">
                        <i class="bi bi-clipboard-data"></i>
                        <span>Audit Log</span>
                    </a>
                </li>
            </ul>
            </c:if>

            <div class="nav-section-title">Support</div>
            <ul class="sidebar-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/help" class="${active == 'help' ? 'active' : ''}">
                        <i class="bi bi-question-circle"></i>
                        <span>Help &amp; Docs</span>
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Sidebar Footer -->
        <div class="sidebar-footer">
            <div class="user-mini">
                <div class="user-avatar">
                    <i class="bi bi-person-fill"></i>
                </div>
                <div class="user-info">
                    <span class="user-name">${sessionScope.fullName}</span>
                    <span class="user-role">${sessionScope.userRole}</span>
                </div>
            </div>
        </div>
    </aside>

    <!-- Top Header Bar -->
    <header class="topbar" id="topbar">
        <div class="topbar-left">
            <button class="sidebar-toggle" id="sidebarToggle" title="Toggle Sidebar">
                <i class="bi bi-list"></i>
            </button>
            <h1 class="page-title">${title}</h1>
        </div>
        <div class="topbar-right">
            <div class="topbar-user dropdown">
                <a class="dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <div class="user-avatar-sm">
                        <i class="bi bi-person-fill"></i>
                    </div>
                    <span class="d-none d-md-inline">${sessionScope.fullName}</span>
                    <span class="badge role-badge">${sessionScope.userRole}</span>
                </a>
                <ul class="dropdown-menu dropdown-menu-end shadow-sm">
                    <li class="dropdown-header">Signed in as <strong>${sessionScope.user.username}</strong></li>
                    <li><hr class="dropdown-divider"></li>
                    <li>
                        <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right me-2"></i> Sign Out
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </header>
    </c:if>

    <!-- Main Content Wrapper -->
    <div class="main-wrapper ${not empty sessionScope.user ? 'has-sidebar' : ''}" id="mainWrapper">
        <main class="main-content">
            <!-- Flash Messages -->
            <c:if test="${not empty param.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i> ${param.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> ${param.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <jsp:doBody/>
        </main>

        <!-- Footer -->
        <footer class="app-footer">
            <span>&copy; 2025 Ocean View Resort. All rights reserved.</span>
        </footer>
    </div>

    <!-- Mobile Sidebar Overlay -->
    <div class="sidebar-overlay" id="sidebarOverlay"></div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
