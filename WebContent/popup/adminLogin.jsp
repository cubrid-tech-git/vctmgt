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

		$("#adminCancle").click(function() {
			alert("close");
		});

		$("#adminChangePw").click(function() {
			location.href="changePw.jsp";
		});
	});
</script>
</head>
<body>
	<form id="loginForm" method="post" action="../controller/adminLoginController.jsp">
	<table>
		<tr>
			<td>ID</td>
			<td>:</td>
			<td>
				<input type="text" id="adminId" name="adminId">
			</td>
		</tr>
		<tr>
			<td>PW</td>
			<td>:</td>
			<td>
				<input type="password" id="adminPw" name="adminPw" maxlength="20">
			</td>
		</tr>
		<tr>
			<td colspan="3" style="text-align: right">
				<input type="submit" id="adminLogin" name="submit" value="login" >
				<input type="button" id="adminCancle" value="cancel">
				<input type="button" id="adminChangePw" value="change PW">
			</td>
		</tr>
	</table>
	</form>
</body>
</html>