<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약인원 리스트 페이지</title>
</head>
<body>
	<p>예약 인원 총 리스트 (총 : ${total_person} 명)</p>
	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>아이디</th>
				<th>고객명</th>
				<th>항공사</th>
				<th>인원</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
			<%
				int now_page = 1;
			
				try{
					now_page = Integer.parseInt(request.getParameter("page"));
				}
				catch(Exception e){
				}
				finally{
					try{
						now_page = 1;
					}
					catch(Exception e){
						
					}
				}
				out.print(now_page);
			%>
			<app:set var="w" value="${total_person}"></app:set>
			<app:forEach var="item" items="${total_list}">
				<tr>
					<td>${w}</td>
					<td>${item[1]}</td>
					<td>${item[2]}</td>
					<td>${item[6]}</td>
					<td>${item[7]}</td>
					<td>${item[9]}</td>
				</tr>
				<app:set var="w" value="${w-1}"></app:set>
			</app:forEach>
		</tbody>
	</table>
	<!-- set : 해당 값을 다른 표현식 이름으로 활용할 수 있도록 셋팅하는 태그 -->
	<app:set var="page" value="${total_person / 2}"/>
	<!-- 페이지 번호 forEach의 기본은 배열이 기본으로 작동되는 원리임 -->
	
	<!-- 나머지 값을 확인하여 올림형태로 적용 -->
	<app:forEach varStatus="no" begin="1" end="${page+(1-(page%1))%1}" step="1">
	<div style="width: 30px; height: 30px; border: 1px solid black; display: inline-block; line-height: 30px; text-align: center; cursor: pointer;" onclick="abc(${no.index})">${no.index}</div>
	</app:forEach>
	<!-- 페이지 번호 끝 -->
</body>
<script>
	function abc(pg) {
		location.href='./air_list.do?page='+pg;
	}
</script>
</html>