<%--
  Created by IntelliJ IDEA.
  User: ziad
  Date: 23/04/2025
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <title>Class Management System</title>
  <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="webjars/font-awesome/6.7.2/css/fontawesome.min.css" rel="stylesheet">
</head>
<body>
<%@include file="common/header.jsp"%>

<div class="container mt-4">
  <div class="jumbotron">
    <h1 class="display-4">Welcome to Class Management System</h1>
    <p class="lead">Manage your classes, schedules, and teachers in one place.</p>
    <hr class="my-4">
    <p>Get started by viewing or managing your classes.</p>
    <a class="btn btn-primary btn-lg" href="classes.do" role="button">View Classes</a>
  </div>
</div>

<%@include file="common/footer.jsp"%>

<script src="webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>