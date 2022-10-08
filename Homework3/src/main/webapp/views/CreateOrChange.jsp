<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="app.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<%@ page import="app.entities.ColumnAndType" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 01.10.2022
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    div{
        float: left;
        width: 300px;
        height: 300px;
    }
</style>
<div>
<form method="get">
    <input type="hidden" value="CreateEmployee" name="CreateNewEmployee">
    <button style="float: left" type="submit">Добавить нового сторудника</button>
</form>

<%if(request.getAttribute("CreateNewEmployee")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <br>
    <label>
        <%=y.get(i)%>
        <input type="text" name="value<%=i%>">
    </label><br>
    <%}%>
    <%}%>
    <label>
        Введите id желаемых позииций
        <input type="text" name="idOfPosition">
    </label><br>
    <button type="submit">Внести нового сотрудника</button>
</form>
<%}%>
</div>

<div>
<form method="get">
    <input type="hidden" value="CreatePosition" name="CreateNewPosition">
    <button style="float: left" type="submit">Добавить новое рабочее место</button>
</form>



<%if(request.getAttribute("CreateNewPosition")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <br>
    <label>
        <%=y.get(i)%>
        <input type="text" name="value1<%=i%>">
    </label><br>
    <%}%>
    <%}%>
    <label>
        Введите id желаемых сотрудников
        <input type="text" name="idOfEmployee">
    </label><br>
    <button style="float: left" type="submit">Внести новую позицию</button>
</form>
<%}%>
</div>

<div>
<form method="get">
    <input type="hidden" value="UpdateEmployee" name="UpdateEmployee">
    <button type="submit">Изменить данные сторудника</button>
</form>

<%if(request.getAttribute("UpdateEmployee")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="valueForUpdate<%=i%>">
    </label><br>
    <%}%>
    <button style="float: left" type="submit">Внести нового сотрудника</button>
</form>
<%}%>
</div>


<div>
<form method="get">
    <input type="hidden" value="UpdatePosition" name="UpdatePosition">
    <button type="submit">Изменить данные раюочего места</button>
</form>

<%if(request.getAttribute("UpdatePosition")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="valueForUpdate1<%=i%>">
    </label><br>
    <%}%>
    <button style="float: left" type="submit">Внести новое рабочее место</button>
</form>
<%}%>

</div>

<div>
<form method="get">
    <input type="hidden" value="CreateLink" name="CreateLink">
    <button style="float: left" type="submit">Назначить сотрудника на должность</button>
</form>
<%if(request.getAttribute("CreateLink")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="valueForCreateLink<%=i%>">
    </label><br>
    <%}%>
    <%}%>
    <button style="float: left" type="submit">Назначить сотрудника</button>
</form>
<%}%>
</div>

<div>
<form method="get">
    <input type="hidden" value="DeleteLink" name="DeleteLink">
    <button type="submit">Снять сотрудника с должности</button>
</form>
<%if(request.getAttribute("DeleteLink")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="valueForDeleteLink<%=i%>">
    </label><br>
    <%}%>
    <%}%>
    <button style="float: left" type="submit">Снять сотрудника</button>
</form>
<%}%>
</div>


<button onclick="location.href='..'">Вернутся</button>
<%if(request.getAttribute("Error")!=null){%>
<h2>Пожалуйста, заполните все поля!</h2>
<%}%>
</body>
</html>
