<%--
  Created by IntelliJ IDEA.
  User: Павел
  Date: 07.06.2020
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<style>
    .green {
        background-color: green
    }

    .red {
        background-color: red
    }
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach items="${mealToList}" var="d">
        <tr class="${d.excess == true ? 'red' :'green'} ">
            <td>${d.dateTime}</td>
            <td>${d.description}</td>
            <td>${d.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>