<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="app.support.PostgresConnectionProvider" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="app.support.Listener" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 24.09.2022
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    .container {
        overflow: auto;
        width: 700px;
        height: 700px;
        background-color: #eeeeee;
        box-shadow: 2px 1px 1px 1px rgba(0,0,0,0.2);
        position: absolute;
        margin: auto;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        border: black;
        border-radius: 10px;
        border: 1px solid;

    }
    img {
        border-radius: 50%;
        height: 50px;
        width: 50px;
        float: left;
    }
    .you {
        background-color: #cccccc;
        border-radius: 5px;
        padding: 10px;
        margin: 10px 0;

    }
    h2 {
        text-align: center;
    }
    .button8 {
        display: inline-block;
        color: black;
        font-weight: 700;
        text-decoration: none;
        user-select: none;
        padding: .5em 2em;
        outline: none;
        border: black;
        border: 1px solid;
        border-radius: 10px;
        transition: 0.2s;
    }
    .button8:hover { background: rgba(255,255,255,.2); }
    .button8:active { background: white; }

    h1{
        font-style: oblique;
        text-align: center;
    }
    .parent {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        overflow: auto;
    }

    label{
        border-radius: 40px;
        font-weight: bold;
        text-align: center;
    }
    input{
        align-content: center;
        text-align: center;
    }
    form{
        text-align: center;
    }

    body{
        background-image: url("image/1.png");
    }
    .block_2{
        position: relative;
    }
    .some-block{
        position: absolute;
        width: 100%;
        bottom: 0;
    }
</style>
<div class="parent">
    <div class="container">
        <h1 style="text-align: center">Сообшения</h1>
        <%List<String> p= (List<String>) request.getAttribute("MessagesFromDB");%>
        <%if(!p.isEmpty()){%>
        <%for(int r=0;r<p.size();r++){%>
        <div class="you"><%=p.get(r)%></div>
        <%}%>
        <%}%>
        <%List<String> u=Listener.getAllSessionMessages().get(request.getAttribute("nameOfRoom"));%>
        <%for(String f: u){%>
        <%if(f!=null){%>
        <div class="you"><%=f%></div>
        <%}%>
        <%}%>
    </div>
    <form method="post" action="/select">
        <input type="hidden" value="0" name="beforeLeave">
        <input type="hidden" value="<%=request.getAttribute("nameOfRoom")%>" name="id">
        <button class="button8" type="submit">Нажмите, чтобы выйти из комнаты</button>
    </form>
    <form class="some-block" method="get">
        <label>
            Ваше сообщение
        </label>
        <input style="width: 235px; height: 30px; border-radius: 10px" type="text" name="MessageWindow">
        <button class="button8" type="submit">Отправить</button>
        <button class="button8" onclick="location.href='/chat'">Обновить</button>
    </form>


</div>
</body>
</html>
