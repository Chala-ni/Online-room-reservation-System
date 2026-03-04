<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Revenue Report" active="reports">

    <!-- Month Selector -->
    <div class="dark-card mb-4">
        <div class="dark-card-header">
            <div class="card-subtitle">Report</div>
            <div class="card-title"><i class="bi bi-currency-dollar me-2"></i>Revenue Report</div>
        </div>
        <div class="dark-card-body">
            <div class="filter-section">
                <form method="get" action="${pageContext.request.contextPath}/reports/revenue" class="row g-3 align-items-end">
                    <div class="col-md-3">
                        <label for="year" class="form-label">Year</label>
                        <select class="form-select" id="year" name="year">
                            <c:forEach var="y" begin="2024" end="2026">
                                <option value="${y}" ${selectedYear == y ? 'selected' : ''}>${y}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="month" class="form-label">Month</label>
                        <select class="form-select" id="month" name="month">
                            <option value="1" ${selectedMonth == 1 ? 'selected' : ''}>January</option>
                            <option value="2" ${selectedMonth == 2 ? 'selected' : ''}>February</option>
                            <option value="3" ${selectedMonth == 3 ? 'selected' : ''}>March</option>
                            <option value="4" ${selectedMonth == 4 ? 'selected' : ''}>April</option>
                            <option value="5" ${selectedMonth == 5 ? 'selected' : ''}>May</option>
                            <option value="6" ${selectedMonth == 6 ? 'selected' : ''}>June</option>
                            <option value="7" ${selectedMonth == 7 ? 'selected' : ''}>July</option>
                            <option value="8" ${selectedMonth == 8 ? 'selected' : ''}>August</option>
                            <option value="9" ${selectedMonth == 9 ? 'selected' : ''}>September</option>
                            <option value="10" ${selectedMonth == 10 ? 'selected' : ''}>October</option>
                            <option value="11" ${selectedMonth == 11 ? 'selected' : ''}>November</option>
                            <option value="12" ${selectedMonth == 12 ? 'selected' : ''}>December</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-search"></i> View Revenue
                        </button>
                    </div>
                </form>
            </div>

            <!-- Revenue Results -->
            <div class="info-section">
                <div class="row g-4">
                    <div class="col-md-6">
                        <div style="background: #f0fdf4; border-radius: 12px; padding: 24px; text-align: center;">
                            <div style="font-size: 0.75rem; color: #64748b; text-transform: uppercase; letter-spacing: 0.05em;">Monthly Revenue</div>
                            <div style="font-size: 2rem; font-weight: 700; color: #10b981; margin-top: 8px;">
                                $<fmt:formatNumber value="${monthlyRevenue}" maxFractionDigits="2"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="background: #eff6ff; border-radius: 12px; padding: 24px; text-align: center;">
                            <div style="font-size: 0.75rem; color: #64748b; text-transform: uppercase; letter-spacing: 0.05em;">Total Revenue (All Time)</div>
                            <div style="font-size: 2rem; font-weight: 700; color: #3b82f6; margin-top: 8px;">
                                $<fmt:formatNumber value="${totalRevenue}" maxFractionDigits="2"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
