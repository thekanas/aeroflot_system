
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список рейсов</title>
    <%@ include file="parts/style.jsp" %>
</head>
<body>
    <%@ include file="parts/header.jsp" %>
    <div class="container mt-5">

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Номер рейса</th>
                <th scope="col">Аэропорт отбытия</th>
                <th scope="col">Аэропорт назначения</th>
                <th scope="col">Дата и время вылента</th>
                <th scope="col">Дата и время прилета</th>
                <th scope="col">Модель самолета</th>
                <th scope="col">Лётная команда</th>
            </tr>
            </thead>
            <tbody>
        <c:forEach var="flight" items="${requestScope.flights}">
            <tr>
                <td>${flight.flightNumber}</td>
                <td>${flight.airportDeparture.name}</td>
                <td>${flight.airportArrival.name}</td>
                <td>${flight.timeDeparture}</td>
                <td>${flight.timeArrival}</td>
                <td>${flight.airplane.brand.name} ${flight.airplane.model}</td>
                <td>
            <c:forEach var="crew" items="${flight.aircrew}">
                ${crew.fullName} ${crew.position}, <br>
            </c:forEach>

                </td>
            </tr>
        </c:forEach>
            </tbody>
        </table>


    <%@ include file="parts/footer.jsp" %>

    </div>
    <%@ include file="parts/script.jsp" %>
</body>
</html>
