<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
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
	int eno = Integer.parseInt(request.getParameter("eno"));
	String ename = request.getParameter("ename");
	UserVo userVo = new UserVo();
	userVo.setEno(eno);
	userVo.setEname(ename);
	UserDao userDao = new UserDaoImpl();
	int result = userDao.deleteUser(userVo);
	System.out.println("what's result : " + result);
	if(result > 0) {
		System.out.println("eno : " + eno + " Deleted.");
		new VctLoggingImpl().userDelLogging(userVo);
	} else {
		System.out.println("delete failed.");
	}
	response.sendRedirect("../popup/regUser.jsp");
%>
</body>
</html>