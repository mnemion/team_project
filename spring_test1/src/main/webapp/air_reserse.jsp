<%@page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Date time = new Date();
	SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String check = today.format(time);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>항공리스트 등록 페이지 - JS + ES</title>
</head>
<body>
	<p>항공 리스트</p>
	<form id="f" method="post" action="./air_reserveok.do" enctype="application/x-www-form-urlencoded">
		<input type="text" name="mcode" placeholder="비행기 코드 넘버(10~14자리)" maxlength="14"><br>
		<select name="mair">
			<option value="ANA항공">ANA항공</option>
			<option value="오케이항공">오케이항공</option>
			<option value="인디고항공">인디고항공</option>
			<option value="중화항공">중화항공</option>
			<option value="에어아시아">에어아시아</option>
			<option value="대한항공">대한항공</option>
			<option value="아시아나항공">아시아나항공</option>
			<option value="제주항공">제주항공</option>
		</select>
		<br>
		<select name="marea">
			<option value="한국">한국</option>
			<option value="일본">일본</option>
			<option value="중국">중국</option>
			<option value="베트남">베트남</option>
			<option value="말레시아">말레시아</option>
			<option value="크로아티아">크로아티아</option>
			<option value="이집트">이집트</option>
			<option value="터키">터키</option>
		</select>
		<br>
		<input type="datetime-Local" name="mdate">
		<br>
		<input type="text" name="mperson" placeholder="예약 가능 인원수를 입력하세요" onkeyup="air_abc(event)" maxlength="3">
		<br>
		<input type="text" name="mairfare" placeholder="1인 기준 항공료" onkeyup="air_abc(event)">
		<br>
		예매 시작 시간 : <input type="datetime-Local" name="start_date">
		<br>
		예매 종료 시간 : <input type="datetime-Local" name="stop_date">
		<br>
		<input type="button" value="여행항공 정보 입력완료" id="btn">
	</form>
</body>
<!-- 
	onkeypress : onkeydown 되었을 때 값을 가져옴 (특수키 작동 안함)
	onkeydown : onkeydown 되었을 때 값을 가져옴 (특수키 작동 됨)
	onkeyup : keydown 후 keyup 없이 되었을 경우 값을 가져옴 (작동됨)
 -->
<script>
	window.onload = function(){
		var bw = navigator.userAgent.toUpperCase();
		if(bw.indexOf("FIREFOX") >= 0){
			alert("해당 브라우저로는 접속을 차단합니다.");
		}
	}

	function air_abc(event) {
		if(event.key >= 0 || event.key <= 9){
			return;
		}
		else{
			alert("숫자값만 입력하세요.");
			f.mairfare.value = "";
		}
	}
	/*
	var today = new Date();
	console.log(today.getFullYear());
	console.log(today.getMonth() + 1);
	console.log(today.getDate());
	console.log(today.getHours());
	console.log(today.getMinutes());
	console.log(today.getSeconds());
	*/
	
	const times = "<%=check%>"
	console.log(times);
	
	document.querySelector("#btn").addEventListener("click", function(){
		/*
			/내용/g : g(golbal) : 정규표현식 방법으로 데이터값을 체크, 치환, 삭제 등을 할 수 있음.
		*/
		
		var s = f.start_date.value.replaceAll(/-|T|:/g,"");
		var e = f.stop_date.value.replaceAll(/-|T|:/g,"");
		
		if(f.mcode.value == ""){
			alert("비행기 코드 넘버 10~14자리 입력하세요.");
		}
		else if(f.mdate.value == ""){
			alert("출발일자를 선택하세요.");
		}
		else if(f.mdate.value <= times){
			alert("출발일자 시간이 정확하지 않습니다.");
		}
		else if(Number(s) >= Number(e)){
			alert("예매시간이 잘못 세팅되었습니다.");
			f.start_date.value = "";
			f.f.stop_date.value = "";
		}
		else{
			f.submit();
		}
	});
</script>
</html>