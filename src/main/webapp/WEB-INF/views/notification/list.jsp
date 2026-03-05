<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Notifications" active="notifications">

    <div class="dark-card">
        <div class="dark-card-header d-flex justify-content-between align-items-center flex-wrap gap-3">
            <div>
                <div class="card-subtitle">System</div>
                <div class="card-title">
                    <i class="bi bi-bell me-2"></i>Notifications
                    <c:if test="${unreadCount > 0}">
                        <span class="badge bg-danger ms-2">${unreadCount} unread</span>
                    </c:if>
                </div>
            </div>
            <c:if test="${unreadCount > 0}">
                <a href="${pageContext.request.contextPath}/notifications/mark-all-read" 
                   class="btn btn-outline-primary btn-sm">
                    <i class="bi bi-check-all"></i> Mark All as Read
                </a>
            </c:if>
        </div>

        <div class="dark-card-body">
            <c:choose>
                <c:when test="${not empty notifications}">
                    <div class="list-group list-group-flush">
                        <c:forEach var="n" items="${notifications}">
                            <div class="list-group-item bg-transparent border-secondary d-flex align-items-start gap-3 ${!n.read ? 'border-start border-primary border-3' : ''}">
                                <div class="notification-icon fs-4">
                                    <i class="bi ${n.iconClass}"></i>
                                </div>
                                <div class="flex-grow-1">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h6 class="mb-1 ${!n.read ? 'fw-bold' : ''}">${n.title}</h6>
                                        <small class="text-muted">
                                            <fmt:formatDate value="${n.createdAt}" pattern="MMM dd, HH:mm"/>
                                        </small>
                                    </div>
                                    <p class="mb-1 text-muted small">${n.message}</p>
                                    <c:if test="${!n.read}">
                                        <a href="${pageContext.request.contextPath}/notifications/mark-read?id=${n.id}" 
                                           class="btn btn-sm btn-outline-secondary">
                                            <i class="bi bi-check"></i> Mark as Read
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <i class="bi bi-bell-slash"></i>
                        <p>No notifications yet.</p>
                        <small class="text-muted">Notifications appear when reservations are created, updated, or cancelled.</small>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
