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
    <%@ include file="parts/style.jsp" %>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="container mt-5">
    <div class="row">
        <div class="col-8" style="position:relative; padding-bottom: 50px">

            <div class="col-12 align-self-start">
                <c:forEach var="person" items="${requestScope.persons}">
                    <a href="${pageContext.request.contextPath}/persons?id=${person.id}">
                        <p>${person.fullName}, ${person.position}</p></a>
                </c:forEach>
            </div>

            <div class="col-12" style="position: absolute; bottom: 0px;">
                <nav aria-label="pag">
                    <ul class="pagination">
                        <li class="page-item
            <c:if test="${requestScope.page eq 1}">
                 disabled
            </c:if>
            ">
                            <a class="page-link" href="/persons?page=${page - 1}" aria-label="Предыдущая">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${requestScope.countPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.page eq i}">
                                    <li class="page-item active" aria-current="page"><a class="page-link"
                                                                                        href="#">${i}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${requestScope.page > i and i+2 >= requestScope.page}">
                                        <li class="page-item"><a class="page-link" href="/persons?page=${i}">${i}</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${i <= requestScope.countPages and requestScope.page < i and i-2 <= requestScope.page}">
                                        <li class="page-item"><a class="page-link" href="/persons?page=${i}">${i}</a>
                                        </li>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <li class="page-item
            <c:if test="${requestScope.page eq requestScope.countPages}">
                disabled
            </c:if>
            ">
                            <a class="page-link" href="${pageContext.request.contextPath}/persons?page=${page + 1}"
                               aria-label="Следующая">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>


        </div>
        <div class="col-4">
            <form class="row g-1" action="${pageContext.request.contextPath}/persons" method="post">
                <div class="col">
                    <label for="inputFullName" class="form-label">ФИО</label>
                    <input type="text" class="form-control" id="inputFullName" name="fullName"
                           placeholder="${personFilter.fullName}">
                </div>
                <div class="w-100"></div>
                <div class="col">
                    <select class="form-select" aria-label="pos" name="position">
                        <option selected>Должность</option>
                        <c:forEach var="position" items="${positions}">
                            <option value="${position}">${position}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="w-100"></div>
                <div class="col">
                    <div class="md-form">
                        <label for="birthDayFrom">Даты рождения начиная от даты</label>
                        <input name="birthDayFrom" type="date" id="birthDayFrom" class="form-control datepicker">
                    </div>
                </div>
                <div class="w-100"></div>
                <div class="col">
                    <div class="md-form">
                        <label for="birthDayTO">Даты рождения заканчивая датой</label>
                        <input name="birthDayTO" type="date" id="birthDayTO" class="form-control datepicker">
                    </div>
                </div>
                <div class="w-100"></div>
                <div class="col-12">Количество записей на странице</div>
                <div class="form-check col">
                    <input class="form-check-input" type="radio" name="limit" id="flexRadioDefault1" value="3"
                    <c:if test="${personFilter.limit eq 3}">
                           checked
                    </c:if>>
                    <label class="form-check-label" for="flexRadioDefault1">
                        3
                    </label>
                </div>
                <div class="form-check col">
                    <input class="form-check-input" type="radio" name="limit" id="flexRadio2" value="5"
                    <c:if test="${personFilter.limit eq 5}">
                           checked
                    </c:if>>
                    <label class="form-check-label" for="flexRadio2">
                        5
                    </label>
                </div>
                <div class="form-check col">
                    <input class="form-check-input" type="radio" name="limit" id="flexRadio3" value="10"
                    <c:if test="${personFilter.limit eq 10}">
                           checked
                    </c:if>>
                    <label class="form-check-label" for="flexRadio3">
                        10
                    </label>
                </div>

                <div class="w-100"></div>
                <div class="col-6">
                    <button type="submit" class="btn btn-primary">Применить</button>
                </div>

            </form>
        </div>
    </div>
    <br>


    <br>
    <hr>
    <br>
    <a href="${pageContext.request.contextPath}/add">Добавить нового сотрудника</a>


    <br>
    <br>
    <br>
    <%@ include file="parts/footer.jsp" %>
</div>
<%@ include file="parts/script.jsp" %>

</body>
</html>
