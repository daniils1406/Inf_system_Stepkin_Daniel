<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="app.support.Room" %>
<%@ page import="app.servlets.Chat" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 12.09.2022
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>

    .button8 {
        display: inline-block;
        color: #00222B;
        font-weight: 700;
        text-decoration: none;
        user-select: none;
        padding: .2em 2em;
        outline: none;
        border: 2px solid;
        border-radius: 1px;
        transition: 0.2s;
    }
    .button8:hover { background: rgba(255,255,255,.2); }
    .button8:active { background: #00222B; }

    .container {
        width: 500px;
        height: 500px;
        background-color: transparent ;
        /*box-shadow: 2px 1px 1px 1px rgba(0,0,0,0.2);*/

    }
    img {
        border-radius: 50%;
        height: 50px;
        width: 50px;
        float: left;
    }
    .time {
        float: right;
    }
    .you {
        background-color: #cccccc;
        border-radius: 5px;
        padding: 10px;
        margin: 10px 0;

    }
    .other {
        background-color: #eeeeee;
        border-color: #dddddd;
        border-radius: 5px;
        padding: 10px;
        margin: 10px 0;

    }
    .you:after, .other:after{
        content: "";
        clear: both;
        display: table;
    }
    h2 {
        text-align: center;
        color: #eeeeee;
    }
    h4{
        color: #eeeeee;
    }
    label{
        color: #eeeeee;
    }

    body{
        position: relative;
        background: #00222B;
    }
</style>
<h2 >Комната <%=request.getAttribute("nameOfRoom")%></h2>
<h4>Сейчас в комнате:<%= request.getAttribute("CurrentUsers")%></h4>
<button class="button8" onclick="location.href='/chat'">Обновить</button>
<button class="button8" onclick="location.href='/'">Вернуться в меню</button>
<form method="get">
    <label>
        Ваше сообщение:
        <input type="text" name="MessegeWindow">
    </label>
    <button class="button8" type="submit">Отправить </button>
    <h4>Session history:</h4>
    <ul>


    </ul>

</form>
<div class="container">
    <%for(Room s: Chat.getChats()){%>
    <%if(s.getIdOfRoom().equals(request.getAttribute("nameOfRoom"))){%>
    <%List<String> entries =s.getMeseges();%>
    <%for(String z: entries){ %>
    <div class="you"><%=z%></div>
    <% }%>
    <%}%>
    <%}%>
</div>




</body>
</html>
