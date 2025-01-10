<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>PlaceHolder</title>
</head>
<body>
<%@include file="navbar.jsp" %>
<h1>PlaceHolder!!!</h1>
<div class="col-md-4">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="text-center">
                    <h4 class="mb-4">Please Log In to Start a Quiz</h4>
                    <p class="text-muted">You need to be logged in to take a quiz. Please log in first.</p>
                    <a href="/login" class="btn btn-outline-primary mt-3">Login</a>
                </div>
            </c:when>
            <c:otherwise>
                <form method="post" action="api/quiz">
                    <div class="mb-3">
                        <label for="category" class="form-label">Select Quiz Category</label>
                        <select class="form-control" id="category" name="category">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Start Quiz</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
