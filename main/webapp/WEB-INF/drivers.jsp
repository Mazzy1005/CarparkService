<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Водители</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
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
      <li><a class="dropdown-item active" href="/MyFirstJavaEEProject/Drivers">Водители</a></li>
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
<h1>Водители</h1>
<table class = "table table-sm table-bordered table-striped">
<tr><th>Id</th><th>Фамилия</th><th>Имя</th><th>Отчество</th><th colspan = "2">Действия</th></tr>
<c:forEach var="d" items="${data}">
 <tr><td>${d.id}</td>
    <td>${d.lastName}</td>
    <td>${d.firstName}</td>
    <td>${d.fatherName}</td>
    <td>
    <form method="get" action="/MyFirstJavaEEProject/DriverEdit">
        <input type="hidden" name="id" value=${d.id}/>
        <input class="btn btn-outline-primary" type="submit" value="Редактировать" />
    </form>
    </td>
    <td>
    <form method="post">
        <input type="hidden" name="id" value=${d.id}/>
        <input class="btn btn-outline-danger" type="submit" value="Удалить" />
    </form>
    </td>
 </tr>
</c:forEach>
</table>
<form method="get" action="/MyFirstJavaEEProject/DriverCreate">
<input class="btn btn-outline-success" type="submit" value="Добавить" />
</form>
</body>
</html>