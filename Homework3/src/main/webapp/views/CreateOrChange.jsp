<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="app.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="app.entities.ColumnAndType" %><%--
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
    <input type="hidden" value="CreateEmployee" name="CreateNewEmployee">
    <button style="float: left" type="submit">Добавить нового сторудника</button>
</form>

    <c:choose>
        <c:when test="${requestScope.CreateNewEmployee!=null && sessionScope.ColumnsOfTable!=null}">
            <form method="post">
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
    <input type="hidden" value="CreatePosition" name="CreateNewPosition">
    <button style="float: left" type="submit">Добавить новое рабочее место</button>
</form>



    <c:choose>
        <c:when test="${requestScope.CreateNewPosition!=null && sessionScope.ColumnsOfTable!=null}">
            <form method="post">
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
    <input type="hidden" value="UpdateEmployee" name="UpdateEmployee">
    <button type="submit">Изменить данные сторудника</button>
</form>
<c:choose>
    <c:when test="${requestScope.UpdateEmployee!=null && sessionScope.ColumnsOfTable!=null}">
        <form method="post">
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
    <input type="hidden" value="UpdatePosition" name="UpdatePosition">
    <button type="submit">Изменить данные рабочего места</button>
</form>
    <c:choose>
        <c:when test="${requestScope.UpdatePosition!=null && sessionScope.ColumnsOfTable!=null}">
            <form method="post">
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
    <input type="hidden" value="CreateLink" name="CreateLink">
    <button style="float: left" type="submit">Назначить сотрудника на должность</button>
</form>
    <c:choose>
        <c:when test="${requestScope.CreateLink!=null && sessionScope.ColumnsOfTable!=null}">
            <form method="post">
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
    <input type="hidden" value="DeleteLink" name="DeleteLink">
    <button type="submit">Снять сотрудника с должности</button>
</form>
    <c:choose>
        <c:when test="${requestScope.DeleteLink!=null && sessionScope.ColumnsOfTable!=null}">
            <form method="post">
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
<c:choose>
    <c:when test="${requestScope.Error!=null}">
        <h2>Пожалуйста, заполните все поля!</h2>
    </c:when>
</c:choose>
</body>
</html>
