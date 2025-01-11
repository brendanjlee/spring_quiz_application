<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Results</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-5">
    <h2 class="text-center mb-4">Your Quiz Results for Quiz: ${quizResult.categoryName}</h2>
    <c:if test="${not empty sessionScope.user}">
        <p>${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
        <p>Quiz Start Time: ${quizResult.timeStart}</p>
        <p>Quiz End Time: ${quizResult.timeEnd}</p>
    </c:if>
    <c:choose>
        <c:when test="${quizResult.result >= 3}">
            <p class="text-success">Congratulations! You passed the quiz.</p>
        </c:when>
        <c:otherwise>
            <p class="text-danger">Sorry, you failed the quiz. Better luck next time!</p>
        </c:otherwise>
    </c:choose>
    <div class="list-group">
        <%--     todo   questions should be quizResult.questions--%>
        <c:forEach var="question" items="${quizResult.questions}">
            <div class="list-group-item">
                <h5>${question.text}</h5>
                <!-- Loop through each choice for the question -->
                <c:forEach var="choice" items="${question.choices}">
                    <div class="list-group-item">
                        <p>
                            <c:choose>
                                <c:when test="${choice.answer}">
                                    <strong>${choice.text}</strong>
                                </c:when>
                                <c:otherwise>
                                    ${choice.text}
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${choice.userAnswer}">
                                <c:choose>
                                    <c:when test="${choice.answer}">
                                        <span class="badge badge-success">Correct</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-danger">Incorrect</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </p>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
    <div class="text-center">
        <a href="/" class="btn btn-primary">Take Another Quiz</a>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
