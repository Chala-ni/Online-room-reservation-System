<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Error" active="">

    <div class="text-center py-5">
        <i class="bi bi-exclamation-triangle display-1 text-danger"></i>
        <h2 class="mt-3">Oops! Something went wrong</h2>
        <p class="text-muted">${not empty error ? error : 'An unexpected error occurred.'}</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary mt-3">
            <i class="bi bi-house"></i> Back to Dashboard
        </a>
    </div>

</t:layout>
