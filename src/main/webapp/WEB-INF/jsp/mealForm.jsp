<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <hr>
    <c:choose>
        <c:when test="${param.action == 'create'}">
            <spring:message code="mealform.create" var="caption"/>
        </c:when>
        <c:otherwise>
            <spring:message code="mealform.update" var="caption"/>
        </c:otherwise>
    </c:choose>
    <h2><c:out value="${caption}"/> </h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.date"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="mealform.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="mealform.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
