<nav class="navbar navbar-expand-lg navbar-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">ClassManager</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${param.active eq 'classes' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/classes.do">Classes</a>
                </li>
            </ul>
            <c:if test="${not empty username}">
                <span class="navbar-text me-3">Welcome, ${username}</span>
                <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/logout.do">Logout</a>
            </c:if>
        </div>
    </div>
</nav>