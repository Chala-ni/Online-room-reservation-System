<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reset Password" active="users">

    <div class="row justify-content-center">
        <div class="col-lg-5">
            <div class="dark-card">
                <div class="dark-card-header">
                    <div class="card-subtitle">Security</div>
                    <div class="card-title"><i class="bi bi-key me-2"></i>Reset Password</div>
                </div>
                <div class="dark-card-body">
                    <div class="info-section">
                    <form method="post" action="${pageContext.request.contextPath}/users/password">
                        <input type="hidden" name="userId" value="${param.userId != null ? param.userId : userId}">

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password <span class="text-danger">*</span></label>
                            <div class="password-field">
                                <input type="password" class="form-control" id="newPassword" name="newPassword" 
                                       required minlength="8">
                                <button type="button" class="password-toggle" onclick="togglePassword('newPassword', this)">
                                    <i class="bi bi-eye"></i>
                                </button>
                            </div>
                            <small class="form-text text-muted">Min 8 characters with at least 1 uppercase, 1 lowercase, and 1 digit.</small>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-warning">
                                <i class="bi bi-check-circle"></i> Reset Password
                            </button>
                            <a href="${pageContext.request.contextPath}/users" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:layout>
