<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 10.04.2023
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список самолетов</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <c:forEach var="airplane" items="${requestScope.airplanes}">
        <p>${airplane.make} ${airplane.model}
            с дальностью полета ${airplane.flightRangeKm} км
            и вместимостью пассажиров ${airplane.passengerCapacity}</p>
    </c:forEach>
    <%@ include file="footer.jsp" %>
</body>
</html>
