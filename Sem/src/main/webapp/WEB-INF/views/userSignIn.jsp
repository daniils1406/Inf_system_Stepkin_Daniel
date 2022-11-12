<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 11.10.2022
  Time: 11:18
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
            nick<br>
            <input type="text" name="nick"/>
        </label>
        </div>
        <div class="field">
        <label>
            Имя<br>
            <input type="text" name="first_name"/>
        </label>
        </div>
        <div class="field">
        <label>
            Фамилия<br>
            <input type="text" name="last_name"/>
        </label>
        </div>
        <div class="field">
        <label>
            mail<br>
            <input type="text" name="mail"/>
        </label>
        </div>
        <div class="field">
        <label>
            Пароль<br>
            <input type="text" name="password"/>
        </label>
        </div>
        <div class="field">
        <label>
            аватар<br>
            <input type="text" name="img"/>
        </label>
        </div>
        <button type="submit">Зарегестрироваться</button>
    </form>
    <button style="margin-bottom: 0px; align-content: center" onclick="location.href='/userLogIn'">Вернутся</button>
    <c:set var="Error" value="${sessionScope.Warning}"/>
    <c:choose>
        <c:when test="${Error!=null}">
            <h4 class="wrong">${Error}</h4>
        </c:when>
    </c:choose>
</div>
</div>
</body>
</html>
