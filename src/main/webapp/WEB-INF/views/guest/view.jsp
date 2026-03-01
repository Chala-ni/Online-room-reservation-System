<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Guest Details" active="guests">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <a href="${pageContext.request.contextPath}/guests" class="btn btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Back to List
            </a>

            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0"><i class="bi bi-person"></i> ${guest.name}</h4>
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/guests/edit?id=${guest.id}" 
                           class="btn btn-warning btn-sm">
                            <i class="bi bi-pencil"></i> Edit
                        </a>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table">
                        <tr>
                            <th style="width:30%">Full Name</th>
                            <td>${guest.name}</td>
                        </tr>
                        <tr>
                            <th>Address</th>
                            <td>${guest.address}</td>
                        </tr>
                        <tr>
                            <th>Contact Number</th>
                            <td>${guest.contactNumber}</td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td>${guest.email}</td>
                        </tr>
                        <tr>
                            <th>NIC/Passport</th>
                            <td>${guest.nicPassport}</td>
                        </tr>
                        <tr>
                            <th>Nationality</th>
                            <td>${guest.nationality}</td>
                        </tr>
                        <tr>
                            <th>Registered</th>
                            <td>${guest.createdAt}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>

</t:layout>
