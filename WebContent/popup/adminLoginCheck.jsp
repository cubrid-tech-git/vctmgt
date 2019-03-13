<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function selfClose() {
		window.close();
	}
</script>
</head>
<body>
<%	
	System.out.println(session.getAttribute("adminEno"));
	if(session.getAttribute("adminEno") != null) {
		out.println("<script charset='UTF-8' src='../resources/js/jquery-1.10.2.js'></script>");
		out.println("<script src='../resources/js/jquery-ui.js'></script>");
		out.println("<script src='../resources/js/main.js'></script>");
		out.println("<script>");
		out.println("$(document).ready(function() {");
		// 부모창에 페이지 load
		out.println("$(opener.document).find('#tabs-4').load('../view/adminPage.jsp');");
		out.println("});");
		//out.println("setInterval(selfClose(), 3000)");
		out.println("</script>");
	// 관리자가 새로 로그인
	} else {
		out.println("<script>");
		out.println("location.href='adminLogin.jsp'");
		out.println("</script>");
	}
%>
</body>
</html>