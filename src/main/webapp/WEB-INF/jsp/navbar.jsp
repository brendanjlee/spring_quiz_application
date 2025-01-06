<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Home</a>
        <div class="d-flex ms-auto gap-5">
            <c:if test="${sessionScope.user.admin}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin">Admin Page</a>
            </c:if>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <form action="${pageContext.request.contextPath}/api/auth/logout" method="post">
                        <button type="submit" class="nav-link" style="background: none; border: none; color: inherit;">
                            Logout (${sessionScope.user.firstName})
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                </c:otherwise>
            </c:choose>
            <c:if test="${empty sessionScope.user}">
                <a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a>
            </c:if>
            <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact Us</a>
        </div>

    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

