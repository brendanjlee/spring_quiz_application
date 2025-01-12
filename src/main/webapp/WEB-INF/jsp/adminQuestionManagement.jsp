<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h3 class="text-center mb-4">Manage Questions</h3>
    <div class="d-flex justify-content-end mb-3">
        <a href="${pageContext.request.contextPath}/admin/addQuestion" class="btn btn-primary">Add New Question</a>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Category</th>
            <th>Text</th>
            <th>Status</th>
            <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="question" items="${questions}">
            <tr>
                <td>${question.categoryName}</td>
                <td>${question.text}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/questions/${question.id}/toggleActive"
                          method="post"
                    >
                        <input type="hidden" name="questionId" value="${question.id}"/>
                        <button type="submit" class="btn ${question.active ? 'btn-success' : 'btn-danger'}">
                                ${question.active ? 'Active' : 'Inactive'}
                        </button>
                    </form>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editQuestion?questionId=${question.id}"
                       class="btn btn-primary">
                        Edit
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
