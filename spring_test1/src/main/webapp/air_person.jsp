<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="sp1.dbconfig" %>
<%
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        con = dbconfig.info();
        String sql = "SELECT * FROM air_reserse";
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비행기 예매 페이지</title>
</head>
<body>
	<form id="f" method="post" action="./air_personok.do">
	<input type="hidden" name="mcode" value="">
	<p>비행기 예매</p>
	<input type="text" name="mid" placeholder="아이디를 입력하세요."><br>
	<input type="text" name="mname" placeholder="고객명을 입력하세요."><br>
	<input type="text" name="mnum" placeholder="여권번호를 입력하세요."><br>
    <select name="mmobile">
        <option value="">통신사를 선택하세요</option>
        <option value="SKT">SKT</option>
        <option value="KT">KT</option>
        <option value="LG">LG</option>
        <option value="알뜰폰">알뜰폰</option>
    </select><br>
	<input type="text" name="mtel" placeholder="고객 연락처('-' 미입력)" maxlength="11"><br>
	<select name="mairfare_mair_mcode" onchange="data(this.value)">
		<option value="">항공사를 선택하세요</option>
		<%
		    while(rs.next()){
		%>
		<option value="<%=rs.getString("mairfare")%>/<%=rs.getString("mair")%>/<%=rs.getString("mcode")%>"><%=rs.getString("mair")%></option>
		<%
		 }
		%>
	</select><br>
    <input type="hidden" name="mair" id="mair">
	<input type="text" name="mpeo" placeholder="인원수를 입력하세요." onkeyup="person(this.value)"><br>
	<p>총 항공료</p><br>
	<input type="text" name="totalmoney" readonly="readonly" value="0"><br>
	<input type="submit" value="예매 완료" id="btn">
	</form>

<script>
	var money;
	function data(z) {
		var a = z.split("/")
		console.log(a)
		f.mcode.value = a[2]; //비행기 코드
        f.mairfare_mair_mcode.value = a[1]; //항공사명
        f.mair.value = a[1]; //항공사명
        f.totalmoney.value = a[0]; //금액
        money = a[0] //1인 기준 금액
        f.mpeo.value= 1; //항공사 변경시 인원 초기화
    }
    function person(p) {
        var sum = Number(p) * Number(money);
        f.totalmoney.value = sum;
    }
    
    document.querySelector("#btn").addEventListener("click", function() {
		if(confirm("예약을 확정하시겠습니까?")){
			f.submit();	
		}
	});
</script>

</body>
</html>