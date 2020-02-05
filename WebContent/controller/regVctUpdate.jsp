<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.mail.CubMailSender"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.vo.MailVo"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 휴가 사용 신청 controller
	int id = Integer.parseInt(request.getParameter("id"));
	int eno = Integer.parseInt(request.getParameter("eno"));
	int avctId = Integer.parseInt(request.getParameter("avctId"));
	int rvctId = Integer.parseInt(request.getParameter("rvctId"));
	float avctCount = Float.parseFloat(request.getParameter("avctCount"));
	float rvctCount = Float.parseFloat(request.getParameter("rvctCount"));
	String fromDate = request.getParameter("fromDate");
	String toDate = request.getParameter("toDate");
	float avctTime = 0;	// 연차휴가 휴가 시간 변수
	if(request.getParameter("avctTime") != null && !request.getParameter("avctTime").equals("")) avctTime = Float.parseFloat(request.getParameter("avctTime"));
	float rvctTime = 0;	// 대체휴가 휴가 시간 변수
	if(request.getParameter("rvctTime") != null && !request.getParameter("rvctTime").equals("")) rvctTime = Float.parseFloat(request.getParameter("rvctTime"));
	float cvctTime = 0;	// 연차 + 대체휴가일 경우 사용할 휴가 시간 계산 변수 (날짜 기준 소수점 처리)
	String vctReason = URLDecoder.decode(request.getParameter("vctReason"), StandardCharsets.UTF_8.toString());
	String trackingPkey = request.getParameter("trackingPkey");
	String trackingSite = request.getParameter("trackingSite");
	String trackingReason = request.getParameter("trackingReason");
	boolean avctCheck = false;
	boolean rvctCheck = false;
	boolean cvctCheck = false;
	int result = 0;
	
	VctDaoImpl vctDao = new VctDaoImpl();
	// avctTime 값이 0이 아닐 경우 연차휴가 vo 만들어서 insert -> 연차휴가만 사용 신청할 경우
	if(avctTime != 0 && rvctTime == 0) {
		System.out.println("연차!");
		VConfirmVo avctVo = new VConfirmVo();
		avctVo.setId(id);
		avctVo.setEno(eno);
		avctVo.setVct_type("A");
		avctVo.setVct_id(avctId);
		avctVo.setFrom_vctdate(fromDate);
		avctVo.setTo_vctdate(toDate);
		avctVo.setVct_time(avctTime);
		//avctVo.setRemain_vct_time(avctCount - avctTime);
		avctVo.setReason(vctReason);
		
		if(vctDao.regVctUpdate(avctVo) > 0) {
			avctCheck = true;
			// mailing type : VctConfirm
			MailVo mailVo = new UserDaoImpl().selectMailInfo(eno);
			mailVo.setCc(null);
			mailVo.setVo(avctVo);
			CubMailSender.makeSendType("VConfirmUpdate", mailVo);
			CubMailSender.send(mailVo);
		}
	}
	StringBuffer reason = new StringBuffer();
	// rvctTime 값이 0이 아닐 경우 대체휴가 vo 만들어서 insert -> 대체휴가만 사용 신청할 경우
	if(rvctTime != 0 && avctTime == 0) {
		System.out.println("대체!");
		VConfirmVo rvctVo = new VConfirmVo();
		rvctVo.setId(id);
		rvctVo.setEno(eno);
		rvctVo.setVct_type("R");
		rvctVo.setVct_id(rvctId);
		rvctVo.setFrom_vctdate(fromDate);
		rvctVo.setTo_vctdate(toDate);
		rvctVo.setVct_time(rvctTime);
		//rvctVo.setRemain_vct_time(rvctCount - rvctTime);
		reason.append(vctReason + " <br>");
		reason.append("<br>---- 근거사유 ----");
		reason.append("<br>트래킹 ID : " + trackingPkey);
		reason.append("<br>초과근무 사이트 : " + trackingSite);
		reason.append("<br>초과근무 내용 : " + trackingReason);
		rvctVo.setReason(reason.toString());
		
		if(vctDao.regVctUpdate(rvctVo) > 0) {
			rvctCheck = true;
			// mailing type : VctConfirm
			MailVo mailVo = new UserDaoImpl().selectMailInfo(eno);
			mailVo.setCc(null);
			mailVo.setVo(rvctVo);
			CubMailSender.makeSendType("VConfirmUpdate", mailVo);
			CubMailSender.send(mailVo);
		}
	}
	
	// 연차휴가 + 대체휴가를 사용할 경우
	if(rvctTime != 0 && avctTime != 0) {
		System.out.println("연차+대체!");
		// 연차휴가와 대체휴가의 ID 값을 저장하는 map 생성
		Map<String, Object> complexMap = new HashMap<String, Object>();
		complexMap.put("avctId", avctId);
		complexMap.put("rvctId", rvctId);
		complexMap.put("avctTime", avctTime);
		complexMap.put("rvctTime", rvctTime);
		
		VConfirmVo rvctVo = new VConfirmVo();
		rvctVo.setId(id);
		rvctVo.setEno(eno);
		rvctVo.setVct_type("C");
		rvctVo.setComplexMap(complexMap);
		rvctVo.setFrom_vctdate(fromDate);
		rvctVo.setTo_vctdate(toDate);
		rvctVo.setReason(vctReason);
		
		if(vctDao.insertVctConfirmComplex(rvctVo) > 0) {
			System.out.println(" -- end --");
		}
		
	}
	
	// 에러코드
	if(avctTime != 0 && rvctTime == 0 && !avctCheck) result = -1;
	if(rvctTime != 0 && avctTime == 0 && !rvctCheck) result = -2;
	if(avctTime != 0 && rvctTime != 0 && !cvctCheck) result = -3;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var result = <%=result%>;
	if(result == -1) {
		alert("연차휴가 신청 실패");
		history.back();
	}
	if(result == -2) {
		alert("대체휴가 신청 실패");
		history.back();
	}
	if(result == -3) {
		alert("연차 + 대체휴가 신청 실패");
		history.back();
	}
	if(result == 0) {
		alert("휴가 신청 완료");
		window.close();
	}
</script>
</head>
<body>

</body>
</html>