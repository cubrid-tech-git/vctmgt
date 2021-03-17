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
<title>대체휴가 등록 신청</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/main.js"></script>
<%-- <script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery.regRvct.js"></script> --%>
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
	<div style="text-align: center; width: 790">
		<form>
			<table class="infoTbl" style="width: 790px;">
				<tr>	
					<td class="askTitle" style="width:100px">신청자</td>
					<td class="contentTd" style="width:295px">${userVo.ename}</td>
					<td class="askTitle" style="width:100px">승인권자</td>
					<td class="contentTd" style="width:295px">${userVo.adminEname}</td>
				</tr>
			</table>
			<br>
			<table class="infoTbl" style="width: 790px;">
				<tr>
					<td class="askTitle" style="width: 120px;">사이트</td>
					<td class="askTitle" style="width: 120px;">트래킹 ID</td>
					<td class="askTitle" style="width: 310px;">지원일자</td>
					<td class="askTitle" style="width: ">구  분</td>
				</tr>
				<tr>
					<td class="contentTd">
						<input type="text" id="rvctSiteName">
					</td>
					<td class="contentTd">
						<input type="text" maxlength="11" id="rvctJiraName">
					</td>
					<td class="contentTd">
						시작 : <input type="date" id="rvctStartDate"><input type="time" id="rvctStartTime"><br>
						종료 : <input type="date" id="rvctEndDate"><input type="time" id="rvctEndTime">
					</td>
					<td class="contentTd">
						<select id="rvctStartType">
							<option value="no">선택</option>
							<option value="ni">심야</option>
							<option value="va">휴일/긴급</option>
							<option value="rv">대체</option>
							<option value="ev">평일긴급</option>
							<!--  구분이 복잡하여 삭제
							<option value="no">선택</option>
							<option value="co">평일-초과(1)</option>
							<option value="cd">평일-심야(1.3)</option>
							<option value="eo">긴급-초과(1.3)</option>
							<option value="ed">긴급-심야(1.5)</option>
							<option value="ro">대체-초과(1)</option>
							<option value="rd">대체-심야(1.3)</option>
							<option value="ve">휴일/긴급(1.5)</option> -->
						</select>
						<select id="rvctEndType">
							<option value="no">선택</option>
							<option value="ni">심야</option>
							<option value="va">휴일/긴급</option>
							<option value="rv">대체</option>
							<option value="ev">평일긴급</option>
							<!-- <option value="no">선택</option>
							<option value="co">평일-초과(1)</option>
							<option value="cd">평일-심야(1.3)</option>
							<option value="eo">긴급-초과(1.3)</option>
							<option value="ed">긴급-심야(1.5)</option>
							<option value="ro">대체-초과(1)</option>
							<option value="rd">대체-심야(1.3)</option>
							<option value="ve">휴일/긴급(1.5)</option> -->
						</select>
					</td>
				</tr>
				<tr>
					<td class="askTitle" colspan="5">내  용</td>
				</tr>
				<tr>
					<td class="contentTd" colspan="5">
						<textarea id="rvctReason" cols="109" rows="8"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<input id="rvctSubmit" type="button" value="요청"/>
			<!-- <input id="rvctRegSubmit" type="button" value="요청"/> -->
			<input class="b-close" type="button" value="닫기"/>
			<input id="typeHelperBtn" type="button" value="구분 설명"/>
			<input type="hidden" id="hiddenEnoVal" value="${userVo.eno}"/>
			<input type="hidden" id="hiddenEnameVal" value="${userVo.ename}"/>
			<input type="hidden" id="hiddenType" value="I">
		</form>
	</div>
</body>
</html>
