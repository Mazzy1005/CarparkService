<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Количество автомобилей на маршрутах</title>
</head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<body>
<ul class="nav nav-pills">
  <li class="nav-item">
    <a class="nav-link" href="/MyFirstJavaEEProject/Journal">Журнал</a>
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
      <li><a class="dropdown-item active" href="/MyFirstJavaEEProject/AutoOnRoutes">Автомобили на маршрутах</a></li>
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
<h1>Количество автомобилей на маршрутах</h1>
<table class = "table table-sm table-bordered table-striped">
<tr><th>Название маршрута</th><th>Количество автомобилей</th></tr>
<c:forEach var="d" items="${data}">
 <tr><td>${d.rName}</td>
    <td>${d.aNum}</td>
 </tr>
</c:forEach>
</table>
<form method="post">
<input class="btn btn-outline-success" type="submit" value="Скачать в формате txt" />
</form>
</body>
</html>