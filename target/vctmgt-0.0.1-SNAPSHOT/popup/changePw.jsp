<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>admin login page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.modal.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/*
		$("#adminLogin").click(function() {
			var loginId = $("#adminId").val();
			var loginPw = $("#adminPw").val();
			alert("id : " + loginId + "\npw : " + loginPw);
			$("#loginForm").submit();
			// location.href="../controller/adminLoginController.jsp?loginId=" + loginId + "&loginPw=" + loginPw;
		});
		*/

		$("#changePwSubmit").click(function() {
			var ename = $("#adminEname").val();
			var originPw = $("#originPw").val();
			var newPw = $("#newPw").val();
			var confirmPw = $("#confirmPw").val();

			//alert(ename + "\n" + originPw + "\n" + newPw + "\n" + confirmPw);
			if(ename == "") {
				alert("이름을 입력하세요.");
				return false;
			}
			if(originPw == "") {
				alert("기존 비밀번호를 입력하세요.");
				return false;
			}
			if(newPw == "") {
				alert("새 비밀번호를 입력하세요.");
				return false;
			}
			if(confirmPw == "") {
				alert("비밀번호를 다시 한번 입력 하세요.");
				return false;
			}
			if(newPw != confirmPw) {
				alert("새 비밀번호와 확인 비밀번호가 서로 다릅니다.");	
				return false;		
			}
			$("#changePwForm").submit();
		});
		
		$("#changePwCancle").click(function() {
			location.href="adminLogin.jsp";
		});
	});
</script>
</head>
<body>
	<form id="changePwForm" method="post" action="../controller/changePwController.jsp">
	<table>
		<tr>
			<td>이름</td>
			<td>:</td>
			<td>
				<input type="text" id="adminEname" name="adminEname">
			</td>
		</tr>
		<tr>
			<td>기존</td>
			<td>:</td>
			<td>
				<input type="password" id="originPw" name="originPw" maxlength="20">
			</td>
		</tr>
		<tr>
			<td>new</td>
			<td>:</td>
			<td>
				<input type="password" id="newPw" name="newPw" maxlength="20">
			</td>
		</tr>
		<tr>
			<td>확인</td>
			<td>:</td>
			<td>
				<input type="password" id="confirmPw" name="confirmPw" maxlength="20">
			</td>
		</tr>
		<tr>
			<td style="text-align: right" colspan="3">
				<input type="button" id="changePwSubmit" name="changePwSubmit" value="확인" >
				<input type="button" id="changePwCancle" value="취소">
			</td>
		</tr>
	</table>
	</form>
</body>
</html>