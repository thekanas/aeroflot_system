<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 09.04.2023
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Список сотрудников</title>
</head>
<body>

    <c:forEach var="person" items="${requestScope.persons}">
        <p>${person.name}, должность   ${person.position}</p>
    </c:forEach>


</body>
</html>
