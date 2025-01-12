<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h1 class="text-center mb-4">User Management</h1>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Active Status</th>
            <th>Admin Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/toggleActive/${user.id}"
                          method="post"
                          style="display: inline;">
                        <input type="hidden" name="userId" value="${user.id}">
                        <button type="submit" class="btn ${user.active ? 'btn-success' : 'btn-danger'}">
                                ${user.active? 'True' : 'False'}
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/toggleAdmin/${user.id}" method="post"
                          style="display: inline;">
                        <input type="hidden" name="userId" value="${user.id}">
                        <button type="submit" class="btn ${user.admin ? 'btn-success' : 'btn-danger'}">
                                ${user.admin? 'True' : 'False'}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
