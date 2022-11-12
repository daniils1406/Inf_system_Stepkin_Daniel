<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 09.10.2022
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles/UserSignIn.css">
</head>
<body style="background-image: url('/recources/SignUp.jpg')">
<div class="parent">
<div class="center-div">
    <div class="sonyDiv">
        <h2 class="sony">SONY
        </h2>
    </div>
    <div class="playStation"><h2 class="playStationInscription">PlayStation</h2></div>
    <form method="post">
        <div class="field">
        <label>
            mail<br>
            <input type="text" name="LogInmail"/>
        </label>
        </div>
        <div class="field">
        <label>
            password<br>
            <input type="text" name="LogInpassword"/>
        </label>
        </div>
        <button type="submit">Войти</button>
    </form>
    <button style="margin-bottom: 10px" onclick="location.href='/userSignIn'">Зарегестрироваться</button>
    <button onclick="location.href='/'">Вернуться в меню</button>
    <c:choose>
        <c:when test="${sessionScope.NotFound!=null}">
            <h4 class="wrong">${sessionScope.NotFound}</h4>
        </c:when>
    </c:choose>
</div>
</div>
</body>
</html>
