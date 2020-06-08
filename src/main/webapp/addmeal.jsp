<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Add new meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>

<form method="POST" action='meals' name="frmAddUser" accept-charset="UTF-8">
    ID: <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}" />"/>
    Описание: <input type="text" name="description" value="<c:out value="${meal.description}" />"/>
    Калории: <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/>
    Дата: <input type="datetime-local" name="datetime" value="<c:out value="${meal.datetime}" />"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>