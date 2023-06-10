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
    <title>Cотрудник: ${person.fullName} </title>
    <%@ include file="parts/style.jsp" %>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="container mt-5">
    <p>${person.fullName}</p>
    <p>Должность: ${person.position}</p>
    <p>Дата рождения: ${person.birthDay}</p>
    <p>Контакты: ${person.contact.tel}, ${person.contact.address}</p>
    <p> ${person.description}</p>
    <br>
    <hr>
    <form method="post" action="${pageContext.request.contextPath}/delete">
        <input type="number" hidden name="id" value="${person.id}" />
        <input class="btn btn-primary" type="submit" name="delete" value="Удалить"/>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/update?id=${person.id}">
        <input type="number" hidden name="id" value="${person.id}" />
        <input class="btn btn-primary" type="submit" value="Редактировать"/>
    </form>


    <%@ include file="parts/footer.jsp" %>
</div>
<%@ include file="parts/script.jsp" %>
</body>
</html>
