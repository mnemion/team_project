<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//일반 JSP로 받는 형태
	/* Controller Attrubute 사용 시 자료형에 맞는 구조를 생성하여 getAttribute로 로드하는 것이 중요 */
	List<String> al = (ArrayList<String>) request.getAttribute("person_list");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>spring 3일차 - Controller -> view로 배열값 출력(JSP)</title>
</head>
<body>
	<p>고객명 리스트</p>
	<ul>
	<%
		int w = 0;
		while(w < al.size()){
			//out.print("<li>"+al.get(w)+"</li>");
	%>
		<li><%=al.get(w)%></li>
		<%
		w++;
		}
		%>
	</ul>
</body>
</html>