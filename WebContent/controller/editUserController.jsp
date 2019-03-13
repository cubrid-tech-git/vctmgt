<%@page import="com.cubrid.util.KeyGen"%>
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.data.UserManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setCharacterEncoding("UTF-8");

	int eno = Integer.parseInt(request.getParameter("eno"));
	int dno = Integer.parseInt(request.getParameter("dno"));
	String ename = request.getParameter("ename");
	String email = request.getParameter("email");
	String emailPwd = request.getParameter("emailPwd");
	int cubAdminCheck = Integer.parseInt(request.getParameter("cubAdminCheck"));
	
	UserVo newVo = new UserVo();
	newVo.setEno(eno);
	newVo.setDno(dno);
	newVo.setEname(ename);
	newVo.setEmail(email);
	int result = 0;
	
	UserDao userDao = new UserDaoImpl();
	UserVo originVo = userDao.selectCubUser(eno).get(0);
	
	// emailPwd 확인
	if(emailPwd.equals("") || emailPwd == null) {
		newVo.setEmail_password(originVo.getEmail_password());
	} else {
		newVo.setEmail_password(KeyGen.enc(emailPwd));
	}
	
	// cubAdminCheck 확인 : 0이면 일반, 1이면 admin으로
	if(cubAdminCheck == 1) {
		// 기존 admin 정보 초기화 (dno를 가지고 조회해서 eno 바꾸기)
		result = userDao.cubAdminChange(newVo);
		newVo.setPassword(KeyGen.makeAdminPwd(dno));
	}
	
	result = userDao.cubUserUpdate(newVo);
	
	System.out.println(originVo.toString() + "\n");
	System.out.println("new\neno:"
			+ eno + "\nename:" + ename + "\ndno:" + dno
			+ "\nemail:" + email + "\nemailPwd:" + emailPwd
			+ "\ncubAdminCheck:" + cubAdminCheck);
	
	if(result > 0) {
		out.println("<script>");
		out.println("alert('수정 완료');");
		out.println("location.reload();");
		out.println("</script>");
	} else {
		out.println("<script>");
		out.println("alert('수정 실패');");
		out.println("location.reload();");
		out.println("</script>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script src="<%=request.getContextPath()%>/resources/js/user.js"></script>
<title>User Edit Controller</title>
</head>
<body>
	
</body>
</html>