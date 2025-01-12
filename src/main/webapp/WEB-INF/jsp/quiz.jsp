<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<%@include file="navbar.jsp" %>
<!-- Header Bar with Category Name and Today's Date -->
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center">
        <div><h4>${category.name}</h4></div>  <!-- Display category name -->
        <div><h5>Time Left</h5></div>  <!-- Today's date -->
    </div>
</div>

<!-- Quiz Form -->
<form method="post" action="${pageContext.request.contextPath}/quiz/submit/${category.id}" id="quizForm">
    <div class="container mt-4">

        <!-- Loop through each quiz question -->
        <c:forEach var="question" items="${questions}">
            <c:if test="${question.active}">
                <div class="mb-4">
                    <!-- Display the question text -->
                    <h5>${question.text}</h5>

                    <!-- Loop through each choice for the current question -->
                    <c:forEach var="choice" items="${question.choices}">
                        <div class="form-check">
                            <!-- Display the radio button for each choice -->
                            <input class="form-check-input" type="radio" name="question_${question.id}"
                                   id="choice_${choice.id}" value="${choice.id}" required>
                            <label class="form-check-label" for="choice_${choice.id}">
                                    ${choice.text}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </c:forEach>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary w-100">Submit Quiz</button>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
