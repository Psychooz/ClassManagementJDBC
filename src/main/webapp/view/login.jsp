<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Login | ClassManager</title>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark">
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card bg-dark text-white border-light" style="width: 24rem; box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.5);">
        <div class="card-body p-4">
            <div class="text-center mb-4">
                <h2 class="card-title text-primary fw-bold">Class Manager Login</h2>
                <h6>Made By : Ziad & Khalil</h6>
                <p>4IIR10</p>
                <hr class="border-light opacity-25 my-3">
            </div>

            <c:if test="${not empty AccountIncorrect}">
                <div class="alert alert-danger alert-dismissible fade show">
                        ${AccountIncorrect}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login.do" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control bg-dark text-white border-secondary"
                           id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control bg-dark text-white border-secondary"
                           id="password" name="password" required>
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary py-2 fw-bold">
                        <i class="bi bi-box-arrow-in-right me-2"></i> Login
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script>
    // Add subtle animation to inputs on focus
    document.querySelectorAll('.form-control').forEach(input => {
        input.addEventListener('focus', () => {
            input.classList.add('shadow');
            input.classList.add('border-primary');
        });
        input.addEventListener('blur', () => {
            input.classList.remove('shadow');
            input.classList.remove('border-primary');
        });
    });
</script>
</body>
</html>