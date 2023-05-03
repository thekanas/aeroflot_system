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

    <c:if test="${ requestScope.successfullyAdd eq true }">
        <p style="color: green">Сотрудник успешно добавлен</p>
    </c:if>
    <form class="row g-1" action="${pageContext.request.contextPath}/add" method="post">
        <div class="col-3">
            <label for="inputFullName" class="form-label">ФИО</label>
            <input type="text" class="form-control" id="inputFullName" name="inputFullName">
        </div>
        <div class="w-100"></div>
        <div class="col-3">
            <label for="inputPosition" class="form-label">Должность</label>
            <input type="text" class="form-control" id="inputPosition" name="inputPosition">
        </div>
        <div class="w-100"></div>
        <div class="col-3">
            <label for="inputBirthDay" class="form-label">Дата рождения</label>
            <input type="date" class="form-control" id="inputBirthDay" name="inputBirthDay" placeholder="2000-01-01">
        </div>
        <div class="w-100"></div>
        <div class="form-floating col-3">
            <textarea class="form-control" placeholder="Характеристика" id="floatingTextarea" style="height: 100px" name="inputDescription"></textarea>
            <label for="floatingTextarea" >Характеристика</label>
        </div>
        <div class="w-100"></div>
        <div class="col-6">
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
    </form>


    <%@ include file="parts/footer.jsp" %>
</div>
<%@ include file="parts/script.jsp" %>
</body>
</html>
