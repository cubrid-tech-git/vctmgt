<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="com.cubrid.vo.CostStaticsVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.util.ReplaceData"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String thisQuater = request.getParameter("quater");
	request.setAttribute("thisQuater", thisQuater);
	int eno = Integer.parseInt(request.getParameter("eno"));
	request.setAttribute("eno", eno);
	
	String startQuater = CurrentTime.korToQuaterStart(thisQuater);
	String endQuater = CurrentTime.korToQuater(thisQuater);
	System.out.println("start : " + startQuater + "\tend : " + endQuater);
	
	VctDao vctDao = new VctDaoImpl();
	CostStaticsVo staticsVo = vctDao.selectRvctCostDetailStatics(eno, thisQuater);
	request.setAttribute("staticsVo", staticsVo);
	if(staticsVo != null) {
		float totalTime = staticsVo.getRemain_time() + staticsVo.getUsed_time();
		request.setAttribute("totalTime", totalTime);
		String cost = (int)Math.floor(staticsVo.getRemain_time()) * 10000 + "원";
		request.setAttribute("cost", cost);
	}
	
	List<RVConfirmVo> siteList = vctDao.selectRvctCostDetailList(eno, thisQuater);
	request.setAttribute("siteList", siteList);
	
	UserDao userDao = new UserDaoImpl();
	UserVo userVo = userDao.selectCubUser(eno).get(0);
	
	String ename = userVo.getEname();
	request.setAttribute("ename", ename);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>대체휴가 정산 상세보기</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/main.js"></script>
<style>
#enameDiv {
	position: absolute; top: 15px; right: 10px;
}
</style>
</head>
<body>
	<div class="popupTitle">
		<h2>${thisQuater} 대체휴가 수당</h2>
	</div>
	<div id="enameDiv"><h4>${ename}</h4></div>
	<div style="text-align: center">
		<table class="infoTbl" style="width: 800px;">
			<tr>	
				<td class="askTitle" style="width:200px">전체 대체휴가 시간</td>
				<td class="askTitle" style="width:200px">대체휴가 사용 시간</td>
				<td class="askTitle" style="width:200px">남은 대체휴가 시간</td>
				<td class="askTitle" style="width:200px">대체휴가 수당</td>
			</tr>
			<tr>
				<td class="contentTd">${totalTime}</td>
				<td class="contentTd">${staticsVo.used_time}</td>
				<td class="contentTd">${staticsVo.remain_time}</td>
				<td class="contentTd">${cost}</td>
			</tr>
		</table>
		<br>
		<table class="infoTbl" style="width: 800px;">
			<tr>	
				<td class="askTitle" style="width:200px">사이트명</td>
				<td class="askTitle" style="width:160px">트래킹 서비스</td>
				<td class="askTitle" style="width:160px">시작 날짜</td>
				<td class="askTitle" style="width:160px">종료 날짜</td>
				<td class="askTitle" style="width:120px">발생 시간(H)</td>
			</tr>
			<c:forEach var="list" items="${siteList}">
				<tr>
					<td class="contentTd" title="${list.reason}">${list.site}</td>
					<td class="contentTd">
						<a href="http://www.cubrid.com:8888/browse/${list.pkey}">${list.pkey}</a></td>
					<td class="contentTd">${list.work_date}</td>
					<td class="contentTd">${list.work_end_date}</td>
					<td class="contentTd">${list.work_time}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>