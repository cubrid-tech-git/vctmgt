<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="javax.mail.Transport" %>
<%@ page import="javax.mail.Message" %>
<%@ page import="javax.mail.internet.InternetAddress" %>
<%@ page import="javax.mail.Address" %>
<%@ page import="javax.mail.internet.MimeMessage" %>
<%@ page import="javax.mail.Session" %>
<%@ page import="javax.mail.Authenticator" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.cubrid.mail.SMTPAuthenticator" %>
<%
	// 파라미터 수신
	request.setCharacterEncoding("UTF-8");

	String sender = request.getParameter("sender");
	String receiver = request.getParameter("receiver");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	
	// 메일 발송 계정
	String userId = "jira@cubrid.com";
	String userPw = "cubridp@ssw0rd";
	
	// 메일 발송을 위한 정보 설정
	// 메일을 발송해 줄 SMTP 서버에 대한 접속 정보를 설정
	// 정보를 담기 위한 객체
	Properties p = new Properties();
	
	// SMTP 서버의 계정 설정
		
	// SMTP 서버 정보 설정
	// 네이버일 경우 smtp.naver.com
	// Google 일 경우 smtp.gmail.com
	p.put("mail.smtp.host", "smtp.cubird.com");
	
	// 아래 정보는 네이버와 구글이 동일
	p.put("mail.smtp.port", "587");
	p.put("mail.smtp.starttls.enable", "true");
	p.put("mail.smtp.auth", "true");
	
	// 발송하기 부분
	try {
		Authenticator auth = new SMTPAuthenticator(userId, userPw);
		Session ses = Session.getInstance(p, auth);
		
		// 메일을 전송할 때 상세한 상황을 콘솔에 출력
		ses.setDebug(true);
		
		// 메일의 내용을 담기 위한 객체
		MimeMessage msg = new MimeMessage(ses);
		
		msg.addHeader("Content-type", "text/HTML; charset=UTF8");
		msg.addHeader("format", "flowd");
		msg.addHeader("Content-Transfer-Encoding", "8bit");
		msg.setFrom(new InternetAddress(userId, "관리자"));
		
		msg.setSubject(subject, "UTF-8");
        msg.setText(content, "UTF-8");
        msg.setSentDate(new Date());
 
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver, false));
        Transport.send(msg); 
		
		String script = "<script type='text/javascript'>\n";
		script += "alert('메일 발송 성공.')\n";
		script += "history.back();\n";
		script += "</script>";
		out.print(script);
	} catch(Exception mex) {
		mex.printStackTrace();
		String script = "<script type='text/javascript'>\n";
		script += "alert('메일 발송에 실패했습니다.')\n";
		script += "history.back();\n";
		script += "</script>";
		out.print(script);
		return;
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