<%@ page import="app.support.RandomString" %>
<%@ page import="java.util.concurrent.ThreadLocalRandom" %>
<%@ page import="app.servlets.MainPage" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 12.09.2022
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
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
    h1{
      color: #eeeeee;
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
  <script>
    function Random() {
      var rnd = Math.floor(Math.random() * 1000000000);
      document.getElementById('tb').value = rnd;
    }
  </script>
  <h1>
    Привествуем вас в нашем примитивном мессенджере!
  </h1>
  <label>
    GENERATED ID
  </label>

  <input type="text" id="tb" name="tb" />
  <input class="button8" type="button" value="Random Number!" onclick="Random();" />
  </form>

  <form method="post">
    <label>
      ID
      <input type="text" name="IDOfChat">
    </label>
    <button class="button8" type="submit" >Внести id комнаты</button>
  </form>
  <button class="button8" onclick="location.href='/chat'">Войтит в чат</button>
  </body>
</html>
