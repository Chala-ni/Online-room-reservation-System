<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Guests" active="guests">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-people"></i> Guests</h2>
        <a href="${pageContext.request.contextPath}/guests/new" class="btn btn-success">
            <i class="bi bi-person-plus"></i> Add Guest
        </a>
    </div>

    <!-- Search -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="${pageContext.request.contextPath}/guests" class="d-flex gap-2">
                <input type="text" class="form-control" name="search" placeholder="Search by name..." 
                       value="${search}">
                <button type="submit" class="btn btn-outline-primary">
                    <i class="bi bi-search"></i> Search
                </button>
                <c:if test="${not empty search}">
                    <a href="${pageContext.request.contextPath}/guests" class="btn btn-outline-secondary">Clear</a>
                </c:if>
            </form>
        </div>
    </div>

    <!-- Guests Table -->
    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty guests}">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>#</th>
                                    <th>Name</th>
                                    <th>Contact Number</th>
                                    <th>Email</th>
                                    <th>NIC/Passport</th>
                                    <th>Nationality</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="guest" items="${guests}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><strong>${guest.name}</strong></td>
                                        <td>${guest.contactNumber}</td>
                                        <td>${guest.email}</td>
                                        <td>${guest.nicPassport}</td>
                                        <td>${guest.nationality}</td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/guests/view?id=${guest.id}" 
                                                   class="btn btn-outline-primary" title="View">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/guests/edit?id=${guest.id}" 
                                                   class="btn btn-outline-warning" title="Edit">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/guests/delete?id=${guest.id}" 
                                                   class="btn btn-outline-danger" title="Delete"
                                                   onclick="return confirm('Are you sure you want to delete this guest?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="bi bi-people display-1 text-muted"></i>
                        <p class="text-muted mt-3">No guests found.</p>
                        <a href="${pageContext.request.contextPath}/guests/new" class="btn btn-success">
                            Add New Guest
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
