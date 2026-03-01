<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="User Management" active="users">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-person-gear"></i> User Management</h2>
        <a href="${pageContext.request.contextPath}/users/new" class="btn btn-primary">
            <i class="bi bi-person-plus"></i> Add User
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty users}">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>#</th>
                                    <th>Username</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${users}" varStatus="loop">
                                    <tr class="${!u.active ? 'table-secondary' : ''}">
                                        <td>${loop.index + 1}</td>
                                        <td><strong>${u.username}</strong></td>
                                        <td>${u.fullName}</td>
                                        <td>${u.email}</td>
                                        <td>
                                            <span class="badge bg-${u.role == 'ADMIN' ? 'danger' : 'primary'}">
                                                ${u.role}
                                            </span>
                                        </td>
                                        <td>
                                            <span class="badge bg-${u.active ? 'success' : 'secondary'}">
                                                ${u.active ? 'Active' : 'Inactive'}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/users/password?userId=${u.id}" 
                                                   class="btn btn-outline-warning" title="Reset Password">
                                                    <i class="bi bi-key"></i>
                                                </a>
                                                <c:if test="${u.active && u.id != sessionScope.userId}">
                                                    <a href="${pageContext.request.contextPath}/users/deactivate?id=${u.id}" 
                                                       class="btn btn-outline-danger" title="Deactivate"
                                                       onclick="return confirm('Deactivate this user?')">
                                                        <i class="bi bi-person-x"></i>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-muted text-center">No users found.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
