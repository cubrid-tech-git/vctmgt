<%@page import="com.cubrid.data.UserManager"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setCharacterEncoding("UTF-8");
	List<UserVo> userList = UserManager.selectCubUser(0);
	request.setAttribute("userList", userList);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사용자 등록</title>
<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8"	src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/user.js"></script>
</head>
<body>
	<div id="popup">
	<table class="userTbl" style="width: 485px;">
		<tr class="titleTr">
			<td class="titleTd">부서</td>
			<td class="titleTd">이름</td>
			<td class="titleTd">e-mail</td>
		</tr>
		<tr>
			<td>
				<select id="cubDno">
					<option value="0">선택</option>
					<option value="401">기술지원 1팀</option>
					<option value="402">기술지원 2팀</option>
					<option value="411">대전사무소</option>
					<option value="301">개발 1팀</option>
					<option value="302">개발 2팀</option>
					<option value="303">개발 3팀</option>
					<option value="311">QA팀</option>
					<option value="100">팀장</option>
				</select>
			</td>
			<td><input type="text" size="20" id="cubName"></td>
			<td><input type="text" size="27" id="cubEmail"></td>
		</tr>
	</table>
	<div style="text-align: right;">
		<input type="button" id="regUserBtn" value="등록">
	</div>
	<hr>
	<table class="userTbl" style="width: 485px;">
		<tr class="titleTr">
			<td class="titleTd" style="with: 10px;">번호</td>
			<td class="titleTd" style="with: 108px;">부서명</td>
			<td class="titleTd" style="with: 100px;">이름</td>
			<td class="titleTd" style="with: 110px;">e-mail</td>
			<td class="titleTd" style="with: 67px;">수정</td>
		</tr>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td class="contentTd">${user.eno}</td>
				<td id="select" class="contentTd">
					<c:choose>
						<c:when test="${user.dno == 401}">
							기술지원 1팀
						</c:when>
						<c:when test="${user.dno == 402}">
							기술지원 2팀
						</c:when>
						<c:when test="${user.dno == 411}">
							대전사무소
						</c:when>
						<c:when test="${user.dno == 301}">
							개발 1팀
						</c:when>
						<c:when test="${user.dno == 302}">
							개발 2팀
						</c:when>
						<c:when test="${user.dno == 303}">
							개발 3팀
						</c:when>
						<c:when test="${user.dno == 311}">
							QA팀
						</c:when>
						<c:when test="${user.dno == 100}">
				                	<c:choose>
                                                                <c:when test="${user.ename == '엄기호'}">
                                                                        기술지원 1팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '권호일'}">
                                                                        기술지원 2팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '남재우'}">
                                                                        대전사무소장
                                                                </c:when>
                                                                <c:when test="${user.ename == '한기수'}">
                                                                        개발 1팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '조성룡'}">
                                                                        개발 2팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '김병욱'}">
                                                                        개발 3팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '손승일'}">
                                                                        QA팀장
                                                                </c:when>
                                                                <c:when test="${user.ename == '오명환'}">
                                                                        전체관리자
                                                                </c:when>
                                                        </c:choose>
						</c:when>
					</c:choose>
				</td>
				<td class="contentTd">${user.ename}</td>
				<td class="contentTd">${user.email}</td>
				<td class="contentTd">
					<input class="editBtn" type="button" value="수정" title="${user.eno}">
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
