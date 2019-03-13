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
		
		// ���̹��� Gmail ����� ���� ����
		// Gmail�� ��� @gmail.com�� ������ ���̵� �Է�
		return new PasswordAuthentication(userId, userPw);
	}
}
