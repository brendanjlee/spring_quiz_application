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
    <h3>Add New Question</h3>
    <form method="post" action="${pageContext.request.contextPath}/api/admin/saveNewQuestion">
        <div class="mb-3">
            <label for="category" class="form-label">Category</label>
            <select class="form-select" id="category" name="categoryId" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="text" class="form-label">Question Text</label>
            <input type="text" class="form-control" id="text" name="text" required/>
        </div>

        <h5>Choices</h5>
        <div class="mb-3">
            <label for="choice_1" class="form-label">Choice 1</label>
            <input type="text" class="form-control" id="choice_1" name="choices[1].text" required/>
            <div class="form-check mt-2">
                <input type="checkbox" class="form-check-input" id="isAnswer_1" name="choices[1].isAnswer"/>
                <label class="form-check-label" for="isAnswer_1">Is Answer?</label>
            </div>
        </div>
        <div class="mb-3">
            <label for="choice_2" class="form-label">Choice 2</label>
            <input type="text" class="form-control" id="choice_2" name="choices[2].text" required/>
            <div class="form-check mt-2">
                <input type="checkbox" class="form-check-input" id="isAnswer_2" name="choices[2].isAnswer"/>
                <label class="form-check-label" for="isAnswer_2">Is Answer?</label>
            </div>
        </div>
        <div class="mb-3">
            <label for="choice_3" class="form-label">Choice 3</label>
            <input type="text" class="form-control" id="choice_3" name="choices[3].text" required/>
            <div class="form-check mt-2">
                <input type="checkbox" class="form-check-input" id="isAnswer_3" name="choices[3].isAnswer"/>
                <label class="form-check-label" for="isAnswer_3">Is Answer?</label>
            </div>
        </div>
        <div class="mb-3">
            <label for="choice_4" class="form-label">Choice 4</label>
            <input type="text" class="form-control" id="choice_4" name="choices[4].text" required/>
            <div class="form-check mt-2">
                <input type="checkbox" class="form-check-input" id="isAnswer_4" name="choices[4].isAnswer"/>
                <label class="form-check-label" for="isAnswer_4">Is Answer?</label>
            </div>
        </div>

        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/questionManagement" class="btn btn-danger">Cancel</a>
            <button type="submit" class="btn btn-success">Save</button>
        </div>
    </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>