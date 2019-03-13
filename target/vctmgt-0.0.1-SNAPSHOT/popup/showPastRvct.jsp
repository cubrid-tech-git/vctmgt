<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int adminEno = Integer.parseInt(request.getParameter("adminEno"));

	VctDaoImpl vctDao = new VctDaoImpl();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("confirmType", "A");
	map.put("confirmEno", adminEno);
	List<RVConfirmVo> workList = vctDao.selectRvctConfirm(map);
	request.setAttribute("workList", workList);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>전체 
</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/main.js"></script>
</head>
<body>
	<div style="text-align: center;width: 890px">
		<h2>대체휴가 승인 요청 (미승인 전체 목록)</h2>
		<table class="infoTbl" id="historyTbl">
			<tr class="titleTr">
				<td class="titleTd" style="width: 55px;">이름</td>
				<td class="titleTd" style="width: 100px;">트래킹 ID</td>
				<td class="titleTd" style="width: 140px;">사이트</td>
				<td class="titleTd" style="width: 115px;">시작 시일</td>
				<td class="titleTd" style="width: 115px;">종료 시일</td>
				<td class="titleTd" style="width: 55px;">시간</td>
				<td class="titleTd" style="width: 250px;">내용</td>
				<td class="titleTd" style="width: 50px;">확인</td>
			</tr>
			<c:set var="index" value="0"/>
			<c:forEach var="work" items="${workList}">
				<tr class="contentTr">
					<td class="contentTd">${work.ename}</td>
					<td class="contentTd"><a href="http://www.cubrid.com:8888/browse/${work.pkey}">${work.pkey}</a></td>
					<td class="contentTd">${work.site}</td>
					<td class="contentTd">${work.work_date}</td>
					<td class="contentTd">${work.work_end_date}</td>
					<td class="contentTd">${work.work_time}</td>
					<td class="contentTd">${work.reason}</td>
					<td class="contentTd">
						<button style="width: 50px" class="rvctConfirmBtn" onclick="rvctConfirmFnA('${work.id}', '${work.work_time}', '${work.eno}')">승인</button>
						<button style="width: 50px" class="rvctCancelBtn" onclick="rvctCancelFnA('${work.id}', '${work.eno}')">삭제</button>
					</td>
					<c:set var="index" value="${index+1}" />
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>