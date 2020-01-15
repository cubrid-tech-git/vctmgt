package com.cubrid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CurrentTime {
	private static StringBuilder str;
	
	private CurrentTime() {}
	
	public static String getTime() {
		str = new StringBuilder();
		
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		
		str.append(year + "-");
		
		if(month < 10) str.append("0" + month + "-");
		else str.append(month + "-");
		
		if(day < 10) str.append("0" + day);
		else str.append(day);
		
		return str.toString();
	}
	
	/**
	 * 
	 * @return String
	 * 현재 달을 리턴함
	 */
	public static String getCurMonth() {
		Calendar cal = Calendar.getInstance();
		int iMonth = cal.get(Calendar.MONTH) + 1;
		String month = "";
		if(iMonth < 10) month = "0" + iMonth;
		else month = "" + iMonth;
		
		return month;
	}
	
	public static String getCurYear() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		
		return year;
	}
	
	/**
	 * 해당 년도의 시작 일을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String startYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		return year + "-" + "01-01";
	}

	/**
	 * 다음해 첫번째 날을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String endYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) + 1;
		
		return year + "-" + "01-01";
	}
	
	/**
	 * 현재 날짜 기준으로 지난 분기의 시작 일을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String makeStartLastQuater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int quater = (int)Math.ceil(month / 3.0);
		String result = null;
		
		switch (quater) {
		case 1:
			result = (year - 1) + "-10-01";
			break;
		case 2:
			result = year + "-01-01";
			break;
		case 3:
			result = year + "-04-01";
			break;
		case 4:
			result = year + "-07-01";
			break;
		}
		
		return result;
	}
	
	/**
	 * 현재 날짜 기준으로 분기의 시작 일을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String makeStartQuater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int quater = (int)Math.ceil(month / 3.0);
		String result = null;
		
		switch (quater) {
		case 1:
			result = year + "-01-01";
			break;
		case 2:
			result = year + "-04-01";
			break;
		case 3:
			result = year + "-07-01";
			break;
		case 4:
			result = year + "-10-01";
			break;
		}
		
		return result;
	}
	
	/**
	 * 현재 날짜 기준으로 지난 분기의 끝나는 일을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String makeEndLastQuater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int quater = (int)Math.ceil(month / 3.0);
		String result = null;
		
		switch (quater) {
		case 1:
			result = year + "-01-10";
			break;
		case 2:
			result = year + "-04-10";
			break;
		case 3:
			result = year + "-07-10";
			break;
		case 4:
			result = year + "-10-10";
			break;
		}
		
		return result;
	}
	
	/**
	 * 현재 날짜 기준으로 분기의 끝나는 일을 반환하는 메소드
	 * @return YYYY-MM-DD
	 */
	public static String makeEndQuater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int quater = (int)Math.ceil(month / 3.0);
		String result = null;
		
		switch (quater) {
		case 1:
//			result = year + "-01-10";
			result = year + "-04-10";
			break;
		case 2:
//			result = year + "-04-10";
			result = year + "-07-10";
			break;
		case 3:
//			result = year + "-07-10";
			result = year + "-10-10";
			break;
		case 4:
//			result = year + "-10-10";
			result = (year + 1) + "-01-10";
			break;
		}
		
		return result;
	}
	
	public static List<String> dateList() {
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		
		int startYear = 2015;
		int startMonth = 1;
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH) + 1;
		
		for(int i = startYear; i <= curYear; i++) {
			if(i != curYear) {
				for(int j = startMonth; j <= 12; j++) {
					String month = "";
					if(j < 10) month = "0" + j;
					else month = "" + j;
					list.add(i + "-" + month);
				}
			} else {
				for(int j = startMonth; j <= curMonth; j++) {
					String month = "";
					if(j < 10) month = "0" + j;
					else month = "" + j;
					list.add(i + "-" + month);
				}
			}
		}
		
		list.sort(Comparator.reverseOrder());
		
		return list;
	}
	
	public static List<String> quaterToKor(String date) {
		List<String> list = new ArrayList<String>();
		int startYear = 2015;
		int paramYear = Integer.parseInt(date.substring(0, 4));
		
		if(startYear == paramYear) {
			for(int i = 1; i <= 4; i++) {
				String tmp = startYear + "-" + i + "분기";
				list.add(tmp);
			}
		} else if(startYear < paramYear) {
			for(int i = startYear; i <= paramYear; i++) {
				for(int j = 1; j <= 4; j++) {
					String tmp = i + "-" + j + "분기";
					list.add(tmp);
				}
			}
		}
		
		list.sort(Comparator.reverseOrder());
		
		return list;
	}
	
	public static String makeKorQuater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int quater = (int)Math.ceil(month / 3.0);
		String result = year + "-" + quater + "분기";
		
		return result;
	}
	
	public static String korToQuater(String quater) {
		String result = null;
		int year = Integer.parseInt(quater.substring(0, 4));
		int month = Integer.parseInt(quater.substring(5, 6));
		
		switch (month) {
		case 1 :
			result = year + "-04-10";
			break;
		case 2 :
			result = year + "-07-10";
			break;
		case 3 :
			result = year + "-10-10";
			break;
		case 4 :
			result = (year + 1) + "-01-10";
			break;
		}
		
		return result;
	}
	
	public static String korToQuaterStart(String quater) {
		String result = null;
		int year = Integer.parseInt(quater.substring(0, 4));
		int month = Integer.parseInt(quater.substring(5, 6));
		
		switch (month) {
		case 1 :
			result = year + "-01-01";
			break;
		case 2 :
			result = year + "-04-01";
			break;
		case 3 :
			result = year + "-07-01";
			break;
		case 4 :
			result = year + "-10-01";
			break;
		}
		
		return result;
	}
	
	public static String korToQuaterEnd01(String quater) {
		String result = null;
		int year = Integer.parseInt(quater.substring(0, 4));
		int month = Integer.parseInt(quater.substring(5, 6));
		
		switch (month) {
		case 1 :
			result = year + "-04-01";
			break;
		case 2 :
			result = year + "-07-01";
			break;
		case 3 :
			result = year + "-10-01";
			break;
		case 4 :
			result = (year + 1) + "-01-01";
			break;
		}
		
		return result;
	}
	
	public static String milliToDate(long milli) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		Date date = new Date(milli);
		
		return sdf.format(date);
	}
	
	public static String makeFullCalendarEndDate(String date) {
		// date = "2015-07-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(cal.DATE, 1);
		
		return sdf.format(cal.getTime());
	}
}