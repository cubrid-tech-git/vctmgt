package com.cubrid.util;

import org.apache.commons.codec.binary.Base64;

/**
 * ��й�ȣ�� ��ȣȭ /��ȣȭ ��� ����
 * */
public class KeyGen {
	
	/**
	 * ��й�ȣ ��ȣȭ
	 * */
	public static String enc(String userpass){
		return Base64.encodeBase64String(userpass.getBytes());
	}
	
	/**
	 * ��й�ȣ ��ȣȭ
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