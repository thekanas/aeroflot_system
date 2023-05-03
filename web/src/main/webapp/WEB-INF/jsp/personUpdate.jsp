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
    <p> ${person.description}</p>
    <br>
    <hr>
    <c:if test="${ requestScope.successfullyUpdate eq true }">
        <p style="color: green">Сотрудник успешно обновлен</p>
    </c:if>
    <form class="row g-1" action="${pageContext.request.contextPath}/update" method="post">
        <div class="col-3">
            <label for="inputFullName" class="form-label">ФИО</label>
            <input type="text" class="form-control" id="inputFullName" name="inputFullName" value="${person.fullName}">
        </div>
        <div class="w-100"></div>
        <div class="col-3">
            <label for="inputPosition" class="form-label">Должность</label>
            <input type="text" class="form-control" id="inputPosition" name="inputPosition" value="${person.position}">
        </div>
        <div class="w-100"></div>
        <div class="col-3">
            <label for="inputBirthDay" class="form-label">Дата рождения</label>
            <input type="text" class="form-control" id="inputBirthDay" name="inputBirthDay" value="${person.birthDay}">
        </div>
        <div class="w-100"></div>
        <div class="form-floating col-3">
            <textarea class="form-control"   id="floatingTextarea" style="height: 100px" name="inputDescription">${person.description}</textarea>
            <label for="floatingTextarea"></label>
        </div>
        <div class="w-100"></div>
        <div class="col-6">
            <input type="number" hidden name="id" value="${person.id}" />
            <button type="submit" class="btn btn-primary">Редактировать</button>
        </div>
    </form>


    <%@ include file="parts/footer.jsp" %>
</div>
<%@ include file="parts/script.jsp" %>
</body>
</html>
