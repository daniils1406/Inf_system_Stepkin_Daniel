<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 18.10.2022
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <link rel="stylesheet" href="styles/game.css">
</head>
<c:set var="game" value="${requestScope.Game}"/>
<body style="background-image:linear-gradient(
        rgba(0, 0, 0, 0.7),
        rgba(0, 0, 0, 0.7)
        ), url('${request.contextPath}/images/game/${game.id}/${game.id}.png')">
<form method="post" action="/gameChange">
  <label>
    Нвоый фон
    <input type="text" name="newMainImage"/>
  </label>
  <input type="hidden" name="nameOfGame" value="${game.id}">
  <button type="submit">Установить</button>
</form>
<h2 style="color: white">Медиа:</h2>
<c:set var="images" value="${requestScope.images}"/>
<c:choose>
  <c:when test="${images!=null}">
    <c:forEach var="i" items="${images}">
      <form class="redact" method="post">
        <input type="hidden" name="nameOfImage" value="${i}">
        <input type="hidden" name="nameOfGame" value="${game.id}">
        <button type="submit">Удалить</button>
      </form>
      <img style="float: left" src="/images/game/${game.id}/${i}.png" width=130 onMouseover='this.style.width=900' onMouseout='this.style.width=130'>
    </c:forEach>
  </c:when>
</c:choose>
<br>
<div class="inf">
  <button class="buy" onclick="location.href='/'">В меню</button><br>
  <button class="buy" onclick="location.href='/user'">В личный кабинет</button>
  <form method="post" action="/gameChange">
    <input type="hidden" name="id" value="${game.id}">
    <input type="hidden" name="show" value="${game.show}">
    <input type="hidden" name="date" value="${game.date}">
    <label>
      Разработчик:
      <input type="text" name="developer" value="${game.developer}"/>
    </label>
    <label>
      Издатель:
      <input type="text" name="publisher" value="${game.publisher}"/>
    </label>
    <label>
      Жанр:
      <input type="text" name="genre" value="${game.genre}"/>
    </label>
    <input type="hidden" name="cost" value="${game.cost}"/>
    <button type="submit">Изменить данные игры</button>
    <br>
    <label>
      Название:
      <input type="text" name="name" value="${game.name}"/>
    </label>
  </form>
  <c:set var="PurchasedGames" value="${requestScope.PurchasedGames}"/>
  <c:set var="isPurchased" value="${false}"/>
  <c:choose>
    <c:when test="${PurchasedGames!=null}">
      <c:forEach var="i" items="${PurchasedGames}">
        <c:choose>
          <c:when test="${i==game.id}">
            <c:set var="isPurchased" value="${true}"/>
          </c:when>
        </c:choose>
      </c:forEach>
      <c:choose>
        <c:when test="${isPurchased==true}">
          <button class="delete" type="button">В вашей библиотеке</button>
        </c:when>
        <c:when test="${isPurchased==false}">
          <form method="post" action="/">
            <input type="hidden" name="SelectedGame" value="${game.id}">
            <button class="delete" type="submit">Добавить в корзину</button>
          </form>
        </c:when>
      </c:choose>
    </c:when>
    <c:when test="${PurchasedGames==null}">
      <form method="post" action="/">
        <input type="hidden" name="SelectedGame" value="${game.id}">
        <button class="delete" type="submit">Добавить в корзину</button>
      </form>
    </c:when>
  </c:choose>

</div>
</body>
</html>
