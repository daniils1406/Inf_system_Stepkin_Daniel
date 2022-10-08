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
<form method="get">
    <input type="hidden" value="CreateEmployee" name="CreateNewEmployee">
    <button type="submit">Добавить нового сторудника</button>
</form>

<%if(request.getAttribute("CreateNewEmployee")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="value<%=i%>">
    </label>
    <%}%>
    <%}%>
    <label>
        Введите id желаемых позииций
        <input type="text" name="idOfPosition">
    </label>
    <button type="submit">Внести нового сотрудника</button>
</form>
<%}%>

<form method="get">
    <input type="hidden" value="CreatePosition" name="CreateNewPosition">
    <button type="submit">Добавить новую позицию</button>
</form>



<%if(request.getAttribute("CreateNewPosition")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="value1<%=i%>">
    </label>
    <%}%>
    <%}%>
    <label>
        Введите id желаемых сотрудников
        <input type="text" name="idOfEmployee">
    </label>
    <button type="submit">Внести новую позицию</button>
</form>
<%}%>

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
    </label>
    <%}%>
    <button type="submit">Внести нового сотрудника</button>
</form>
<%}%>

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
    </label>
    <%}%>
    <button type="submit">Внести новое рабочее место</button>
</form>
<%}%>

<form method="get">
    <input type="hidden" value="CreateLink" name="CreateLink">
    <button type="submit">Назначить сотрудника на должность</button>
</form>
<%if(request.getAttribute("CreateLink")!=null && request.getSession().getAttribute("ColumnsOfTable")!=null){%>
<form method="post">
    <%List<String> y= (List<String>) request.getSession().getAttribute("ColumnsOfTable");%>
    <%for(int i=0;i< y.size();i++){%>
    <%if(i!=0){%>
    <label>
        <%=y.get(i)%>
        <input type="text" name="valueForCreateLink<%=i%>">
    </label>
    <%}%>
    <%}%>
    <button type="submit">Назначить сотрудника</button>
</form>
<%}%>



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
    </label>
    <%}%>
    <%}%>
    <button type="submit">Снять сотрудника</button>
</form>
<%}%>

<button onclick="location.href='..'">Вернутся</button>
</body>
</html>
