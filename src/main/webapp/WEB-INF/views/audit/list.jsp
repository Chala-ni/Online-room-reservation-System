<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Audit Log" active="audit">

    <div class="dark-card">
        <div class="dark-card-header d-flex justify-content-between align-items-center flex-wrap gap-3">
            <div>
                <div class="card-subtitle">Administration</div>
                <div class="card-title"><i class="bi bi-clipboard-data me-2"></i>Audit Log</div>
            </div>
            <div class="d-flex gap-2">
                <a href="${pageContext.request.contextPath}/audit" 
                   class="btn btn-sm ${empty filterType ? 'btn-primary' : 'btn-outline-secondary'}">All</a>
                <a href="${pageContext.request.contextPath}/audit?type=RESERVATION" 
                   class="btn btn-sm ${filterType == 'RESERVATION' ? 'btn-primary' : 'btn-outline-secondary'}">Reservations</a>
                <a href="${pageContext.request.contextPath}/audit?type=USER" 
                   class="btn btn-sm ${filterType == 'USER' ? 'btn-primary' : 'btn-outline-secondary'}">Users</a>
                <a href="${pageContext.request.contextPath}/audit?type=BILL" 
                   class="btn btn-sm ${filterType == 'BILL' ? 'btn-primary' : 'btn-outline-secondary'}">Bills</a>
            </div>
        </div>

        <div class="dark-card-body">
            <c:choose>
                <c:when test="${not empty logs}">
                    <div class="table-section">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead>
                                    <tr>
                                        <th style="width: 150px;">Timestamp</th>
                                        <th style="width: 120px;">Action</th>
                                        <th style="width: 100px;">Entity</th>
                                        <th>Details</th>
                                        <th style="width: 120px;">Performed By</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="log" items="${logs}">
                                        <tr>
                                            <td class="text-muted small">
                                                <fmt:formatDate value="${log.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </td>
                                            <td>
                                                <span class="badge ${log.action == 'CREATE' ? 'bg-success' : 
                                                                    log.action == 'STATUS_CHANGE' ? 'bg-warning text-dark' : 
                                                                    log.action == 'DELETE' ? 'bg-danger' : 'bg-info'}">
                                                    ${log.action}
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge bg-secondary">${log.entityType}</span>
                                                <small class="text-muted">#${log.entityId}</small>
                                            </td>
                                            <td>${log.details}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty log.performedByName}">
                                                        ${log.performedByName}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">System</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <i class="bi bi-clipboard-x"></i>
                        <p>No audit logs found.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
