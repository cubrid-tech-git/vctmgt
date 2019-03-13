<!-- 관리자 비밀번호 변경 controller -->
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.util.KeyGen"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
	String ename = request.getParameter("adminEname");
	String originPw = KeyGen.enc(request.getParameter("originPw"));
	String newPw = KeyGen.enc(request.getParameter("newPw"));
	String confirmPw = KeyGen.enc(request.getParameter("confirmPw"));
	System.out.println("ename : " + ename + "\toriginPw : " + originPw + "\tnewPw : " + newPw + "\tconfirmPw : " + confirmPw);
	
	// originPW가 맞는지 확인
	UserDaoImpl userDao = new UserDaoImpl();
	UserVo vo = userDao.selectLoginCheck(ename);
	System.out.println("ename : " + vo.getEname() + "\toriginPw : " + vo.getPassword() + "\teno : " + vo.getEno());
	if(vo != null && vo.getPassword().equals(originPw)) {
		// DB에 넣기
		vo.setPassword(newPw);
		int result = userDao.updateAdminPw(vo);
		if(result == 1) {
			new VctLoggingImpl().adminChangePWLogging(vo);
			out.println("<script>");
			out.println("alert('비밀번호 변경 성공');");
			out.println("location.href='../popup/adminLogin.jsp';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('비밀번호 변경 실패');");
			out.println("history.back();");
			out.println("</script>");
		}
	} else {
		out.println("<script>");
		out.println("alert('기존 비밀번호가 맞지 않습니다.');");
		out.println("history.back();");
		out.println("</script>");
	}
	
	
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>