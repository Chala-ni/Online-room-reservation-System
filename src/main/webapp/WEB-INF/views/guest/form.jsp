<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${empty guest.id || guest.id == 0 ? 'Add Guest' : 'Edit Guest'}" active="guests">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0">
                        <i class="bi bi-${empty guest.id || guest.id == 0 ? 'person-plus' : 'pencil'}"></i>
                        ${empty guest.id || guest.id == 0 ? 'Add New Guest' : 'Edit Guest'}
                    </h4>
                </div>
                <div class="card-body">
                    <form method="post" id="guestForm"
                          action="${pageContext.request.contextPath}/guests/${empty guest.id || guest.id == 0 ? 'new' : 'edit'}">
                        
                        <c:if test="${not empty guest.id && guest.id > 0}">
                            <input type="hidden" name="id" value="${guest.id}">
                        </c:if>

                        <div class="row">
                            <!-- Full Name -->
                            <div class="col-md-6 mb-3">
                                <label for="name" class="form-label">Full Name <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="name" name="name" 
                                       value="${guest.name}" required maxlength="100"
                                       pattern="[A-Za-z\s]{2,100}" title="Letters and spaces only, 2-100 characters">
                            </div>

                            <!-- Nationality -->
                            <div class="col-md-6 mb-3">
                                <label for="nationality" class="form-label">Nationality <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="nationality" name="nationality" 
                                       value="${guest.nationality}" required maxlength="50">
                            </div>
                        </div>

                        <!-- Address -->
                        <div class="mb-3">
                            <label for="address" class="form-label">Address <span class="text-danger">*</span></label>
                            <textarea class="form-control" id="address" name="address" rows="2" 
                                      required minlength="5" maxlength="255">${guest.address}</textarea>
                        </div>

                        <div class="row">
                            <!-- Contact Number -->
                            <div class="col-md-6 mb-3">
                                <label for="contactNumber" class="form-label">Contact Number <span class="text-danger">*</span></label>
                                <input type="tel" class="form-control" id="contactNumber" name="contactNumber" 
                                       value="${guest.contactNumber}" required
                                       placeholder="+44 1234 567890">
                            </div>

                            <!-- Email -->
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${guest.email}" required>
                            </div>
                        </div>

                        <!-- NIC/Passport -->
                        <div class="mb-3">
                            <label for="nicPassport" class="form-label">NIC / Passport Number <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="nicPassport" name="nicPassport" 
                                   value="${guest.nicPassport}" required maxlength="20"
                                   placeholder="e.g., AB1234567">
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-check-circle"></i> 
                                ${empty guest.id || guest.id == 0 ? 'Add Guest' : 'Update Guest'}
                            </button>
                            <a href="${pageContext.request.contextPath}/guests" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Cancel
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</t:layout>
