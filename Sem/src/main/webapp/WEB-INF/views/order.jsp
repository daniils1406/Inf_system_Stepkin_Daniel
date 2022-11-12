<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 12.10.2022
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles/order.css">
</head>
<body style="background-image: url('/recources/Order.jpg');width: 100%">
<c:set var="ListOfSelectedGames" value="${requestScope.ListOfSelectedGames}"/>
<c:set var="user" value="${sessionScope.CurrentUser}"/>
<c:set var="sum" value="${0}"/>
<c:choose>
    <c:when test="${user!=null && ListOfSelectedGames!=null && not empty ListOfSelectedGames}">
        <form method="post">
            <input type="hidden" value="Delete" name="Delete">
            <button class="delete" type="submit">Удалить из корзины</button>
        <c:forEach var="i" items="${ListOfSelectedGames}">
            <div class="game" >
                <input type="checkbox" name="gamesToDelete" value="${i.id}">
                <img class="imageOrder" src="${request.contextPath}/images/game/${i.id}/${i.id}.png"/>
                <h2 style="vertical-align: middle;display: table-cell;">${i.name}</h2>
                <h4>Разработчик:${i.developer}</h4><br>
                <h4>Издатель:${i.publisher}</h4><br>
                <h4>Жанр:${i.genre}</h4><br>
                <h4>Цена:${i.cost}</h4>
                <c:set var="sum" value="${sum=sum+i.cost}"/>
            </div>
        </c:forEach>
        </form>
        <form method="post">
            <div class="check">
                <div class="sum"><h2 style="margin: 0px; text-align: center">СУММА: ${sum}</h2></div>
                <form method="post">
                    <input type="hidden" name="buy" value="buy">
                    <button class="buy" type="submit">Оформить</button>
                </form>
                <button type="button" class="buy" onclick="location.href='/'">Вернуться к магазину</button>
            </div>
        </form>
    </c:when>
    <c:when test="${(user!=null && ListOfSelectedGames==null) || (user!=null && empty ListOfSelectedGames)}">
        <H2 style="color: white;right: 50%">Вы еще не выбрали ни одной игры</H2>
        <button class="exit" onclick="location.href='/'">Вернуться к магазину</button>
    </c:when>
</c:choose>

</body>
</html>
