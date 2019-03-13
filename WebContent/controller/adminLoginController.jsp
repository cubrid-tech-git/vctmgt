<!-- 관리자 로그인 controller -->
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.util.KeyGen"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
	String loginId = request.getParameter("adminId");
	String loginPw = KeyGen.enc(request.getParameter("adminPw"));
	// save session info after checking login
	UserDao userDao = new UserDaoImpl();
	UserVo vo = userDao.selectLoginCheck(loginId);
	
	// 관리자가 로그인 되어 있을 경우 해당 창 그냥 닫기
	
	// 관리자가 아닐 경우
	if(vo == null) {
		out.println("<script>");
		out.println("alert('" + loginId + "님은 관리자가 아닙니다.');");
		out.println("window.close();");
		out.println("</script>");
		return;
	}
	// 관리자 로그인 성공
	if(loginPw.equals(vo.getPassword())) {
		// 세션 저장
		new VctLoggingImpl().adminLoginLogging(vo);
		session.setAttribute("adminEno", vo.getEno());
		out.println("<script charset='UTF-8' src='../resources/js/jquery-1.10.2.js'></script>");
		out.println("<script src='../resources/js/jquery-ui.js'></script>");
		out.println("<script src='../resources/js/main.js'></script>");
		out.println("<script>");
		out.println("$(document).ready(function() {");
		out.println("alert('" + loginId + "님 로그인 하셨습니다.');");
		// 부모창에 페이지 load
		out.println("$(opener.document).find('#tabs-4').load('../view/adminPage.jsp');");
		out.println("});");
		out.println("</script>");
	// 관리자 로그인 실패
	} else {
		out.println("<script>");
		out.println("alert('비밀번호가 틀립니다.');");
		out.println("history.back();");
		out.println("</script>");
		return;
	}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>admin login controller</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.bpopup.min.js"></script>
</head>
<body>

</body>
</html>