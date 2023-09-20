<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 완료페이지</title>
</head>
<body>
아이디 : <%=request.getAttribute("userid") %> <br>
패스워드 : ${userpw} <br>
</body>
</html>