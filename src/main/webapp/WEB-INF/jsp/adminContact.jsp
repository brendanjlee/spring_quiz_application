<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-4">
    <h2>Contact Us Messages</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Subject</th>
            <th>Email</th>
            <th>Time Submitted</th>
            <th>Message</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contact" items="${contacts}">
            <tr>
                <td>${contact.subject}</td>
                <td>${contact.email}</td>
                    <%--                <td><fmt:formatDate value="${contact.timeSubmitted}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
                <td>${contact.timeSubmitted}</td>
                <td>${contact.message}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
