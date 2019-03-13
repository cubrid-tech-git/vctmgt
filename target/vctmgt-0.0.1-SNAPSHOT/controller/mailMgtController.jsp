<!-- email 관리 controller -->
<%@page import="java.util.List"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.util.KeyGen"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int eno = Integer.parseInt(request.getParameter("eno"));
	String inputOriginPW = KeyGen.enc(request.getParameter("originPW"));
	String newPW = KeyGen.enc(request.getParameter("newPW"));
	String newPWConfirm = KeyGen.enc(request.getParameter("newPWConfirm"));

	// DB에서 기존 비밀번호 가져오기
	UserDao userDao = new UserDaoImpl();
	List<UserVo> userList = userDao.selectCubUser(eno);
	UserVo originUserVo = userList.get(0);
	String originPW = originUserVo.getEmail_password();
	
	System.out.println("origin PW : \t" + originUserVo.getEmail_password());
	System.out.println("input origin PW : \t" + inputOriginPW);
	System.out.println("new PW : \t" + newPW);
	System.out.println("new PW confirm : \t" + newPWConfirm);
		
	if(!originPW.equals(inputOriginPW)) {
		out.println("<script>");
		out.println("alert('기존 비밀번호가 틀립니다.');");
		out.println("history.back();");
		out.println("</script>");
	} else if(!newPW.equals(newPWConfirm)) {
		out.println("<script>");
		out.println("alert('새 비밀번호가 틀립니다.');");
		out.println("history.back();");
		out.println("</script>");
	} else {
		UserVo userVo = new UserVo();
		userVo.setEno(eno);
		userVo.setEmail_password(newPW);
		
		int result = userDao.updateEmailPassword(userVo);
		
		if(result == 1) {
			out.println("<script>");
			out.println("alert('비밀번호 변경 성공');");
			out.println("window.close();");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('비밀번호 변경 실패');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>email management controller</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.bpopup.min.js"></script>
</head>
<body>
	
</body>
</html>