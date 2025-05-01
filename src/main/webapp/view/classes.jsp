<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Classes | ClassManager</title>
  <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="static/css/login.css" rel="stylesheet">
  <link href="webjars/font-awesome/6.7.2/css/all.min.css" rel="stylesheet">
</head>
<body>

<%@include file="common/header.jsp"%>

<div class="container mt-4">
  <c:if test="${not empty success}">
    <div class="alert alert-success alert-dismissible fade show">
        ${success}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show">
        ${error}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>

  <!-- Page Header and Add Button -->
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-chalkboard me-2"></i>Class Management</h2>
    <a href="classes.do?action=new" class="btn btn-primary">
      <i class="fas fa-plus me-2"></i>Add New Class
    </a>
  </div>

  <!-- Search Form -->
  <div class="card shadow-sm mb-4">
    <div class="card-header bg-light">
      <h5 class="mb-0"><i class="fas fa-search me-2"></i>Search Classes</h5>
    </div>
    <div class="card-body">
      <form method="get" action="classes.do" class="g-3">
        <input type="hidden" name="action" value="search">
        <div class="row">
          <div class="col-md-4 mb-3">
            <label for="keyword" class="form-label">Keyword</label>
            <input type="text" class="form-control" id="keyword" name="keyword"
                   value="${param.keyword}" placeholder="Name or description">
          </div>
          <div class="col-md-3 mb-3">
            <label for="teacher" class="form-label">Teacher</label>
            <input type="text" class="form-control" id="teacher" name="teacher"
                   value="${param.teacher}" placeholder="Teacher name">
          </div>
          <div class="col-md-3 mb-3">
            <label for="room" class="form-label">Room</label>
            <input type="text" class="form-control" id="room" name="room"
                   value="${param.room}" placeholder="Room number">
          </div>
          <div class="col-md-2 mb-3 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">
              <i class="fas fa-search me-2"></i>Search
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>

  <!-- Classes Table -->
  <div class="card shadow-sm">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-hover table-striped">
          <thead class="table-dark">
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
                <div class="btn-group" role="group">
                  <a href="classes.do?action=edit&id=${cls.id}"
                     class="btn btn-sm btn-warning"
                     data-bs-toggle="tooltip" title="Edit">
                    <i class="fas fa-edit"></i>
                  </a>
                  <a href="classes.do?action=delete&id=${cls.id}"
                     class="btn btn-sm btn-danger"
                     onclick="return confirm('Are you sure you want to delete this class?')"
                     data-bs-toggle="tooltip" title="Delete">
                    <i class="fas fa-trash"></i>
                  </a>
                </div>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty classes}">
            <tr>
              <td colspan="7" class="text-center text-muted py-4">
                <i class="fas fa-info-circle fa-2x mb-3"></i><br>
                No classes found. Create a new class or try different search criteria.
              </td>
            </tr>
          </c:if>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<%@include file="common/footer.jsp"%>

<!-- JavaScript Libraries -->
<script src="webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script src="webjars/font-awesome/6.7.2/js/all.min.js"></script>

<!-- Initialize Bootstrap tooltips -->
<script>
  $(document).ready(function(){
    $('[data-bs-toggle="tooltip"]').tooltip();
  });
</script>
</body>
</html>