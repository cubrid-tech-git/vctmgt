<!-- 대체휴가 등록 승인 / 거절 controller -->
<%@page import="com.cubrid.mail.CubMailSender"%>
<%@page import="com.cubrid.vo.MailVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int rvctId = Integer.parseInt(request.getParameter("rvctId"));
	String isType = request.getParameter("isType");
	int eno = Integer.parseInt(request.getParameter("rvctEno"));
	int check = 0;
	
	VctDaoImpl vctDao = new VctDaoImpl();
	UserDao userDao = new UserDaoImpl();
	
	RVConfirmVo rvctVo = new RVConfirmVo();
	rvctVo.setId(rvctId);
	
	if(isType.equals("Y")) {
		float rvctWorkTime = Float.parseFloat(request.getParameter("rvctWorkTime"));
		rvctVo.setWork_time(rvctWorkTime);
		rvctVo.setEno(eno);
		String rvctEndDate = CurrentTime.makeStartQuater();
		rvctVo.setWork_date(rvctEndDate);
		
		// rvct_confirm.status = Y 로 변경
		int result = vctDao.rvctConfirmY1(rvctVo);
		
		// vct의 id, vctcount 조회
		if(result == 1) {
			VctVo vctVo = vctDao.rvctConfirmY2(rvctVo);
			// vct 테이블의 id에 해당하는 vctcount를 update
			vctVo.setVctcount(vctVo.getVctcount() + rvctWorkTime);
			check = vctDao.rvctConfirmY3(vctVo);
			if(check == 1) {
				System.out.println(rvctId + " 승인");
				// logging
				new VctLoggingImpl().rvctRegYNLogging(rvctVo);
				// mailing
				MailVo mailVo = userDao.selectMailInfo(eno);
				mailVo.setConfirm(true);
				mailVo.setCc(null);
				// RConfirmYN
				RVConfirmVo rVo = userDao.selectRVConfirmYNMail(rvctId);
				mailVo.setVo(rVo);
				String managerEmail = mailVo.getReceiver();
				String managerPw = mailVo.getReceiver_password();
				mailVo.setReceiver(mailVo.getSender());
				mailVo.setSender(managerEmail);
				mailVo.setSender_password(managerPw);
				CubMailSender.makeSendType("RConfirmYN", mailVo);
				CubMailSender.send(mailVo);
			}
		}
	} else {
		check = vctDao.rvctConfirmN(rvctVo);
		
		// mailing
		MailVo mailVo = userDao.selectMailInfo(eno);
		mailVo.setConfirm(false);
		mailVo.setCc(null);
		// RConfirmYN
		RVConfirmVo rVo = userDao.selectRVConfirmYNMail(rvctId);
		mailVo.setVo(rVo);
		String managerEmail = mailVo.getReceiver();
		String managerPw = mailVo.getReceiver_password();
		mailVo.setReceiver(mailVo.getSender());
		mailVo.setSender(managerEmail);
		mailVo.setSender_password(managerPw);
		CubMailSender.makeSendType("RConfirmYN", mailVo);
		CubMailSender.send(mailVo);
	}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rvct Confirm Controller for Admin</title>
<script type="text/javascript">
	var check = <%=check%>
	if(check == 1) {
		alert("완료");
		window.close();
	}
</script>
</head>
<body>

</body>
</html>