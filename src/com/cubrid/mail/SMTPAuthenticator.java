package com.cubrid.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	private String userId;
	private String userPw;
	
	public SMTPAuthenticator(String userId, String userPw) {
		this.userId = userId;
		this.userPw = userPw;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		
		// 네이버나 Gmail 사용자 계정 설정
		// Gmail의 경우 @gmail.com을 제외한 아이디만 입력
		return new PasswordAuthentication(userId, userPw);
	}
}
