<%@page import="com.cubrid.scheduler.AvctQuartzCronTrigger"%>
<%@page import="com.cubrid.scheduler.RvctQuartzCronTrigger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 대체휴가 자동 갱신 스케쥴러
	RvctQuartzCronTrigger rvctScheduler = new RvctQuartzCronTrigger();
	rvctScheduler.start();
	
	// 연차휴가 자동 갱신 스케쥴러
	AvctQuartzCronTrigger avctScheduler = new AvctQuartzCronTrigger();
	avctScheduler.start();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 현황판</title>
<link rel="stylesheet" href="resources/css/main.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<script src="resources/js/jquery-1.10.2.js"></script>
<script src="resources/js/jquery-ui.js"></script>
<script src="resources/js/main.js"></script>
<script>
$(function() {
	$("#tabs-1").load("view/summaryPage.jsp");
	$("#tabs").tabs();
});
</script>
</head>
<body>
	<div id="wrapper">
	
		<div id="header"></div><!-- end header -->

		<div id="container">

			<div id="tabs">
				<ul>
					<li id="tab1"><a href="#tabs-1">요약 정보</a></li>
					<li id="tab2"><a href="#tabs-2">개인별 관리</a></li>
					<li id="tab3"><a href="#tabs-3">정보</a></li>
					<li id="tab4"><a href="#tabs-4">관리자</a></li>
				</ul>
				<div id="tabs-1"></div>
				<div id="tabs-2"></div>
				<div id="tabs-3"></div>
				<div id="tabs-4">관리자</div>
			</div><!-- end tabs -->
			
		</div><!-- end container -->
		
		<div id="footer"></div><!-- end footer -->
		
	</div><!-- end wrapper -->
	<input type="hidden" id="hiddenId" value="test">
</body>
</html>