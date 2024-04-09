<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="DataBaseExcange.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
    String isSelected(String s1, String s2){
        if(s1.equals(s2))
        {
        	return "selected";
        }
        else
        {
        	return "";
        }
    }

	String showDrivers(ArrayList<DriversData> drivers, String selected)
	{
		String s=new String();
		for(DriversData d : drivers)
		{
			String driver = d.getLastName()+ ' ' +d.getFirstName()+  ' '  +d.getFatherName();
			if(driver.equals(selected))
			{
				s+=("<option value=\"" + driver +"\" selected>" + driver + "</option>");
			}
			else
			{
				s+=("<option value=\"" + driver +"\">" + driver + "</option>");				
			}
		}
		return s;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Автомобили</title>
</head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<body>
<ul class="nav nav-pills">
  <li class="nav-item">
    <a class="nav-link" href="/MyFirstJavaEEProject/Journal">Журнал</a>
  </li>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Справочники</a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item active" href="/MyFirstJavaEEProject/Auto">Автомобили</a></li>
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/Drivers">Водители</a></li>
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/Routes">Маршруты</a></li>
    </ul>
  </li>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Отчеты</a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/AutoOnRoutes">Автомобили на маршрутах</a></li>
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/BonusSum">Премии</a></li>
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/RecordsOnRoutes">Рекорды на маршрутах</a></li>
    </ul>
  </li>
 <li class="nav-item">
    <a class="nav-link" href="/MyFirstJavaEEProject/Logout">Выйти</a>
  </li>
  <li class="nav-item nav-link">
    <p><%=request.getSession().getAttribute("user") %></p>
  </li>
</ul>
	<h1>Изменить данные автомобиля</h1>
	<form method="post">
		<input type="hidden" value="${auto.id}" name="id" />
		<label>Номер</label><br>
		<input type="text" name="num" value="${auto.num}" /><br>
		<br> <label>Цвет</label><br>
		<input type="text" name="color" value="${auto.color}" /><br><br>
		<label>Марка</label><br> 
		<select name="mark" >
			<option value="Мерседес" <%=isSelected("Мерседес",((AutoData)request.getAttribute("auto")).getMark()) %>>Мерседес</option>
			<option value="Газель" <%=isSelected("Газель",((AutoData)request.getAttribute("auto")).getMark()) %>>Газель</option>
			<option value="Форд" <%=isSelected("Форд",((AutoData)request.getAttribute("auto")).getMark()) %>>Форд</option>
		</select><br><br>
		<label>Водитель</label><br>
		<select name="driver" >
		<%=showDrivers((ArrayList<DriversData>)request.getAttribute("drivers"), ((AutoData)request.getAttribute("auto")).getDriver())%>
		</select><br><br>
		<br> <input class="btn btn-outline-success" type="submit" value="Сохранить" />
	</form>
</body>
</html>