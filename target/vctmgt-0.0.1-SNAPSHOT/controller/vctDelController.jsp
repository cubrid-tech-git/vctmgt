<!-- 휴가 신청한 내용 삭제 controller -->
<%@page import="com.cubrid.mail.CubMailSender"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.vo.MailVo"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
	int id = Integer.parseInt(request.getParameter("id"));
	String delReason = request.getParameter("delReason");
	VctDao vctDao = new VctDaoImpl();
	UserDao userDao = new UserDaoImpl();
	VConfirmVo vVo = vctDao.selectVctConfirmListById(id);
	
	int result = vctDao.vctDel(id);
	
	if(result > 0) {
		vVo.setReason(delReason);
		MailVo mailVo = userDao.selectMailInfo(vVo.getEno());
		mailVo.setVo(vVo);
		CubMailSender.makeSendType("VConfirmDel", mailVo);
		
		boolean check = CubMailSender.send(mailVo);
		if(check) {
			out.println("<script>");
			out.println("alert('삭제 성공');");
			out.println("window.close();");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('삭제 실패(메일 발송 실패)');");
			out.println("window.close();");
			out.println("</script>");
		}
	} else {
		out.println("<script>");
		out.println("alert('삭제 실패');");
		out.println("window.close();");
		out.println("</script>");
	}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 신청 내용 삭제</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.bpopup.min.js"></script>
</head>
<body>

</body>
</html>