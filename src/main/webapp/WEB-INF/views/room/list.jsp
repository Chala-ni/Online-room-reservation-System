<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Rooms" active="rooms">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-door-open"></i> Room Management</h2>
        <c:if test="${sessionScope.userRole == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/rooms/new" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Add Room
            </a>
        </c:if>
    </div>

    <!-- Filters -->
    <div class="card mb-4">
        <div class="card-body">
            <div class="row g-3">
                <div class="col-md-6">
                    <strong>Filter by Type:</strong>
                    <div class="d-flex gap-2 mt-1">
                        <a href="${pageContext.request.contextPath}/rooms" 
                           class="btn btn-sm btn-outline-secondary ${empty typeFilter && empty statusFilter ? 'active' : ''}">All</a>
                        <c:forEach var="type" items="${roomTypes}">
                            <a href="${pageContext.request.contextPath}/rooms?type=${type}" 
                               class="btn btn-sm btn-outline-primary ${typeFilter == type.name() ? 'active' : ''}">${type}</a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-6">
                    <strong>Filter by Status:</strong>
                    <div class="d-flex gap-2 mt-1">
                        <c:forEach var="status" items="${roomStatuses}">
                            <a href="${pageContext.request.contextPath}/rooms?status=${status}" 
                               class="btn btn-sm btn-outline-${status == 'AVAILABLE' ? 'success' : 
                                   status == 'OCCUPIED' ? 'warning' : 'danger'} ${statusFilter == status.name() ? 'active' : ''}">${status}</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Room Cards -->
    <c:choose>
        <c:when test="${not empty rooms}">
            <div class="row g-4">
                <c:forEach var="room" items="${rooms}">
                    <div class="col-xl-3 col-md-4 col-sm-6">
                        <div class="card h-100 room-card border-${room.status == 'AVAILABLE' ? 'success' : 
                            room.status == 'OCCUPIED' ? 'warning' : 'danger'}">
                            <div class="card-header text-center">
                                <h5 class="mb-0">Room ${room.roomNumber}</h5>
                                <span class="badge bg-${room.status == 'AVAILABLE' ? 'success' : 
                                    room.status == 'OCCUPIED' ? 'warning' : 'danger'}">${room.status}</span>
                            </div>
                            <div class="card-body text-center">
                                <i class="bi bi-door-open display-4 text-${room.status == 'AVAILABLE' ? 'success' : 
                                    room.status == 'OCCUPIED' ? 'warning' : 'danger'}"></i>
                                <h6 class="mt-2">${room.roomType}</h6>
                                <p class="text-muted mb-1">${room.description}</p>
                                <p class="mb-1">
                                    <strong>$<fmt:formatNumber value="${room.ratePerNight}" maxFractionDigits="2"/></strong> / night
                                </p>
                                <p class="text-muted">
                                    <i class="bi bi-people"></i> Max ${room.maxOccupancy} guests
                                </p>
                            </div>
                            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                                <div class="card-footer text-center">
                                    <div class="btn-group btn-group-sm">
                                        <a href="${pageContext.request.contextPath}/rooms/edit?id=${room.id}" 
                                           class="btn btn-outline-warning" title="Edit">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <c:if test="${room.status == 'AVAILABLE'}">
                                            <a href="${pageContext.request.contextPath}/rooms/status?id=${room.id}&newStatus=MAINTENANCE" 
                                               class="btn btn-outline-danger" title="Set Maintenance">
                                                <i class="bi bi-tools"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${room.status == 'MAINTENANCE'}">
                                            <a href="${pageContext.request.contextPath}/rooms/status?id=${room.id}&newStatus=AVAILABLE" 
                                               class="btn btn-outline-success" title="Set Available">
                                                <i class="bi bi-check-circle"></i>
                                            </a>
                                        </c:if>
                                        <a href="${pageContext.request.contextPath}/rooms/delete?id=${room.id}" 
                                           class="btn btn-outline-danger" title="Delete"
                                           onclick="return confirm('Delete this room?')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5">
                <i class="bi bi-door-closed display-1 text-muted"></i>
                <p class="text-muted mt-3">No rooms found.</p>
            </div>
        </c:otherwise>
    </c:choose>

</t:layout>
