<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.data.UserManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	int cubDno = Integer.parseInt(request.getParameter("cubDno"));
	String cubName = request.getParameter("cubName");
	String cubEmail = request.getParameter("cubEmail");
	System.out.println("dno : " + cubDno + "\tname : " + cubName + "\temail : " + cubEmail);
	UserVo vo = new UserVo();
	vo.setDno(cubDno);
	vo.setEmail(cubEmail);
	vo.setEname(cubName);
	int result = UserManager.regCubUser(vo);
	if(result > 0) new VctLoggingImpl().userAddLogging(vo);
	
	response.sendRedirect("../popup/regUser.jsp");
%>
</body>
</html>