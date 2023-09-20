<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<title>페이지3 접근형태</title>
</head>
<body>
<input type="button" value="창닫기" onclick="bbb()">
페이지3
</body>
<script>
	var fdata = sessionStorage.getItem("time");
	var times = window.location.search.split("?days=");
	
	if(fdata == null){
		alert("올바른 접근 방법이 아닙니다.");
	}
	else{
		if(fdata != times[1]){
			alert("올바른 접근 방법이 아닙니다.");
		}
	}
</script>
</html>