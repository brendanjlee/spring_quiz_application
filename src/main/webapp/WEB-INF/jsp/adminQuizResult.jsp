<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h2 class="mb-4">Quiz Management</h2>

    <!-- Filter Section -->
    <div class="d-flex justify-content-between mb-4">
        <form action="${pageContext.request.contextPath}/quizResultManagement" method="GET" class="form-inline">
            <!-- Filter by Category -->
            <div class="form-group mr-3">
                <label for="category" class="mr-2">Filter by Category:</label>
                <select id="category" name="category" class="form-control">
                    <option value="">Select Category</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Filter by User -->
            <div class="form-group mr-3">
                <label for="user" class="mr-2">Filter by User:</label>
                <select id="user" name="user" class="form-control">
                    <option value="">Select User</option>
                    <c:forEach var="user" items="${users}">
                        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Filter</button>
        </form>
    </div>

    <!-- Results Table -->
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Time Taken</th>
            <th>Category</th>
            <th>User</th>
            <th>Number of Questions</th>
            <th>Score</th>
            <th>View Results</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="result" items="${results}">
            <tr>
                <td>${result.startTime}</td>
                <td>${result.categoryName}</td>
                <td>${result.userFullName}</td>
                <td>${result.numQuestions}</td>
                <td>${result.score}</td>
                <td>
                    <a href="/quiz/result/${result.quizResultId}" class="btn btn-primary">View</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
