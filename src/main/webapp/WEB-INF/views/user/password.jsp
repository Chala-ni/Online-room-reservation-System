<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reset Password" active="users">

    <div class="row justify-content-center">
        <div class="col-lg-5">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0"><i class="bi bi-key"></i> Reset Password</h4>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/users/password">
                        <input type="hidden" name="userId" value="${param.userId != null ? param.userId : userId}">

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" 
                                   required minlength="6">
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

</t:layout>
