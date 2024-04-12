<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Register</title>
</head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<body>
<h1>Register</h1>
<form method="post" >
<label>Login</label>
<input type="text" name = "login"/><br><br>
<label>Password</label>
<input type="text" name = "password"/><br><br>
<input class="btn btn-outline-success" type="submit" value="Register" /><br><br>
<%
if(request.getAttribute("error")!=null)
{
	out.println("Error!!! A user with this login already exists!!!");
}
%>
</body>
</html>