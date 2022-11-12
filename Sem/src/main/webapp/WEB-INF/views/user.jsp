<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 11.10.2022
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/styles/user.css">
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/user.js"></script>
    <link >
</head>
<body>
<div class="sonyDiv"><h3 class="sonyWrite">SONY</h3></div>
<c:set var="player" value="${sessionScope.CurrentUser}"/>
<div class="menu"><img class="avatar" src="${request.contextPath}/images/avatar/${player.id}.png"/><h4> HELLO,${player.nick}!</h4>
    <form method="post" action="/userExit">
        <button class="buy" type="submit">Выйти из аккаунта</button>
    </form>
    <button class="buy" onclick="location.href='/'">Вернуться в магазин</button>
    <button id="data1" class="choice">Учетная запись</button>
    <button id="data2" class="choice">Библиотека</button>
    <button id="data3" class="choice">История покупок</button>
</div>
<div>



<div style="display: none" id="userData1">
    <img style="width: 50px; height: 50px" src="${request.contextPath}/images/avatar/${player.id}.png" />

    <form method="post" action="/userChange">
        <label>
            avatar
            <input type="text" name="changeImg"/>
        </label>
        <input type="hidden" name="id" value="${player.id}"/>
        <button type="submit">Установить</button>
    </form>

            <form method="post" action="/userChange">
                    <input type="hidden" name="id" value="${player.id}"/>
                <label>
                    nick
                    <input type="text" name="nick" value="${player.nick}"/>
                </label>
                <label>
                    firstName
                    <input type="text" name="first_name" value="${player.firstName}"/>
                </label>
                <label>
                    lastName
                    <input type="text" name="last_name" value="${player.lastName}"/>
                </label>
                <label>
                    mail
                    <input type="text" name="mail" value="${player.mail}"/>
                </label>
                <label>
                    password
                    <input type="text" name="password"/>
                </label>
                <input type="hidden" name="admin" value="${player.admin}">
                <input type="hidden" name="salt" value="${player.salt}">
        <button type="submit" name="changeData" value="changeData">Изменить данные</button>
    </form>
    <c:set var="Error" value="${sessionScope.Warning}"/>
    <c:choose>
        <c:when test="${Error!=null}">
            <h3>${Error}</h3>
        </c:when>
    </c:choose>



</div>
    <c:set var="ListOfAvailableGames" value="${requestScope.AvailableGames}"/>
    <div style="display: none" id="userData2">
        <h3>Ваша библиотека</h3>
        <c:choose>
            <c:when test="${not empty ListOfAvailableGames}">
                <c:forEach var="i" items="${ListOfAvailableGames}">
                    <div class="gameWrapper">
                        <form style="margin: 2px" method="get" action="/game">
                            <input type="hidden" name="ClickGame" value="${i.id}"/>
                            <button class="imageButton" type="submit"><img class="image1" src="${request.contextPath}/images/game/${i.id}/${i.id}.png"></button>
                        </form>
                        <c:out value="${i.name}"/>
                    </div>
                </c:forEach>
            </c:when>
            <c:when test="${empty ListOfAvailableGames}">
                <h1>Ваша библиотека пуста</h1>
            </c:when>
        </c:choose>


    </div>
    <div style="display: none" id="userData3">
        <h2>Список покупок</h2>
        <c:set var="history" value="${requestScope.history}"/>
        <c:forEach var="h" items="${history}">
            <h3>Заказ номер:${h.key.id} Дата:${h.key.date} Время:${h.key.time} Сумма:${h.key.totalCost}</h3>
            <c:forEach var="j" items="${h.value}">
                <h4>Название:${j.name} Стоимость:${j.cost} <img style="height: 50px; width: auto" src="${request.contextPath}/images/game/${j.id}/${j.id}.png"></h4>
            </c:forEach>
        </c:forEach>
    </div>

</body>
</html>
