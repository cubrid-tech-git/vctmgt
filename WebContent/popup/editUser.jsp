<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.data.UserManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setCharacterEncoding("UTF-8");

	int eno = Integer.parseInt(request.getParameter("eno"));
	request.setAttribute("eno", eno);
	System.out.println("eno : " + eno);
	
	UserDao userDao = new UserDaoImpl();
	UserVo userVo = userDao.selectCubUser(eno).get(0);
	
	switch (userVo.getDno()) {
	case 401 :
		userVo.setDname("기술지원 1팀");
		break;
	case 402 :
		userVo.setDname("기술지원 2팀");
		break;
	case 411 :
		userVo.setDname("대전사무소");
		break;
	case 301 :
		userVo.setDname("개발 1팀");
		break;
	case 302 :
		userVo.setDname("개발 2팀");
		break;
	case 311 :
		userVo.setDname("QA팀");
		break;
	case 100 :
		userVo.setDname("팀장");
		break;
	}
	
	request.setAttribute("userVo", userVo);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script src="<%=request.getContextPath()%>/resources/js/user.js"></script>
<title>User Edit Page</title>
</head>
<body>
	<div style="text-align: center;width: 487px;">
		<h2>사용자 정보 수정</h2>
		<table class="userTbl" style="width: 485px;">
			<tr class="titleTr">
				<td class="titleTd" style="with: 50px;">번호</td>
				<td class="titleTd" style="with: 115px;">부서명</td>
				<td class="titleTd" style="with: 120px;">이름</td>
				<td class="titleTd" style="with: 200px;">e-mail</td>
			</tr>
			<tr>
				<td class="titleTd">${userVo.eno}</td>
				<td class="titleTd">
					<select id="cubDno">
						<option value="${userVo.dno}">${userVo.dname}</option>
						<option value="401">기술지원 1팀</option>
						<option value="402">기술지원 2팀</option>
						<option value="411">대전사무소</option>
						<option value="301">개발 1팀</option>
						<option value="302">개발 2팀</option>
						<option value="311">QA팀</option>
						<option value="100">팀장</option>
					</select>
				</td>
				<td class="titleTd">
					<input type="text" id="cubEname" value="${userVo.ename}" size="9">
				</td>
				<td class="titleTd">
					<input type="text" id="cubEmail" value="${userVo.email}" size="33">
				</td>
			</tr>
		</table>
		<hr>
		<table class="userTbl" style="width: 485px;">
			<tr class="titleTr">
				<td class="titleTd" style="with: 200px;">e-mail 비밀번호</td>
				<td style="with: 200px;">
					<input type="password" id="cubEmailPwd" size="28">
				</td>
				<td class="titleTd" style="widows: 85px">
					관리자 임명 <input type="checkbox" id="cubAdminCheck" ${userVo.password eq null?'':'checked'}>
				</td>
			</tr>
		</table>
		<hr>
		<font color="red" size="2">* 주의 : 관리자 변경시 기존 관리자는 로그인이 불가능 할 수 있습니다.</font><br>
		<button id="userEdit">수정</button>
		<button id="userDel">사용자 삭제</button>
		<button id="userCancle">뒤로</button>
		<input type="hidden" id="cubEno" value="${userVo.eno}">
	</div>
</body>
</html>
