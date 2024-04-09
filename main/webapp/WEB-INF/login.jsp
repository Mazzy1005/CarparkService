<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Вход</title>
</head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<body>
<h1>Войти</h1>
<form method="post" >
<label>Логин</label>
<input type="text" name = "login"/><br><br>
<label>Пароль</label>
<input type="password" name = "password"/><br><br>
<input class="btn btn-outline-success" type="submit" value="Войти" />
<a href="/MyFirstJavaEEProject/Register">Зарегистрироваться</a>
</form>
<%
if(request.getAttribute("error")!=null)
{
	out.println("Ошибка!!! Неверный логин или пароль!!!");
}
%>
</body>
</html>