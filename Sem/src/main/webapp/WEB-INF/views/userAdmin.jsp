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
  <script type="text/javascript" src="/js/userAdmin.js"></script>
</head>
<body>
<div class="sonyDiv"><h3 class="sonyWrite">SONY</h3></div>
<c:set var="player" value="${sessionScope.CurrentUser}"/>
<div class="menu"><img class="avatar" src="${request.contextPath}/images/avatar/${player.id}.png"/><h4> HELLO,${player.nick}!</h4>
  <form method="post" action="/userExit">
    <button class="buy" type="submit">Выйти из аккаунта</button>
  </form>
  <button class="buy" class="buy" onclick="location.href='/'">Вернуться в магазин</button>
  <button id="data1" class="choice">Учетная запись</button>
  <button id="data2" class="choice">Библиотека</button>
  <button id="data3" class="choice">Игры*</button>
  <button id="data4" class="choice">Пользователи*</button>
  <button id="data5" class="choice">История покупок</button>
</div>
<div>

  <div style="display: none" id="userData1">
  <img style="height: 50px; width: 50px" src="${request.contextPath}/images/avatar/${player.id}.png" />
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
    </label><br>
    <label>
      firstName
      <input type="text" name="first_name" value="${player.firstName}"/>
    </label><br>
    <label>
      lastName
      <input type="text" name="last_name" value="${player.lastName}"/>
    </label><br>
    <label>
      mail
      <input type="text" name="mail" value="${player.mail}"/>
    </label><br>
    <label>
      password
      <input type="text" name="password"/>
    </label><br>
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
            <h1>У вас еще нет игр</h1>
          </c:when>
        </c:choose>


      </div>
      <div style="display: none" id="userData3">
        <h2>Игры</h2>
        <form method="post" action="/createGame">
          <label>
            name
            <input type="text" name="name">
          </label>
          <label>
            developer
            <input type="text" name="developer">
          </label>
          <label>
            publisher
            <input type="text" name="publisher">
          </label>
          <label>
            genre
            <input type="text" name="genre">
          </label>
          <label>
            cost
            <input type="number" name="cost">
          </label>
          <label>
            img
            <input type="text" name="img">
          </label>
          <button type="submit">Добавить новую игру</button>
        </form>

  <form method="post" action="/userSendImgToGame">
    <label>
      Добавить обзорную картинку
      <input type="text" name="OverImg">
    </label>
    <button type="submit">Добавить картинку</button>
  </form>
  <c:set var="OverviewImg" value="${requestScope.OverviewImg}"/>
  <c:choose>
    <c:when test="${OverviewImg!=null && not empty OverviewImg}">
      <table>
        <c:forEach var="u" items="${OverviewImg}">
          <tr>
          <td>${u}</td><td><img src="${u}" style="width: 100px; height: auto"></td>
          </tr>
        </c:forEach>
      </table>
    </c:when>
  </c:choose>
  <form method="post" action="/userSendImgToGame">
    <label>
      Добавить указанные картинки к id
      <input type="number" name="OverImgToGame">
    </label>
    <button type="submit">Внести карртинки на страницу игры</button>
  </form>


  <c:set var="ListOfGames" value="${requestScope.GameList}"/>
        <form method="get">
          Упорядочить по:
          <select name="columnGameForOrder">
            <option value="id">id</option>
            <option value="name">name</option>
            <option value="developer">developer</option>
            <option value="publisher">publisher</option>
            <option value="genre">genre</option>
            <option value="cost">cost</option>
            <option value="show">show</option>
          </select>
          В порядке:
          <select name="descOrAsc">
            <option value="asc">asc</option>
            <option value="desc">desc</option>
          </select>

          Отобрать по:
          <select name="columnGameForFilter">
            <option value="name">name</option>
            <option value="developer">developer</option>
            <option value="publisher">publisher</option>
            <option value="date">date</option>
            <option value="genre">genre</option>
            <option value="cost">cost</option>
            <option value="show">show</option>
          </select>
          Значение:
          <input type="text" name="filterArgument" placeholder="Укажите пусто чтобы отключить фильтр">
          <button type="submit" name="Filter" value="Filter">Включить фильтр</button>
        </form>
<form method="post">
  <button type="submit" name="hideGame" value="hideGame">Отобразить</button>
  <button type="submit" name="showGame" value="showGame">Спрятать</button>

  <table>
    <tr><th>id</th> <th>name</th> <th>developer</th> <th>publisher</th> <th>date</th><th>genre</th><th>cost</th><th>show</th></tr>
    <c:forEach var="i" items="${ListOfGames}">
      <tr>
        <td>${i.id}</td>
        <td>
          <form method="get" action="${pageContext.request.contextPath}/game">
        <input type="hidden" name="ClickGame" value="${i.id}">
        <button type="submit" style="padding: 0;border: none;">${i.name}</button>
      </form>
      </td>
        <td>${i.developer}</td> <td>${i.publisher}</td> <td>${i.date}</td><td>${i.genre}</td><td>${i.cost}</td><td>${i.show}</td>
        <td><input type="checkbox" name="games" value="${i.id}"></td>
      </tr>
    </c:forEach>
  </table>
</form>
      </div>
  <div style="display: none" id="userData4">
    <H2>Пользователи</H2>
    <c:set var="ListOfUsers" value="${requestScope.ListOfUsers}"/>
    <form method="get">
      Упорядочить по:
      <select name="columnGameForOrder">
        <option value="id">id</option>
        <option value="nick">nick</option>
        <option value="first_name">first_name</option>
        <option value="last_name">last_name</option>
        <option value="mail">mail</option>
        <option value="password">password</option>
        <option value="admin">admin</option>
      </select>
      В порядке:
      <select name="descOrAsc">
        <option value="asc">asc</option>
        <option value="desc">desc</option>
      </select>

      Отобрать по:
      <select name="columnGameForFilter">
        <option value="id">id</option>
        <option value="nick">nick</option>
        <option value="first_name">first_name</option>
        <option value="last_name">last_name</option>
        <option value="mail">mail</option>
        <option value="admin">admin</option>
      </select>
      Значение:
      <input type="text" name="filterArgument" placeholder="Укажите пусто чтобы отключить фильтр">
      <button type="submit" name="FilterUser" value="FilterUser">Включить фильтр</button>
    </form>


    <form method="post">
      <button type="submit" name="delete" value="delete">Удалить</button>
      <button type="submit" name="signAsAdmin" value="signAsAdmin">Сделать админом</button>
      <button type="submit" name="writeOffAsAdmin" value="writeOffAsAdmin">Убрать админа</button>
      <table>
        <tr><th>id</th> <th>Nick</th> <th>FirstName</th> <th>LastName</th> <th>Mail</th> <th>Admin</th></tr>
        <c:forEach var="i" items="${ListOfUsers}">
          <tr>
            <td>${i.id}</td> <td>${i.nick}</td> <td>${i.firstName}</td> <td>${i.lastName}</td> <td>${i.mail}</td><td>${i.admin}</td>
            <td><input type="checkbox" name="player" value="${i.id}"></td>
          </tr>
        </c:forEach>
      </table>
    </form>
  </div>

  <div style="display: none" id="userData5">
    <h2>Список покупок</h2>
    <c:set var="history" value="${requestScope.history}"/>
    <c:forEach var="h" items="${history}">
      <h3>Заказ номер:${h.key.id} Дата:${h.key.date} Время:${h.key.time} Сумма:${h.key.totalCost}</h3>
      <c:forEach var="j" items="${h.value}">
        <h4>Название:${j.name} Стоимость:${j.cost} <img style="height: 50px; width: auto" src="${request.contextPath}/images/game/${j.id}/${j.id}.png"></h4>
      </c:forEach>
    </c:forEach>
  </div>

</div>

</body>
</html>
