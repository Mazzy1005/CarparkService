<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Sign in</title>
</head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<body>
<h1>Sign in</h1>
<form method="post" >
<label>Login</label>
<input type="text" name = "login"/><br><br>
<label>Password</label>
<input type="password" name = "password"/><br><br>
<input class="btn btn-outline-success" type="submit" value="Sign in" />
<a href="/MyFirstJavaEEProject/Register">Register</a>
</form>
<%
if(request.getAttribute("error")!=null)
{
	out.println("Error!!! Invalid login or password!!!");
}
%>
</body>
</html>