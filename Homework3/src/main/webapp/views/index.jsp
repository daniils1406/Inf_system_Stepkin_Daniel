<%@ page import="java.sql.Connection" %>
<%@ page import="app.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="app.entities.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<%@ page import="app.entities.Position" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 01.10.2022
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <style>
    div{
      position: absolute;
      top: 200px;
    }
  </style>
  <h1>База данных сотрудников некоторой компании</h1>
  <%List<List<String>> p= (List<List<String>>) request.getAttribute("outList");%>
  <%if(p!=null && !p.isEmpty()){%>
  <table border="2" style="margin-right: 50px; float: left">
    <tr>
      <%List<String> c=(List<String>) request.getAttribute("ColumnsOfLinkTable");%>
      <%for(int i=0;i<c.size();i++){%>
      <th><%=c.get(i)%></th>
      <%}%>
    </tr>
    <%for(int i=0;i<p.size();i++){%>
    <tr>
    <%for(int j=0;j<p.get(i).size();j++){%>
    <td><%=p.get(i).get(j)%></td>
    <%}%>
    </tr>
  <%}%>
  </table>
  <%}%>
  <%List<Employee> e= (List<Employee>) request.getAttribute("employees");%>
  <form method="get">
    <input type="hidden" name="Delete" value="Delete">
  <%if(e!=null && !e.isEmpty()){%>
  <table border="2" style=" margin-right: 50px;float: left">
    <tr>
      <%List<String> c=(List<String>) request.getAttribute("ColumnsOfEmployeeTable");%>
      <th></th>
      <%for(int i=0;i<c.size();i++){%>
      <th><%=c.get(i)%></th>
      <%}%>
    </tr>
    <%for(int i=0;i<e.size();i++){%>
    <tr>
      <td><input type="checkbox" name="emp<%=e.get(i).getId()%>" value="<%=e.get(i).getId()%>"></td>
      <td><%=e.get(i).getId()%></td>
      <td><%=e.get(i).getFirstName()%></td>
      <td><%=e.get(i).getLast_name()%></td>
      <td><%=e.get(i).getBirthday()%></td>
      <td><%=e.get(i).getGender()%></td>
      <td><%=e.get(i).getEducation()%></td>
    </tr>
    <%}%>
  </table>
  <%}%>

  <%List<Position> t= (List<Position>) request.getAttribute("positions");%>
  <%if(t!=null && !t.isEmpty()){%>

  <table border="2" style="float: left">
    <tr>
      <%List<String> c=(List<String>) request.getAttribute("ColumnsOfPositionTable");%>
      <th></th>
      <%for(int i=0;i<c.size();i++){%>
      <th><%=c.get(i)%></th>
      <%}%>
    </tr>
    <%for(int i=0;i<t.size();i++){%>
    <tr>
      <td><input type="checkbox" name="pos<%=t.get(i).getId()%>" value="<%=t.get(i).getId()%>"></td>
      <td><%=t.get(i).getId()%></td>
      <td><%=t.get(i).getActivity()%></td>
      <td><%=t.get(i).getSpecialization()%></td>
    </tr>
    <%}%>
  </table>
  <%}%>
    <button type="submit" style="position: absolute; top: 100px; left: 1400px">Удалить выделенные сущности</button>
  </form>
<div>
  <form method="get">
    <h4>Упорядочить сотрудников</h4>
    <label>
      Укажите столбец
      <input type="text" name="column">
    </label><br>
    <label>
      Укажите порядок "DESC ASC"
      <input type="text" name="ascendingOrDescending">
    </label><br>
    <button type="submit">Упорядочить</button>
  </form>


  <form method="get">
    <H4>Упорядочить рабочие места</H4>
    <label>
      Укажите столбец
      <input type="text" name="column1">
    </label><br>
    <label>
      Укажите порядок "DESC ASC"
      <input type="text" name="ascendingOrDescending1">
    </label><br>
    <button type="submit">Упорядочить</button>
  </form>
  <button onclick="location.href='/change'">Создать запись или  отредактировать существуюшую</button>
</div>
  </body>
</html>
