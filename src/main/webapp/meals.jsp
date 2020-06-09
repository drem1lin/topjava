<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="addmeal" class="ru.javawebinar.topjava.web.MealServlet"/>

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
<table border=1>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Обновить</th>
        <th>Удалить</th>
    </tr>
    <c:forEach items="${mealToList}" var="meal">
        <tr class="${meal.excess == true ? 'red' :'green'} ">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Обновить</a></td>
            <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    <p><a href="meals?action=edit">Add Meal</a></p>
</table>
</body>
</html>