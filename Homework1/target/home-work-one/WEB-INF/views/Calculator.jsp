<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 05.09.2022
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title> Calculator
  </title>

  <style type="text/css">
    TABLE {
      width: 25%; /* Ширина таблицы */
      height: 150%; /* Высота таблицы */
    }
  </style>
</head>
<body>
<script>
  function addTextToInput(anElement) {
    var text = document.getElementById('jaloby').value;
    text += anElement.innerText;
    document.getElementById('jaloby').value = text;
  }
</script>

<script>
  function deleteSign(text) {
    text=text.substring(0,text.length-1);
    document.getElementById('jaloby').value = text;
  }
</script>

<script>
  // переменная, в которой хранится выбранное математическое действие
  var op;

  // функция расчёта
  function func(text) {
    // переменная для результата
    var result;
    // получаем первое и второе число
    let p="+-*/";
    var e;
    for(var i=0;i<text.length;i++){
      if(p.includes(text.charAt(i))){
        e=i;
        break;
      }
    }
    var num1 = Number(text.split(op)[0]);
    var num2 = Number(text.split(op)[1]);
    // смотрим, что было в переменной с действием, и действуем исходя из этого
    switch (op) {
      case '+':
        result = num1 + num2;
        break;
      case '-':
        result = num1 - num2;
        break;
      case '*':
        result = num1 * num2;
        break;
      case '/':
        result = num1 / num2;
        break;
    }
    text=String(result);
    document.getElementById('jaloby').value = text;
  }
</script>

<script>
  function switchSign(text){
    let op=text.charAt(0)
    switch (op) {
      case '+':
        text="-"+text.substring(1,text.length);
        document.getElementById('jaloby').value = text;
        break;
      case '-':
        text="+"+text.substring(1,text.length);
        document.getElementById('jaloby').value = text;
        break;
      default:
        text="-"+text;
        document.getElementById('jaloby').value = text;
    }
  }
</script>

<script>
  function deleteall(){
    document.getElementById('jaloby').value = "";
  }
</script>


<script>
  function memoryplus(number){
    document.form1.hiddenValue.value = number;
  }
</script>

<script>
  function memoryExtract(){
    <%
    boolean s=true;
    %>
  }
</script>



<table>
  <tbody>
  <tr>
    <td colspan="4">
        <%
      String p=(String) session.getAttribute("name");
      out.println("<form method=\"post\">");
      if(p!=null){
        out.println("<input value=\""+p+"\" id=\"jaloby\" type=\"text\" name=\"name\"/> In memory: "+p+" ");
      }else{
        out.println("<input id=\"jaloby\" type=\"text\" name=\"name\"/> In memory: ");
      }
      out.println("</td>");
    %>
  </tr>

  <tr>
    <td><button style="height:50px;width:50px" onclick="memoryExtract()">M</button></td><td>
    <td><button style="height:50px;width:50px" type="submit" onclick="memoryplus(document.getElementById('jaloby').value)">M+</button></td>
    </form>
    <td><button style="height:50px;width:50px" onclick="deleteall()">M-</button></td>
    <td><button style="height:50px;width:50px" onclick="deleteSign(document.getElementById('jaloby').value)"><-</button></td>
  </tr>
  <tr>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">1/</button></td><td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">^2</button></td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">^1/2</button></td>
    <td><button id="divide" style="height:50px;width:50px" onclick="addTextToInput(this);op='/'">/</button></td>
  </tr>
  <tr>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">7</button></td><td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">8</button></td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">9</button></td>
    <td><button id="times" style="height:50px;width:50px" onclick="addTextToInput(this);op='*'">*</button></td>
  </tr>
  <tr>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">4</button></td><td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">5</button></td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">6</button></td>
    <td><button id="minus" style="height:50px;width:50px" onclick="addTextToInput(this); op='-'">-</button></td>
  </tr>
  <tr>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">1</button></td><td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">2</button></td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">3</button></td>
    <td><button id="plus" style="height:50px;width:50px" onclick="addTextToInput(this); op='+'">+</button></td>
  </tr>
  <tr>
    <td><button style="height:50px;width:50px" onclick="switchSign(document.getElementById('jaloby').value)">+/-</button></td><td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">0</button></td>
    <td><button style="height:50px;width:50px" onclick="addTextToInput(this)">,</button></td>
    <td><button style="height:50px;width:50px" onclick="func(document.getElementById('jaloby').value)">=</button></td>
  </tr>
  </tbody>
</table>
<button onclick="location.href='/list'">Back to main</button>
</body>
</html>

