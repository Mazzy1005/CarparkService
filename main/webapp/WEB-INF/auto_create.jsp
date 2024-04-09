<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="DataBaseExcange.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
	String showDrivers(ArrayList<DriversData> drivers)
	{
		String s=new String();
		for(DriversData d : drivers)
		{
			String driver = d.getLastName()+ ' ' +d.getFirstName()+  ' '  +d.getFatherName();
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
	<h1>Добавить автомобиль</h1>
	<form method="post">
		<label>Номер</label><br>
		<input type="text" name="num" /><br><br>
		<label>Цвет</label><br>
		<input type="text" name="color"/><br><br>
		<label>Марка</label><br> 
		<select name="mark" >
			<option value="Мерседес">Мерседес</option>
			<option value="Газель">Газель</option>
			<option value="Форд">Форд</option>
		</select><br><br>
		<label>Водитель</label><br>
		<select name="driver" >
		<%=showDrivers((ArrayList<DriversData>)request.getAttribute("drivers"))%>
		</select><br><br>
		<br> <input class="btn btn-outline-success" type="submit" value="Сохранить" />
	</form>
</body>
</html>