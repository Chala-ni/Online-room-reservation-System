<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Add User" active="users">

    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0"><i class="bi bi-person-plus"></i> Add New User</h4>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/users/new">
                        
                        <div class="mb-3">
                            <label for="username" class="form-label">Username <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   required minlength="3" maxlength="50" pattern="[a-zA-Z0-9_]+"
                                   placeholder="e.g., johndoe">
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   required minlength="6">
                        </div>

                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full Name <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="fullName" name="fullName" 
                                   required maxlength="100">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>

                        <div class="mb-3">
                            <label for="role" class="form-label">Role <span class="text-danger">*</span></label>
                            <select class="form-select" id="role" name="role" required>
                                <option value="">-- Select Role --</option>
                                <c:forEach var="r" items="${roles}">
                                    <option value="${r}">${r}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> Create User
                            </button>
                            <a href="${pageContext.request.contextPath}/users" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</t:layout>
