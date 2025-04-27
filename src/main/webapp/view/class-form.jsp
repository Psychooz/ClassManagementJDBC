<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <title>
    <c:choose>
      <c:when test="${empty class.id}">Add New Class</c:when>
      <c:otherwise>Edit Class</c:otherwise>
    </c:choose>
    | ClassManager
  </title>
  <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="static/css/style.css" rel="stylesheet">
</head>
<body>
<%@include file="common/header.jsp"%>

<div class="container mt-4">
  <div class="card shadow-sm">
    <div class="card-header">
      <h4 class="mb-0">
        <c:choose>
          <c:when test="${empty class.id}">Add New Class</c:when>
          <c:otherwise>Edit Class</c:otherwise>
        </c:choose>
      </h4>
    </div>
    <div class="card-body">
      <form action="classes.do" method="post">
        <input type="hidden" name="id" value="${class.id}">

        <c:choose>
          <c:when test="${empty class.id}">
            <input type="hidden" name="action" value="create">
          </c:when>
          <c:otherwise>
            <input type="hidden" name="action" value="update">
          </c:otherwise>
        </c:choose>

        <div class="mb-3">
          <label for="name" class="form-label">Class Name *</label>
          <input type="text" class="form-control" id="name" name="name"
                 value="${class.name}" required>
        </div>

        <div class="mb-3">
          <label for="description" class="form-label">Description</label>
          <textarea class="form-control" id="description" name="description"
                    rows="3">${class.description}</textarea>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="schedule" class="form-label">Schedule</label>
            <input type="text" class="form-control" id="schedule" name="schedule"
                   value="${class.schedule}">
          </div>
          <div class="col-md-6 mb-3">
            <label for="teacher" class="form-label">Teacher</label>
            <input type="text" class="form-control" id="teacher" name="teacher"
                   value="${class.teacher}">
          </div>
        </div>

        <div class="mb-3">
          <label for="room" class="form-label">Room</label>
          <input type="text" class="form-control" id="room" name="room"
                 value="${class.room}">
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <a href="classes.do" class="btn btn-secondary me-md-2">Cancel</a>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<%@include file="common/footer.jsp"%>

<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>