<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <div class="container text-center py-5">
        <i class="bi bi-search display-1 text-warning"></i>
        <h1 class="display-3 mt-3">404</h1>
        <h2>Page Not Found</h2>
        <p class="text-muted">The page you're looking for doesn't exist or has been moved.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary mt-3">
            <i class="bi bi-house"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>
