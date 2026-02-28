<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Revenue Report" active="reports">

    <h2 class="mb-4"><i class="bi bi-currency-dollar"></i> Revenue Report</h2>

    <!-- Month Selector -->
    <div class="card mb-4">
        <div class="card-body">
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
    </div>

    <!-- Revenue Cards -->
    <div class="row g-4">
        <div class="col-md-6">
            <div class="card border-success">
                <div class="card-body text-center">
                    <h6 class="text-muted">Monthly Revenue</h6>
                    <h2 class="text-success">
                        $<fmt:formatNumber value="${monthlyRevenue}" maxFractionDigits="2"/>
                    </h2>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card border-primary">
                <div class="card-body text-center">
                    <h6 class="text-muted">Total Revenue (All Time)</h6>
                    <h2 class="text-primary">
                        $<fmt:formatNumber value="${totalRevenue}" maxFractionDigits="2"/>
                    </h2>
                </div>
            </div>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary mt-4">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
