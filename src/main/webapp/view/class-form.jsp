<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty param.id}">Edit Class</c:when>
            <c:otherwise>Add New Class</c:otherwise>
        </c:choose>
        | ClassManager
    </title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/view/static/css/login.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/6.7.2/css/all.min.css" rel="stylesheet">
</head>
<body>
<%@include file="common/header.jsp"%>

<div class="container mt-4">
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="card shadow-sm">
        <div class="card-header">
            <h4 class="mb-0">
                <i class="fas fa-chalkboard me-2"></i>
                <c:choose>
                    <c:when test="${not empty param.id}">Edit Class</c:when>
                    <c:otherwise>Add New Class</c:otherwise>
                </c:choose>
            </h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/classes.do" method="post">
                <input type="hidden" name="action" value="${not empty param.id ? 'update' : 'create'}">

                <c:if test="${not empty classData}">
                    <input type="hidden" name="id" value="${classData.id}">
                </c:if>

                <div class="mb-3">
                    <label for="name" class="form-label">Class Name *</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${not empty classData.name ? classData.name : ''}" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description"
                              rows="3">${not empty classData.description ? classData.description : ''}</textarea>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="schedule" class="form-label">Schedule</label>
                        <input type="text" class="form-control" id="schedule" name="schedule"
                               value="${not empty classData.schedule ? classData.schedule : ''}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="teacher" class="form-label">Teacher</label>
                        <input type="text" class="form-control" id="teacher" name="teacher"
                               value="${not empty classData.teacher ? classData.teacher : ''}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="room" class="form-label">Room</label>
                    <input type="text" class="form-control" id="room" name="room"
                           value="${not empty classData.room ? classData.room : ''}">
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <a href="${pageContext.request.contextPath}/classes.do" class="btn btn-secondary me-md-2">
                        <i class="fas fa-times me-1"></i> Cancel
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-1"></i>
                        <c:choose>
                            <c:when test="${not empty param.id}">Update Class</c:when>
                            <c:otherwise>Create Class</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp"%>

<script src="${pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/font-awesome/6.7.2/js/all.min.js"></script>

<script>
    $(document).ready(function() {
        $('form').submit(function() {
            if ($('#name').val().trim() === '') {
                alert('Class name is required');
                return false;
            }
            return true;
        });
    });
</script>
</body>
</html>