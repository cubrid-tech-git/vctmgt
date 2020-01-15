<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.util.ReplaceData"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int eno = Integer.parseInt(request.getParameter("eno"));

	UserDaoImpl userDao = new UserDaoImpl();
	UserVo userVo = userDao.selectCubUser(eno).get(0);
	request.setAttribute("userVo", userVo);
	
	String dnoName = null;
	switch(userVo.getDno()) {
	case 401:
		dnoName = "기술지원 1팀";
		break;
	case 402:
		dnoName = "기술지원 2팀";
		break;
	case 411:
		dnoName = "대전사무소";
		break;
	case 301:
		dnoName = "개발 1팀";
		break;
	case 302:
		dnoName = "개발 2팀";
		break;
	case 303:
		dnoName = "개발 3팀";
		break;
	case 311:
		dnoName = "QA팀";
		break;
	case 100:
		dnoName = "팀장";
		break;
	}
	request.setAttribute("dnoName", dnoName);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 신청</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<style>
.askTitle {
	border: 1px solid grey;
	padding: 0;
	height: 35px;
	font-weight: bold;
	color: white;
	background-color: #336699;
}

</style>
</head>
<body>
	<div style="text-align: center">
		<form>
			<table class="infoTbl">
				<tr>
					<td class="askTitle" style="width:120px">신청자</td>
					<td class="contentTd" style="width:320px">${userVo.ename}</td>
					<td class="askTitle" style="width:120px">부서</td>
					<td class="contentTd" style="width:320px">${dnoName}</td>
				</tr>
			</table>
			<br>
			<table class="infoTbl">
				<tr>
					<td class="askTitle" style="width:120px">휴가 선택</td>
					<td class="contentTd" style="text-align: left;width:320px">
						&nbsp;&nbsp;연차 : <input type="text" style="width: 25px" maxlength="2"/> 일 &nbsp;&nbsp;
						대체 : <input type="text" style="width: 25px"  maxlength="2"/> 시간 &nbsp;&nbsp;
					</td>
					<td class="askTitle" style="width:120px">합계</td>
					<td class="contentTd" style="text-align: left;width:320px">
						&nbsp;&nbsp;<input type="text"  style="width: 25px"  maxlength="2" disabled="disabled"/> 일  &nbsp;
						<input type="text"  style="width: 25px"  maxlength="2" disabled="disabled"/> 시간
					</td>
				</tr>
				<tr>
					<td class="askTitle" style="width:120px">시작 일자</td>
					<td class="contentTd" style="width:320px; text-align: left" colspan="3">
						<input type="date"/>
					</td>
				</tr>
				<tr>
					<td class="askTitle" style="height:200px">휴가 사유</td>
					<td class="contentTd" colspan="3">
						<textarea id="askReason" cols="80" rows="10"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<table class="infoTbl">
				<tr>
					<td class="askTitle" style="width:120px">승인권자명</td>
					<td class="contentTd" style="width:320px">${userVo.adminEname}</td>
					<td class="askTitle" style="width:120px">부서</td>
					<td class="contentTd" style="width:320px">${dnoName}</td>
				</tr>
			</table>
			<br>
			
			<input id="askSubmit" type="submit" value="요청"/>
			<button class="askCloseBtn">닫기</button>
		</form>
	</div>
</body>
</html>
