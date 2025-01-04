<!-- WEB-INF/jsp/includes/navbar.jsp -->
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
<div class="navbar">
    <a href="/" class="nav-link">Home</a>
    <a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a>
    <a href="${pageContext.request.contextPath}/register" class="nav-link">Register</a>
    <a href="${pageContext.request.contextPath}/contact" class="nav-link">Contact Us</a>
</div>
</body>
</html>
