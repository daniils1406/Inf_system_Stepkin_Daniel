
<%@ taglib prefix="c" uri="jakarta.tags.core" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 01.10.2022
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    div{
        float: left;
        width: 300px;
        height: 300px;
    }
</style>
<div>
<form method="get">
    <input type="hidden" value="CreateEmployee" name="button">
    <button style="float: left" type="submit">Добавить нового сторудника</button>
</form>

    <c:choose>
        <c:when test="${requestScope.button=='CreateEmployee'}">
            <form method="post" action="/newEmployee">
                <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
                <c:forEach var="i" items="${y}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index!=0}">
                            <br>
                            <label>
                                    ${i}
                                <input type="text" name="value${loop.index}">
                            </label><br>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <label>
                    Введите id желаемых позииций
                    <input type="text" name="idOfPosition">
                </label><br>
                <button type="submit">Внести нового сотрудника</button>
            </form>
        </c:when>
    </c:choose>
</div>

<div>
<form method="get">
    <input type="hidden" value="CreatePosition" name="button">
    <button style="float: left" type="submit">Добавить новое рабочее место</button>
</form>



    <c:choose>
        <c:when test="${requestScope.button=='CreatePosition'}">
            <form method="post" action="/newPosition">
                <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
                <c:forEach var="i" items="${y}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index!=0}">
                            <br>
                            <label>
                                    ${i}
                                <input type="text" name="value1${loop.index}">
                            </label><br>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <label>
                    Введите id желаемых позииций
                    <input type="text" name="idOfEmployee">
                </label><br>
                <button type="submit">Внести новую позицию</button>
            </form>
        </c:when>
    </c:choose>
</div>

<div>
<form method="get">
    <input type="hidden" value="UpdateEmployee" name="button">
    <button type="submit">Изменить данные сторудника</button>
</form>
<c:choose>
    <c:when test="${requestScope.button=='UpdateEmployee'}">
        <form method="post" action="/changeEmployee">
            <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
            <c:forEach var="i" items="${y}" varStatus="loop">
                <label>
                    ${i}
                    <input type="text" name="valueForUpdate${loop.index}">
                </label><br>
            </c:forEach>
            <button style="float: left" type="submit">Внести нового сотрудника</button>
        </form>
    </c:when>
</c:choose>
</div>


<div>
<form method="get">
    <input type="hidden" value="UpdatePosition" name="button">
    <button type="submit">Изменить данные рабочего места</button>
</form>
    <c:choose>
        <c:when test="${requestScope.button=='UpdatePosition'}">
            <form method="post" action="/changePosition">
                <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
                <c:forEach var="i" items="${y}" varStatus="loop">
                    <label>
                        ${i}
                        <input type="text" name="valueForUpdate1${loop.index}">
                    </label><br>
                </c:forEach>
                <button style="float: left" type="submit">Внести новое рабочее место</button>
            </form>
        </c:when>
    </c:choose>


</div>

<div>
<form method="get">
    <input type="hidden" value="CreateLink" name="button">
    <button style="float: left" type="submit">Назначить сотрудника на должность</button>
</form>
    <c:choose>
        <c:when test="${requestScope.button=='CreateLink'}">
            <form method="post" action="/newLink">
                <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
                <c:forEach var="i" items="${y}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index!=0}">
                            <label>
                                ${i}
                                <input type="text" name="valueForCreateLink${loop.index}">
                            </label><br>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <button style="float: left" type="submit">Назначить сотрудника</button>
            </form>
        </c:when>
    </c:choose>


</div>

<div>
<form method="get">
    <input type="hidden" value="DeleteLink" name="button">
    <button type="submit">Снять сотрудника с должности</button>
</form>
    <c:choose>
        <c:when test="${requestScope.button=='DeleteLink'}">
            <form method="post" action="/deleteLink">
                <c:set var="y" value="${sessionScope.ColumnsOfTable}"/>
                <c:forEach var="i" items="${y}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index!=0}">
                            <label>
                                    ${i}
                                <input type="text" name="valueForDeleteLink${loop.index}">
                            </label><br>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <button style="float: left" type="submit">Снять сотрудника</button>
            </form>
        </c:when>
    </c:choose>

</div>


<button onclick="location.href='..'">Вернутся</button>
<c:set var="y" value="${sessionScope.Error}"/>
<c:choose>
    <c:when test="${y==false}">
        <h2>Пожалуйста, заполните все поля!</h2>
    </c:when>
</c:choose>
</body>
</html>
