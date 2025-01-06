<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h1 class="text-center mb-4">Admin Panel</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="d-grid gap-3">
                <a href="${pageContext.request.contextPath}/userManagement" class="btn btn-primary btn-lg">User
                    Management</a>
                <a href="${pageContext.request.contextPath}/quizResultManagement" class="btn btn-primary btn-lg">Quiz
                    Result Management</a>
                <a href="${pageContext.request.contextPath}/questionManagement" class="btn btn-primary btn-lg">Question
                    Management</a>
                <a href="${pageContext.request.contextPath}/contactUsManagement" class="btn btn-primary btn-lg">Contact
                    Us Management</a>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
