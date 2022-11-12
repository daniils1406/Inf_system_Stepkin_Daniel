<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 09.10.2022
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<div class="sony"><h3 style=" color: white; text-align: right; margin: 5px 10px 0px 0px;">SONY</h3></div>
<div class="header">
    <img src="/recources/psn.png"/>
    <button onclick="location.href='/userLogIn'" class="logIn"><h3 style="margin: 0px">Авторизироваться</h3></button>
    <c:choose>
        <c:when test="${sessionScope.CurrentUser!=null}">
            <c:set var="s" value="${sessionScope.CurrentUser}"/>
            <img style="width: 50px; height: 50px" src="${request.contextPath}/images/avatar/${s.id}.png" />
            <c:out value="Добро пожаловать, ${s.nick}"/>
        </c:when>
    </c:choose>
    <button onclick="location.href='/order'" class="basket"><h3 style="margin: 0px">Корзина</h3></button>
</div>
<c:set var="GameList" value="${requestScope.GameList}"/>
<c:set var="PurchasedGames" value="${requestScope.PurchasedGames}"/>
<h2>Все игры</h2>
<div class="gameDiv">
<c:forEach var="i" items="${GameList}">

    <c:set var="purchased" value="${false}"/>
    <c:choose>
        <c:when test="${PurchasedGames!=null}">
            <c:choose>
                <c:when test="${i.show==false}">
                </c:when>
                <c:when test="${i.show==true}">
                    <div class="gameWrapper">
                        <form style="margin: 2px" method="get" action="/game">
                        <input type="hidden" name="ClickGame" value="${i.id}"/>
                        <button style="border: none;background-color: white" type="submit"><img class="gameImage" src="${request.contextPath}/images/game/${i.id}/${i.id}.png"></button>
                        </form>
                        <c:forEach var="j" items="${PurchasedGames}">
                            <c:choose>
                                <c:when test="${i.id==j}">
                                    <c:set var="purchased" value="${true}"/>
                                    <H4 style="margin: 0px">В вашей библиотеке</H4>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${purchased==false}">
                                <form style=" float: left;margin: 2px" method="post">
                                    <input type="hidden" name="SelectedGame" value="${i.id}">
                                    <button class="buttonImage" type="submit"><img style="height: 50px;width: auto" src="/recources/basket.png"></button>
                                </form>
                            </c:when>
                        </c:choose>
                        <p>${i.name}</p>
                        <p>${i.developer} ${i.publisher}</p>
                        <p>${i.cost}$</p>
                    </div>
                </c:when>
            </c:choose>

        </c:when>
        <c:when test="${PurchasedGames==null}">
            <c:choose>
                <c:when test="${i.show==false}">
                </c:when>
                <c:when test="${i.show==true}">
                    <div class="gameWrapper">
                        <form style="margin: 2px" method="get" action="/game">
                        <input type="hidden" name="ClickGame" value="${i.id}"/>
                        <button style="border: none;background-color: white" type="submit"><img class="gameImage" src="${request.contextPath}/images/game/${i.id}/${i.id}.png"></button>
                        </form>
                        <form style=" float: left;margin: 2px" method="post">
                            <input type="hidden" name="SelectedGame" value="${i.id}">
                            <button class="buttonImage" type="submit"><img style="height: 50px;width: auto" src="/recources/basket.png"></button>
                        </form>
                        <p>${i.name}</p>
                        <p>${i.developer} ${i.publisher}</p>
                        <p>${i.cost}$</p>

                    </div>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</c:forEach>
</div>
<div class="filterDiv">
    <form method="get">
        <h3>Порядок</h3>
        Упорядочить по:
        <select name="columnGameForOrder">
            <option value="name">name</option>
            <option value="developer">developer</option>
            <option value="publisher">publisher</option>
            <option value="date">date</option>
            <option value="genre">genre</option>
            <option value="cost">cost</option>
        </select><br>
        В порядке:
        <select name="descOrAsc">
            <option value="asc">asc</option>
            <option value="desc">desc</option>
        </select>
        <br>
        <h3>Фильтр</h3>
        Отобрать по:
        <select name="columnGameForFilter">
            <option value="name">name</option>
            <option value="developer">developer</option>
            <option value="publisher">publisher</option>
            <option value="genre">genre</option>
            <option value="cost">cost</option>
        </select><br>
        Значение:
        <input type="text" name="filterArgument" placeholder="Укажите пусто чтобы отключить фильтр">
        <br>
        <button class="filter" type="submit" name="Filter" value="Filter">Включить фильтр</button>
    </form>
</div>
</body>
</html>
