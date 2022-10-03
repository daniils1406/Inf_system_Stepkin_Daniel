<%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 26.09.2022
  Time: 23:03
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

  .center-div
  {
    position: absolute;
    margin: auto;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 500px;
    height: 250px;
    background-color: #ccc;
    border: black;
    border-radius: 10px;
    border: 1px solid;
  }
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
</style>
<div class="parent">
  <div class="center-div">
    <h1>Добро пожаловать в наш чат!</h1>
    <form method="post">
      <label style="font-size: large">
        Введите ваш ник
        <input style="width: 235px; height: 30px; border-radius: 10px"  type="text" name="NameOfUser">
      </label>
      <button class="button8" type="submit">Выбрать чат</button>
    </form>
  </div>

</div>

</body>
</html>