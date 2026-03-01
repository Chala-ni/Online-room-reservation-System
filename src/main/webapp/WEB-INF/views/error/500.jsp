<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <div class="container text-center py-5">
        <i class="bi bi-exclamation-triangle display-1 text-danger"></i>
        <h1 class="display-3 mt-3">500</h1>
        <h2>Internal Server Error</h2>
        <p class="text-muted">Something went wrong on our end. Please try again later.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary mt-3">
            <i class="bi bi-house"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>
