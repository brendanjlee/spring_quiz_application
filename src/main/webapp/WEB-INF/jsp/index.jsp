<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz It!</title>
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <div class="row">
        <!-- Left side: Recent Quizzes Table -->
        <div class="col-md-8">
            <h3>Recent Quizzes</h3>
            <c:choose>
                <c:when test="${empty quizResults}">
                    <p>No quizzes to show.</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Quiz Category</th>
                            <th>Date Taken</th>
                            <th>Result</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="quizResult" items="${quizResults}">
                            <tr>
                                <td>${quizResult.categoryName}</td>
                                <td>${quizResult.timeStart}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/quiz/result/${user.id}/${quizResult.id}"
                                       class="btn btn-primary btn-sm">View
                                        Result</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <!-- right side: Recent Quizzes Table -->
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
                        <form method="post" action="${pageContext.request.contextPath}/quiz/${user.id}">
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
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
