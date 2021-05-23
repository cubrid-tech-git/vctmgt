<!-- 대체휴가 등록 controller -->
<%@page import="com.cubrid.log.VctLoggingImpl"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.vo.MailVo"%>
<%@page import="com.cubrid.mail.CubMailSender"%>
<%@page import="com.cubrid.util.ReplaceData"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
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
	int eno = Integer.parseInt(request.getParameter("rvctEno"));
	String site = URLDecoder.decode(request.getParameter("rvctSiteName"), StandardCharsets.UTF_8.toString());
	String jira = URLDecoder.decode(request.getParameter("rvctJiraName"), StandardCharsets.UTF_8.toString());
	String startDate = request.getParameter("rvctStartDate");
	String endDate = request.getParameter("rvctEndDate");
	String startTime = request.getParameter("rvctStartTime");
	String endTime = request.getParameter("rvctEndTime");
	String startType = request.getParameter("rvctStartType");
	String endType = request.getParameter("rvctEndType");
	String reason = URLDecoder.decode(request.getParameter("rvctReason"), StandardCharsets.UTF_8.toString());
	float rvctWorkTime = Float.parseFloat(request.getParameter("rvctWorkTime"));
	String rvctEname = request.getParameter("rvctEname");
	String rvctEmail = request.getParameter("rvctEmail");
	//System.out.println(eno + "\n" + rvctEname + "\n" + rvctEmail+ "\n" + site + "\n" + jira + "\n" + startDate + "\n" + endDate + "\n" + startTime + "\n" + endTime + "\n" + startType + "\n" + endType + "\n" + reason + "\n" + rvctWorkTime);
	
	RVConfirmVo vo = new RVConfirmVo();
	vo.setEno(eno);
	vo.setEname(rvctEname);
	vo.setPkey(jira);
	vo.setSite(site);
	vo.setStatus("W");
	vo.setWork_date(startDate + " " + startTime);
	vo.setStart_type(startType.toUpperCase());
	vo.setWork_end_date(endDate + " " + endTime);
	vo.setEnd_type(endType.toUpperCase());
	vo.setWork_time(rvctWorkTime);
	vo.setReason(reason);
	
	
	// DB에 대체휴가 신청 넣고
	VctDaoImpl dao = new VctDaoImpl();
	int result = dao.insertRvctConfirm(vo);
	System.out.println("replace vct confirm result : " + result);
	
	if(result == 1) {
		new VctLoggingImpl().rvctRegLogging(vo);
		// 관리자(처부장)에게 메일보내기
		MailVo mailVo = new UserDaoImpl().selectMailInfo(eno);
		mailVo.setVo(vo);
		mailVo.setCc(null);
		//@@@@@@@@@@이메일 테스트@@@@@@@@@@
		//mailVo.setManager_ename("원종민");
		//mailVo.setReceiver("jmwon@cubrid.com");
		//System.out.println("Cc : " + mailVo.getCc() + " vo: " + mailVo.getVo() + " Manager_ename: " + mailVo.getManager_ename() + " Receiver: " + mailVo.getReceiver() + " Sender: " + mailVo.getSender() );
		CubMailSender.makeSendType("RConfirm", mailVo);
		CubMailSender.send(mailVo);
	}
	
	
%>
</body>
	<script>
		alert("대체휴가 신청 완료");
		window.close();
	</script>
</html>