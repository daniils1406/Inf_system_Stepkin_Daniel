
<%@ taglib prefix="c" uri="jakarta.tags.core" %><%--
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
  <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
  <style>
    .f{
      position: static;
      width: 400px;
      height: 400px;
    }
  </style>
  <h1>База данных сотрудников некоторой компании</h1>
  <c:set var="p" value="${requestScope.outList}"/>
  <c:choose>
    <c:when test="${p!=null}">
      <table border="2" style="margin-right: 100px; margin-bottom: 30px; float: left">
        <tr>
          <c:set var="c" value="${requestScope.ColumnsOfLinkTable}"/>
          <c:forEach var="i" items="${c}">
          <th> <c:out value="${i}"/></th>
          </c:forEach>
        </tr>
        <c:forEach var="i" items="${p}">
          <tr>
            <c:forEach var="i1" items="${i}">
            <td><c:out value="${i1}"/></td>
            </c:forEach>
          </tr>
        </c:forEach>
      </table>
    </c:when>
  </c:choose>


  <c:set var="e" value="${requestScope.employees}"/>
  <form method="get">
    <input type="hidden" name="Delete" value="Delete">
    <c:choose>
      <c:when test="${e!=null}">
  <table border="2" style=" margin-right: 70px; margin-bottom: 30px; float: left">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsOfEmployeeTable}"/>
      <th></th>
      <c:forEach var="i" items="${c}">
        <th><c:out value="${i}"/></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${e}">
      <tr>
        <td><input type="checkbox" name="emp${i.id}" value="${i.id}"></td>
        <td><c:out value="${i.id}"/></td>
        <td><c:out value="${i.firstName}"/></td>
        <td><c:out value="${i.last_name}"/></td>
        <td><c:out value="${i.birthday}"/></td>
        <td><c:out value="${i.gender}"/></td>
        <td><c:out value="${i.education}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>

    <c:set var="t" value="${requestScope.positions}"/>
    <c:choose>
      <c:when test="${t!=null}">


  <table border="2" style="float: left; margin-right: 70px; margin-bottom: 30px;">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsOfPositionTable}"/>
      <th></th>
      <c:forEach var="i" items="${c}">
        <th><c:out value="${i}"></c:out></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${t}">
      <tr>
        <td><input type="checkbox" name="pos${i.id}" value="${i.id}"></td>
        <td><c:out value="${i.id}"/></td>
        <td><c:out value="${i.activity}"/></td>
        <td><c:out value="${i.specialization}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>
    <button type="submit" style="float: left;">Удалить выделенные сущности</button>
  </form>


  <div class="f">
    <form method="get">
      <h4>Отобрать сотрудников по(укажите пустое поле для отмены фильтрации):</h4>
      <select name="columnForSelection">
        <option value="id">id</option>
        <option value="first_name">first_name</option>
        <option value="last_name">last_name</option>
        <option value="birthday">birthday</option>
        <option value="gender">gender</option>
        <option value="education">education</option>
      </select>
      <input type="text" name="argumentOfSelection">
      <button type="submit">Отобрать</button>
    </form>
  </div>




  <form method="get">
    <h4>Упорядочить сотрудников</h4>
    <h5>По столбцу</h5>
    <select name="column">
      <option value="id">id</option>
      <option value="first_name">first_name</option>
      <option value="last_name">last_name</option>
      <option value="birthday">birthday</option>
      <option value="gender">gender</option>
      <option value="education">education</option>
    </select><br>


    <h5>Порядок</h5>

    <select name="ascendingOrDescending">
      <option value="asc">asc</option>
      <option value="desc">desc</option>
    </select><br>
    <button type="submit">Упорядочить</button>
  </form>

  <div>
    <form method="get">
      <h4>Отобрать рабочие места по(укажите пустое поле для отмены фильтрации):</h4>
      <select name="columnForSelection1">
        <option value="id">id</option>
        <option value="activity">activity</option>
        <option value="specialization">specialization</option>
      </select>
      <input type="text" name="argumentOfSelection1">
      <button type="submit">Отобрать</button>
    </form>
  </div>

  <form method="get">
    <H4>Упорядочить рабочие места</H4>

    <h5>По столбцу</h5>
    <select name="column1">
      <option value="id">id</option>
      <option value="activity">activity</option>
      <option value="specialization">specialization</option>
    </select><br>
    <h5>Порядок</h5>
<%--    <input type="text" name="ascendingOrDescending1" list="DescAsc1" />--%>
<%--    <datalist id="DescAsc1">--%>
<%--      <option value="desc">desc</option>--%>
<%--      <option value="asc">asc</option>--%>
<%--    </datalist><br>--%>
    <select name="ascendingOrDescending1">
      <option value="asc">asc</option>
      <option value="desc">desc</option>
    </select><br>
    <button type="submit">Упорядочить</button>
  </form>
  <c:choose>
    <c:when test="${requestScope.ErrorOfFilter!=null}">
      <h2>${requestScope.ErrorOfFilter}</h2>
    </c:when>
  </c:choose>
  <button onclick="location.href='/change'">Создать запись или  отредактировать существуюшую</button>
  </body>
</html>
