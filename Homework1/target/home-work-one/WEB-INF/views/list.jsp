<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="servlets.ListServlet" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 03.09.2022
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>





    @keyframes bg {
      0% {
        background-size:    0 3px,
        3px 0,
        0 3px,
        3px 0;
      }
      25% {
        background-size:    100% 3px,
        3px 0,
        0 3px,
        3px 0;
      }
      50% {
        background-size:    100% 3px,
        3px 100%,
        0 3px,
        3px 0;
      }
      75% {
        background-size:    100% 3px,
        3px 100%,
        100% 3px,
        3px 0;
      }
      100% {
        background-size:    100% 3px,
        3px 100%,
        100% 3px,
        3px 100%;
      }
    }

    div {
      width: 25%;
      margin: 2rem auto;
      padding: 2em;

      background-repeat: no-repeat;
      background-image:   linear-gradient(to right, #C9C9C9 100%, #C9C9C9 100%),
      linear-gradient(to bottom, #C9C9C9 100%, #C9C9C9 100%),
      linear-gradient(to right, #C9C9C9 100%, #C9C9C9 100%),
      linear-gradient(to bottom, #C9C9C9 100%, #C9C9C9 100%);
      background-size:    100% 3px,
      3px 100%,
      100% 3px,
      3px 100%;
      background-position:    0 0,
      100% 0,
      100% 100%,
      0 100%;
      animation: bg 1.25s cubic-bezier(0.19, 1, 0.22, 1) 1;
      animation-play-state: paused;
    }

    div:hover {
      animation-play-state: running;
    }
    h5{
      text-align: center;
    }


    button{
      height:20px;
      width:100px;
      margin: -135px -50px;
      position:relative;
      top:50%;
      left:50%;
    }
    img{
      background-image:none;
      margin: 0px +50px;
      width: 150px;
      height: 150px;
    }
    div{
      margin: 10px;
      font-size: 20px;
      height: 80px;
    }
    .sidebar{
      float: left;
      width: 250px;
      height: 250px;
    }
  </style>
</head>
<body>

<%PrintWriter out1 = response.getWriter();%>
  <%List<ListServlet.ContentHolder> p= (List<ListServlet.ContentHolder>) request.getAttribute("Content");%>
 <% if(p.size()>0){%>
    <%for(ListServlet.ContentHolder cl : p){%>
      <div class="sidebar">
      <button class="slide_from_left" onclick="location.href='<%=cl.getMapping()%>'"><%=cl.getButton()%></button>
      <h5><%=cl.getDiscriptio()%></h5>
      <img style="text-align: center" src="<%=cl.getPict()%>">
      </div>
    <%}%>
  <%}else out1.println("<p>There are no users yet!</p>");%>

</body>
</html>
