<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int rvctId = Integer.parseInt(request.getParameter("rvctId"));
	VctDaoImpl vctDao = new VctDaoImpl();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("confirmType", "P");
	map.put("confirmEno", rvctId);
	List<RVConfirmVo> rvctList = vctDao.selectRvctConfirm(map);
	RVConfirmVo rvctVo = rvctList.get(0);
	rvctVo.setId(rvctId);
	request.setAttribute("rvctVo", rvctVo);
	int eno = rvctVo.getEno();
	
	UserDaoImpl userDao = new UserDaoImpl();
	rvctVo.setAdmin(userDao.selectAdmin(eno).getAdminEname());
	
	// 시간 포맷 정리
	String startDate = "20" + rvctVo.getWork_date().split(" ")[0];
	String endDate = "20" + rvctVo.getWork_end_date().split(" ")[0];
	String startTime = rvctVo.getWork_date().split(" ")[1];
	String endTime = rvctVo.getWork_end_date().split(" ")[1];
	//System.out.println(startDate + "\n" + startTime + "\n" + endDate + "\n" + endTime);
	request.setAttribute("startDate", startDate);
	request.setAttribute("endDate", endDate);
	request.setAttribute("startTime", startTime);
	request.setAttribute("endTime", endTime);
	
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Reg Rvct</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/main.js"></script>
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
					<td class="contentTd" style="width:295px">${rvctVo.ename}</td>
					<td class="askTitle" style="width:100px">승인권자</td>
					<td class="contentTd" style="width:295px">${rvctVo.admin}</td>
				</tr>
			</table>
			<br>
			<table class="infoTbl" style="width: 790px;">
				<tr>
					<td class="askTitle" style="width: 120px;">사이트</td>
					<td class="askTitle" style="width: 120px;">트래킹 ID</td>
					<td class="askTitle" style="width: 310px;">지원일자</td>
					<td class="askTitle" style="width: ;">구  분</td>
				</tr>
				<tr>
					<td class="contentTd">
						<input type="text" id="rvctSiteName" value="${rvctVo.site}">
					</td>
					<td class="contentTd">
						<input type="text" maxlength="11" id="rvctJiraName" value="${rvctVo.pkey}">
					</td>
					<td class="contentTd">
						시작 : <input type="date" id="rvctStartDate" value="${startDate}"> &nbsp; <input type="time" id="rvctStartTime" value="${startTime}"><br>
						종료 : <input type="date" id="rvctEndDate" value="${endDate}"> &nbsp; <input type="time" id="rvctEndTime" value="${endTime}">
					</td>
					<td class="contentTd">
						<select id="rvctStartType">
							<c:choose>
								<c:when test="${rvctVo.start_type == 'NI'}">
									<option value="ni">심야</option>
								</c:when>
								<c:when test="${rvctVo.start_type == 'VA'}">
									<option value="va">휴일/긴급</option>
								</c:when>
								<c:when test="${rvctVo.start_type == 'RV'}">
									<option value="rv">대체</option>
								</c:when>
								<c:when test="${rvctVo.start_type == 'EV'}">
									<option value="ev">평일긴급</option>
								</c:when>
							</c:choose>
							<option value="ni">심야</option>
							<option value="va">휴일/긴급</option>
							<option value="rv">대체</option>
							<option value="ev">평일긴급</option>
						</select>
						<select id="rvctEndType">
							<c:choose>
								<c:when test="${rvctVo.end_type == 'NI'}">
									<option value="ni">심야</option>
								</c:when>
								<c:when test="${rvctVo.end_type == 'VA'}">
									<option value="va">휴일/긴급</option>
								</c:when>
								<c:when test="${rvctVo.start_type == 'RV'}">
									<option value="rv">대체</option>
								</c:when>
								<c:when test="${rvctVo.start_type == 'EV'}">
									<option value="ev">평일긴급</option>
								</c:when>
							</c:choose>
							<option value="ni">심야</option>
							<option value="va">휴일/대체</option>
							<option value="rv">대체</option>
							<option value="ev">평일긴급</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="askTitle" colspan="5">내  용</td>
				</tr>
				<tr>
					<td class="contentTd" colspan="5">
						<textarea id="rvctReason" cols="109" rows="8" >${rvctVo.reason}</textarea>
					</td>
				</tr>
			</table>
			<br>
			<input id="rvctSubmit" type="button" value="수정"/>
			<input class="b-close" type="button" value="닫기"/>
			<input id="typeHelperBtn" type="button" value="구분 설명"/>
			<input type="hidden" id="hiddenEnoVal" value="${rvctVo.eno}"/>
			<input type="hidden" id="hiddenEnameVal" value="${rvctVo.ename}"/>
			<input type="hidden" id="hiddenType" value="U">
			<input type="hidden" id="rvctId" value="${rvctVo.id}">
		</form>
	</div>
</body>
</html>