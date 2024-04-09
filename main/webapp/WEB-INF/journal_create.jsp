<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="DataBaseExcange.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!String showAuto(ArrayList<AutoData> auto) {
		String s = new String();
		for (AutoData a : auto) {
			{
				s += ("<option value=\"" + a.getNum() + "\">" + a.getNum() + "</option>");
			}
		}
		return s;
	}

	String showRoutes(ArrayList<RouteData> routes) {
		String s = new String();
		for (RouteData r : routes) {
			{
				s += ("<option value=\"" + r.getName() + "\">" + r.getName() + "</option>");
			}
		}
		return s;
	}%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Журнал</title>
</head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<body>
<ul class="nav nav-pills">
  <li class="nav-item">
    <a class="nav-link active" href="/MyFirstJavaEEProject/Journal">Журнал</a>
  </li>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Справочники</a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="/MyFirstJavaEEProject/Auto">Автомобили</a></li>
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
	<h1>Добавить запись в журнал</h1>
	<form method="post">
		<label>Время отправки</label><br>
		<input type="datetime-local" step=1 name="time_out" /><br>
		<br> <label>Время прибытия</label><br> 
		<input type="datetime-local"	step=1 name="time_in" /><br>
		<br> <label>Автомобиль</label><br> 
		<select name="num">
			<%=showAuto((ArrayList<AutoData>) request.getAttribute("auto"))%>
		</select><br>
		<br> <label>Маршрут</label><br> 
		<select name="route">
			<%=showRoutes((ArrayList<RouteData>) request.getAttribute("routes"))%>
		</select><br>
		<br> <br> <input class="btn btn-outline-success" type="submit" value="Сохранить" />
	</form>
</body>
<%
if(request.getAttribute("error")!=null)
{
	out.println("Ошибка!!! Время прибытия должно быть больше чем время отправления, а автомобиль должен находится в парке!!!");
}
%>
</html>