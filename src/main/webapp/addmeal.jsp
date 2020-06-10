<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="addmeal" class="ru.javawebinar.topjava.web.MealServlet"/>

<html lang="ru">
<head>
    <title>Add new meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>

<form method="POST" action="meals" accept-charset="UTF-8">
    <input hidden="true" name='Id' value=${meal.id}>
    Описание: <input type="text" name="description" value=${meal.description}>
    Калории: <input type="number" name="calories" value=${meal.calories}>
    Дата: <input type="datetime-local" name="datetime" value=${meal.datetime}>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>