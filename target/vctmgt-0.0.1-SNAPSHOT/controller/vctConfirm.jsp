<!-- 휴가 승인 controller -->
<%@page import="com.cubrid.mail.CubMailSender"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.vo.MailVo"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int adminEno = Integer.parseInt(session.getAttribute("adminEno").toString());
	int eno = Integer.parseInt(request.getParameter("eno")); 
	int vctConfirmId = Integer.parseInt(request.getParameter("vctConfirmId"));
	int vctId = Integer.parseInt(request.getParameter("vctId"));
	float vctcount = Float.parseFloat(request.getParameter("vctcount"));
	boolean isSend = Boolean.parseBoolean(request.getParameter("isSend"));
	boolean check = false;
	
	VctDaoImpl vctDao = new VctDaoImpl();
	UserDao userDao = new UserDaoImpl();
	
	// 1. vctId에 해당하는 vct.vctcount 조회
	VctVo vctVo = vctDao.selectVctConfirmVctcount(vctId);
	float originalVctcount = 0;
	if(vctId != 0) originalVctcount = vctVo.getVctcount();
	System.out.println(originalVctcount);
			
	// 2. vct_confirm 의 status = 'Y' 로 변경, vct.vctcount UPDATE
	VConfirmVo vconfirmVo = new VConfirmVo();
	vconfirmVo.setId(vctConfirmId);
	vconfirmVo.setVct_id(vctId);
	vconfirmVo.setVct_time(originalVctcount - vctcount);
	int result = 0;
	// vct_confirm.vct_id 가 0이 아닐 경우 -> 경조휴가가 아닐 경우
	if(vctId != 0) result = vctDao.updateVctConfirmVctcount(vconfirmVo);
	else if(vctId == 0) result = vctDao.updateVctConfirmOnlyVct_confirm(vconfirmVo);
	
	// 3. history INSERT
	if(result > 0) {
		check = true;
		// mailing
		// manager eno = 
		// 직원 eno = vctVo.getEno(); vctVo.getEname();
		MailVo mailVo = userDao.selectMailInfo(adminEno);	// 관리자 사번으로 메일 정보 조회하고
		MailVo receiverMailVo = userDao.selectMailInfo(eno);
		mailVo.setReceiver(receiverMailVo.getSender());	// 신청자 사번으로 조회한 메일 주소를 receiver로 넣기
		mailVo.setVo(vconfirmVo);
		mailVo.setConfirm(true);
		VConfirmVo vVo = userDao.selectVctConfirmYNMail(vctConfirmId);
		mailVo.setVo(vVo);
		CubMailSender.makeSendType("VctConfirmYN", mailVo);
		if(isSend) CubMailSender.send(mailVo);
	}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var check = <%=check%>;
	if(check) {
		alert("대체휴가 승인 완료");
		window.close();
	} else {
		alert("대체휴가 승인 실패");
		window.close();
	}
</script>
</head>
<body>

</body>
</html>