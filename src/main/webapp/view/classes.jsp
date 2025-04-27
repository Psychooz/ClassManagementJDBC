<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <title>Classes | ClassManager</title>
  <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="static/css/style.css" rel="stylesheet">
</head>
<body>
<%@include file="common/header.jsp"%>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Class Management</h2>
    <a href="classes.do?action=new" class="btn btn-primary">
      <i class="fas fa-plus"></i> Add New Class
    </a>
  </div>

  <c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
  </c:if>

  <div class="card shadow-sm">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>ID</th>
            <th>Class Name</th>
            <th>Description</th>
            <th>Schedule</th>
            <th>Teacher</th>
            <th>Room</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${classes}" var="cls">
            <tr>
              <td>${cls.id}</td>
              <td>${cls.name}</td>
              <td>${cls.description}</td>
              <td>${cls.schedule}</td>
              <td>${cls.teacher}</td>
              <td>${cls.room}</td>
              <td>
                <a href="classes.do?action=edit&id=${cls.id}"
                   class="btn btn-sm btn-warning">
                  <i class="fas fa-edit"></i> Edit
                </a>
                <a href="classes.do?action=delete&id=${cls.id}"
                   class="btn btn-sm btn-danger"
                   onclick="return confirm('Are you sure you want to delete this class?')">
                  <i class="fas fa-trash"></i> Delete
                </a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<%@include file="common/footer.jsp"%>

<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
<script src="webjars/font-awesome/6.0.0/js/all.min.js"></script>
</body>
</html>