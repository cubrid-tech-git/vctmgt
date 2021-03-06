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

	String sender = request.getParameter("sender");	// 보내는 사람
	String receiver = request.getParameter("receiver");	// 받는 사람
	String subject = request.getParameter("subject");	// 제목
	String content = request.getParameter("content");	// 내용
	
	
	// 메일 발송 계정
	String userId = "cubridmgt@gmail.com";
	String userPw = "cubrid123";
	
	// 메일 발송을 위한 정보 설정
	// 메일을 발송해 줄 SMTP 서버에 대한 접속 정보를 설정
	// 정보를 담기 위한 객체
	Properties p = new Properties();
	
	// SMTP 서버의 계정 설정
	// Naver와 연결할 경우 네이버 아이디 지정
	// Google과 연결할 경우 본인의 Gmail 주소
	p.put("mail.stmp.user", userId);
	
	// SMTP 서버 정보 설정
	// 네이버일 경우 smtp.naver.com
	// Google 일 경우 smtp.gmail.com
	p.put("mail.smtp.host", "smtp.gmail.com");
	
	// 아래 정보는 네이버와 구글이 동일
	p.put("mail.smtp.port", "465");
	p.put("mail.smtp.starttls.enable", "true");
	p.put("mail.smtp.auth", "true");
	p.put("mail.smtp.debug", "true");
	p.put("mail.smtp.socketFactory.port", "465");
	p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	p.put("mail.smtp.socketFactory.fallback", "false");
	
	
	// 발송하기 부분
	
	try {
		Authenticator auth = new SMTPAuthenticator(userId, userPw);
		Session ses = Session.getInstance(p, auth);
		
		// 메일을 전송할 때 상세한 상황을 콘솔에 출력
		ses.setDebug(true);
		
		// 메일의 내용을 담기 위한 객체
		MimeMessage msg = new MimeMessage(ses);
		
		// 제목 설정
		msg.setSubject(subject, "UTF-8");
		
		// 보내는 사람의 메일 주소
		Address fromAddr = new InternetAddress(sender);
		msg.setFrom(fromAddr);
		
		// 받는 사람의 메일 주소
		Address toAddr = new InternetAddress(receiver);
		msg.addRecipient(Message.RecipientType.TO, toAddr);
		
		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent(content, "text/html;charset=UTF-8");
		
		// 발송하기
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