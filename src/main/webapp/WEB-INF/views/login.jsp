<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="login-page">
    <div class="container">
        <div class="row justify-content-center align-items-center min-vh-100">
            <div class="col-md-5 col-lg-4">
                <div class="card shadow-lg">
                    <div class="card-body p-5">
                        <!-- Logo & Title -->
                        <div class="text-center mb-4">
                            <i class="bi bi-water display-1 text-primary"></i>
                            <h2 class="mt-2">Ocean View Resort</h2>
                            <p class="text-muted">Room Reservation System</p>
                        </div>

                        <!-- Logout Message -->
                        <c:if test="${param.logout == 'true'}">
                            <div class="alert alert-info">
                                <i class="bi bi-info-circle"></i> You have been logged out successfully.
                            </div>
                        </c:if>

                        <!-- Error Message -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>

                        <!-- Login Form -->
                        <form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
                            <div class="mb-3">
                                <label for="username" class="form-label">
                                    <i class="bi bi-person"></i> Username
                                </label>
                                <input type="text" class="form-control" id="username" name="username" 
                                       value="${username}" required autofocus
                                       placeholder="Enter your username">
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">
                                    <i class="bi bi-lock"></i> Password
                                </label>
                                <input type="password" class="form-control" id="password" name="password" 
                                       required placeholder="Enter your password">
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-box-arrow-in-right"></i> Login
                                </button>
                            </div>
                        </form>

                        <!-- Demo Credentials -->
                        <div class="mt-4 p-3 bg-light rounded">
                            <small class="text-muted">
                                <strong>Demo Credentials:</strong><br>
                                Admin: admin / admin123<br>
                                Receptionist: receptionist1 / recep123
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
