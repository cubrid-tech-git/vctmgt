<%@page import="java.util.Calendar"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.data.VctManager"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<%
	String date = CurrentTime.getTime();
	request.setAttribute("curDate", date);
	String curMonth = CurrentTime.getCurMonth();
	request.setAttribute("curMonth", curMonth);
	
	VctDaoImpl dao = new VctDaoImpl();
	List<VctVo> vctList = dao.selectAllVct(0);
	request.setAttribute("vctList", vctList);
	
	// fullCalendar에 표시될 데이터 조회
	String events = dao.selectAllV();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fullcalendar.css">
<link rel="stylesheet" media="print" href="<%=request.getContextPath()%>/resources/css/fullcalendar.print.css">
<script src="<%=request.getContextPath()%>/resources/js/moment.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-ui.custom.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/fullcalendar.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/lang-all.js"></script>
<script>
	$(document).ready(function() {
		$('#calendar').fullCalendar({
			events: [
						<%=events%>
					],
			eventClick: function(event) {
				if(event.content) {
					alert(event.content);
					return false;
				}
			}
		});
	});
</script>
</head>
<body>
	<h2>휴가자 현황</h2>
	<div id="calendar"></div>
	
	<br>
	
	<h2>휴가 정보</h2>
	<table class="infoTbl">
		<tr class="titleTr">
			<td class="titleTd">이름</td>
			<td class="titleTd">남은 연차 휴가</td>
			<td class="titleTd">남은 대체 휴가</td>
		</tr>
		<c:forEach var="vct" items="${vctList}">
		<tr class="contentTr">
			<td class="contentTd" title="${vct.eno}">${vct.ename}</td>
			<td class="titleTd">${vct.vctcount}</td>
			<td class="titleTd">${vct.vctcount_tmp}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>