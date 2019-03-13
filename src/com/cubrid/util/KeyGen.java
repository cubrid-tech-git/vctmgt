package com.cubrid.util;

import org.apache.commons.codec.binary.Base64;

/**
 * 비밀번호를 암호화 /복호화 기능 구현
 * */
public class KeyGen {
	
	/**
	 * 비밀번호 암호화
	 * */
	public static String enc(String userpass){
		return Base64.encodeBase64String(userpass.getBytes());
	}
	
	/**
	 * 비밀번호 복호화
	 * */
	public static String dec(String userpass){
		return new String(Base64.decodeBase64(userpass));
	}
	
	public static String makeAdminPwd(int admincode) {
		String result = null;
		
		switch (admincode) {
		case 1:
			result = KeyGen.enc("ceoadmin!@#");
			break;
		case 2:
			result = KeyGen.enc("ceoadmin!@#");
			break;
		case 3:
			result = KeyGen.enc("salesadmin!@#");
			break;
		case 4:
			result = KeyGen.enc("techadmin!@#");
			break;
		}
		
		return result;
	}

}