<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Ocean View Resort</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="login-page">
    <!-- Left Panel - Branding -->
    <div class="login-left">
        <div class="login-branding">
            <i class="bi bi-water logo-icon"></i>
            <h1>Ocean View Resort</h1>
            <p>Premium room reservation and hotel management system</p>
        </div>
        <div class="login-features">
            <div class="feature">
                <i class="bi bi-calendar-check"></i>
                <span>Streamlined reservation management</span>
            </div>
            <div class="feature">
                <i class="bi bi-people"></i>
                <span>Guest profiles and history tracking</span>
            </div>
            <div class="feature">
                <i class="bi bi-graph-up"></i>
                <span>Real-time occupancy and revenue reports</span>
            </div>
            <div class="feature">
                <i class="bi bi-shield-check"></i>
                <span>Role-based access control</span>
            </div>
        </div>
    </div>

    <!-- Right Panel - Login Form -->
    <div class="login-right">
        <div class="login-form-wrapper">
            <h2>Welcome back</h2>
            <p class="login-subtitle">Sign in to your account to continue</p>

            <!-- Logout Message -->
            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-info">
                    <i class="bi bi-info-circle me-1"></i> You have been logged out successfully.
                </div>
            </c:if>

            <!-- Error Message -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle me-1"></i> ${error}
                </div>
            </c:if>

            <!-- Login Form -->
            <form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username" 
                           value="${username}" required autofocus
                           placeholder="Enter your username">
                </div>
                <div class="mb-4">
                    <label for="password" class="form-label">Password</label>
                    <div class="password-field">
                        <input type="password" class="form-control" id="password" name="password" 
                               required placeholder="Enter your password">
                        <button type="button" class="password-toggle" onclick="togglePassword('password', this)">
                            <i class="bi bi-eye"></i>
                        </button>
                    </div>
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-lg">
                        Sign In <i class="bi bi-arrow-right ms-1"></i>
                    </button>
                </div>
            </form>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function togglePassword(inputId, btn) {
            const input = document.getElementById(inputId);
            const icon = btn.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'bi bi-eye-slash';
            } else {
                input.type = 'password';
                icon.className = 'bi bi-eye';
            }
        }
    </script>
</body>
</html>
