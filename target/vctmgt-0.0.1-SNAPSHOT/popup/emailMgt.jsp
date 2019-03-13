<%@page import="com.cubrid.util.KeyGen"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int eno = Integer.parseInt(request.getParameter("eno"));
	UserDao userDao = new UserDaoImpl();
	List<UserVo> list = userDao.selectCubUser(eno);
	UserVo userVo = list.get(0);
	request.setAttribute("userVo", userVo);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email Management Page</title>
<style type="text/css">
#emailDiv {
	text-align: center;
	vertical-align: middle;
}
#changePWForm {
	text-align: center;
}
#PWTbl {
	width: 100%;
	text-align: center;
}
</style>
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css"> --%>
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.modal.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.base64.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#originPW").click(function() {
			$("#originPW").removeAttr("type");
			$("#originPW").attr("value", "");
			$("#originPW").attr("type", "password");
		});

		$("#originPW").keyup(function() {
			$("#originPW").removeAttr("type");
			$("#originPW").attr("value", "");
			$("#originPW").attr("type", "password");
		});
		
		$("#newPW").click(function() {
			$("#newPW").removeAttr("type");
			$("#newPW").attr("value", "");
			$("#newPW").attr("type", "password");
		});

		$("#newPW").keyup(function() {
			$("#newPW").removeAttr("type");
			$("#newPW").attr("value", "");
			$("#newPW").attr("type", "password");
		});
		
		$("#newPWConfirm").click(function() {
			$("#newPWConfirm").removeAttr("type");
			$("#newPWConfirm").attr("value", "");
			$("#newPWConfirm").attr("type", "password");
		});

		$("#newPWConfirm").keyup(function() {
			$("#newPWConfirm").removeAttr("type");
			$("#newPWConfirm").attr("value", "");
			$("#newPWConfirm").attr("type", "password");
		});
		
		$("#submitBtn").click(function() {
			if(($("#originPW").val() == '기존 비밀번호') || ($("#originPW") == "")) {
				alert("기존 비밀번호를 입력하세요.");
				return false;
			}
			
			if(($("#newPW").val() == '새 비밀번호') || ($("#newPW") == "")) {
				alert("새 비밀번호를 입력하세요.");
				return false;
			}
			
			if(($("#newPWConfirm").val() == '새 비밀번호 확인') || ($("#newPWConfirm") == "")) {
				alert("새 비밀번호 확인을 입력하세요.");
				return false;
			}
			
			$("#changePWForm").submit();
		});
	});
</script>
</head>
<body>
	<div id="emailDiv">
		<h3>${userVo.email}</h3>
		<form action="../controller/mailMgtController.jsp" method="post" id="changePWForm">
			<table id="PWTbl">
				<tr>
					<td id="originPWTd">
						<input type="text" name="originPW" id="originPW" value="기존 비밀번호">
					</td>
				</tr>
				<tr>
					<td id="newPWTd">
						<input type="text" name="newPW" id="newPW" value="새 비밀번호">
					</td>
				</tr>
				<tr>
					<td id="newPWConfirmTd">
						<input type="text" name="newPWConfirm" id="newPWConfirm" value="새 비밀번호 확인">
					</td>
				</tr>
			</table>
			<br>
			<button id="submitBtn">확인</button>
			<button id="resetBtn">리셋</button>
			<input type="hidden" value="${userVo.eno}" name="eno">
		</form>
	</div>
</body>
</html>