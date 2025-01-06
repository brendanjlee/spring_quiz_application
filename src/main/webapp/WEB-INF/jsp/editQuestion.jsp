<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Question</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h3>Edit Question</h3>
    <form method="post" action="${pageContext.request.contextPath}/api/admin/saveQuestion">
        <input type="hidden" name="questionId" value="${question.id}"/>
        <div class="mb-3">
            <label for="text" class="form-label">Question Text</label>
            <input type="text" class="form-control" id="text" name="text" value="${question.text}" required/>
        </div>

        <h5>Choices</h5>
        <c:forEach var="choice" items="${choices}">
            <div class="mb-3">
                <label for="choice_${choice.id}" class="form-label">Choice: ${choice.id}</label>
                <input type="text" class="form-control" id="choice_${choice.id}" name="choices[${choice.id}].text"
                       value="${choice.text}" required/>
                <div class="form-check mt-2">
                    <input type="checkbox" class="form-check-input" id="isAnswer_${choice.id}"
                           name="choices[${choice.id}].isAnswer" ${choice.answer ? 'checked' : ''} />
                    <label class="form-check-label" for="isAnswer_${choice.id}">Is Answer?</label>
                </div>
            </div>
        </c:forEach>

        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/questionManagement" class="btn btn-danger">Cancel</a>
            <button type="submit" class="btn btn-success">Save</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
