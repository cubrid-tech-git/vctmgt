<!-- 관리자 로그인 controller -->
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.util.KeyGen"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int vctId = Integer.parseInt(request.getParameter("vctId"));
	float vctcount = Float.parseFloat(request.getParameter("vctcount"));
	int vctConfirmId = Integer.parseInt(request.getParameter("vctConfirmId"));
	float vctConfirmCount = Float.parseFloat(request.getParameter("vctConfirmCount"));
	String fromDate = request.getParameter("fromDate");
	String toDate = request.getParameter("toDate");
	
	System.out.println("vctId : " + vctId + "\nvctCount : " + vctcount + "\nvctConfirmId : " + vctConfirmId + "\nvctConfirmCount : " + vctConfirmCount + "\ndate : " + fromDate + "~" + toDate);
	
	// dao 호출시 사용될 map 생성
	Map<String, VctVo> map = new HashMap<String, VctVo>();
	
	// vct 테이블 update 수행할 id, count 저장
	VctVo vctVo = new VctVo();
	vctVo.setId(vctId);
	vctVo.setVctcount(vctcount);
	map.put("Vct", vctVo);
	
	// vct_confirm 테이블 update 수행할 id, count 저장
	VctVo vctConfirmVo = new VctVo();
	vctConfirmVo.setId(vctConfirmId);
	vctConfirmVo.setVctcount(vctConfirmCount);
	vctConfirmVo.setRegdate(fromDate);
	vctConfirmVo.setEnddate(toDate);
	map.put("VctConfirm", vctConfirmVo);
	
	// dao 생성 및 update 수행
	VctDao vctDao = new VctDaoImpl();
	int result = vctDao.adminPageUpdateVct(map, 1);
	
	// update 성공
	if(result > 0) {
		out.println("<script>");
		out.println("alert('수정 성공');");
		out.println("window.close();");
		out.println("</script>");		
	// update 실패
	} else {
		out.println("<script>");
		out.println("alert('수정 실패');");
		out.println("window.close();");
		out.println("</script>");
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