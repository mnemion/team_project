<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//배열값이 null일 경우 JSP에서 해당 HTML 코드를 비활성화시키는 방법
	String aa = null;
	//out.print((String)request.getAttribute("member_data"));
	try{
		List<ArrayList<String>> member_data = (List<ArrayList<String>>)request.getAttribute("member_data");
		int many = member_data.size();
		aa = "ok";
	}
	catch(Exception e){
		out.print("오류발생");
		aa = (String) request.getAttribute("member_data");
		
	}
%>
<% if(aa != null) { %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>고객 리스트 페이지</title>
</head>
<body>
	
	<br><br>
	<form id="f" method="get" action="./spring6ok.do" onsubmit="return idsearch()">
	검색 : <input type="text" name="search">
	<input type="submit" value="검색">
	</form>
</body>
<%
	}
%>
<script>
	var word = "장바구니의 정보를 출력하는 페이지 바구니";
	/*
		trim() => 앞, 뒤에 공백만 삭제
		replace() => 첫번째 관련 단어만 변경
		replaceAll() => 모든 단어를 확인하여 해당 단어로 변경
	*/
	//var word2 = word.trim();
	//var word2 = word.replace("바구니", "aaa");
	//var word2 = word.replaceAll("바구니", "***");
	//console.log(word2);

	function idsearch() {
		//입력에 따른 공백을 제거 후 조건문으로 재확인
		f.search.value = f.search.value.replaceAll(" ", "");
		if(f.search.value == ""){
			alert("검색할 단어를 입력하세요.");
			return false;
		}
		else{
			return;
		}
	}
</script>
</html>