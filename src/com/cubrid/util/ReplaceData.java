package com.cubrid.util;

public class ReplaceData {
	/**
	 * 
	 * @param String dept
	 * @return int
	 * @see 부서명을 부서코드로 변환하는 메소드
	 */
	public static int mappingDept(String dept) {
		int result = 0;
		
		if("ceo".equals(dept)) {
			result = 1;
		} else if("sales".equals(dept)) {
			result = 2;
		} else if("management".equals(dept)) {
			result = 3;
		} else if("tech".equals(dept)) {
			result = 4;
		}
		
		return result;
	}
	
	public static String dnoToDept(int dno) {
		String result = "";
		if(dno == 1 || dno == 3) {
			result = "관리";
		} else if(dno == 2) {
			result = "영업";
		} else if(dno == 4) {
			result = "기술";
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param int sec
	 * @return String time
	 * @see 초를 입력받아 00:00:00 형태로 시간을 리턴하는 메소드 
	 */
	public static String secToTime(int sec) {
		int iHour = 0;
		int iMin = 0;
		int iSec = 0;
		int reminder = 0;
		
		String sHour = "";
		String sMin = "";
		String sSec = "";
		
		if(sec > 3600) {
			iHour = sec / 3600;
			reminder = sec % 3600;
			iMin = reminder / 60;
			iSec = reminder % 60;
		} else {
			iMin = sec / 60;
			iSec = sec % 60;
		}
		
		if(iHour < 10) sHour = "0" + iHour;
		else sHour = "" + iHour;
		if(iMin < 10) sMin = "0" + iMin;
		else sMin = "" + iMin;
		if(iSec < 10) sSec = "0" + iSec;
		else sSec = "" + iSec;
		
		return sHour + ":" + sMin + ":" + sSec;
	}
	
	/**
	 * 
	 * @param String time
	 * @return int sec
	 * @see 00:00:00 형태의 시간을 sec로 리턴하는 메소드
	 */
	public static int timeToSec(String time) {
		String t[] = time.split(":");
		int iHour = Integer.parseInt(t[0]);
		int iMin = Integer.parseInt(t[1]);
		int iSec = Integer.parseInt(t[2]);
		
		int result = iHour * 3600 + iMin * 60 + iSec;
		
		return result;
	}
	
	public static String displayStatus(String status) {
		String result = "";
		
		if("N".equals(status)) {
			result = "미등록";
		} else if("W".equals(status)) {
			result = "대기";
		} else if("C".equals(status)) {
			result = "불가";
		} else if("O".equals(status)) {
			result = "승인";
		}
		
		return result;
	}
	
	public static String floatToTime(String input) {
		String[] temp = input.split("\\.");
		float min = Float.parseFloat(temp[1]);
		min = min / 10 * 60;
		
		return temp[0] + ":" + Float.toString(min).substring(0, 2).split("\\.")[0];
	}
	
	public static String timeToFloat(String input) {
		String[] temp = input.split(":");
		float min = Float.parseFloat(temp[1]);
		min = min / 60 * 10;
		
		return temp[0] + "." + (int)min;
	}
}
