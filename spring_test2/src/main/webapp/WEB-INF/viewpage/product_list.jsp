<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int ea = (int) request.getAttribute("listea");
	
	List<ArrayList<String>> plist = (List<ArrayList<String>>)request.getAttribute("plist");
	//숫자를 자동으로 천단위당 콤마 작용하는 Class
	DecimalFormat df = new DecimalFormat("###, ###");
%>
<!-- View 파트 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>등록된 상품 갯수 :<%=ea %></p>
<table border="1">
	<thead>
		<tr align="center">
		<th>번호 </th>	
		<th>상풐코드</th>	
		<th>상품명</th>	
		<th>상품가격</th>	
		<th>수정/삭제</th>	
		</tr>
	</thead>
	<tbody>
	<%
		int w= 0;
		do{
	%>
		<tr align="center">
			<td><%=ea-w%></td>
			<td><%=plist.get(w).get(1) %></td>
			<td align="left"><%=plist.get(w).get(2) %></td>
			<td><%=df.format(Integer.parseInt(plist.get(w).get(3))) %> 원</td>
			<td>
				<input type="button" value="수정" onclick="modify_pd(<%=plist.get(w).get(0)%>)">
				<input type="button" value="삭제" onclick="delete_pd(<%=plist.get(w).get(0)%>)">
				
			</td>
		</tr>
		<%
				w++;
			}while(w < plist.size());
		
		%>
	</tbody>
</table>
</body>
<script>
	function modify_pd(idx){
		location.href='./product_modify.do?idx='+idx;
	}

	function delete_pd(idx){
	    if(confirm("해당 데이터를 삭제하시겠습니까? 절대 복구가 안됩니다.")){
	        location.href='./product_delete.do?idx='+idx;
	    }
	}
</script>
</html>