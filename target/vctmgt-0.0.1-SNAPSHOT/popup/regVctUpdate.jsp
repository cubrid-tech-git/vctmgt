<%@page import="java.util.List"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 휴가 신청한 내용 수정 페이지 //
	int id = Integer.parseInt(request.getParameter("id"));
	VctDao vctDao = new VctDaoImpl();
	VConfirmVo vVo = vctDao.selectVctConfirmListById(id);
	vVo.setReason(vVo.getReason().split(" <br>")[0]);
	vVo.setReason(vVo.getReason().split("휴가 사유 : ")[1]);
	request.setAttribute("vVo", vVo);
	String dnoName = null;
	
	// 연차휴가 남은 데이터 조회하는 vo 만들기
	VctVo aVo = new VctVo();	// 연차휴가용 vo
	aVo.setEno(vVo.getEno());
	aVo.setVct_type("A");
	aVo.setRegdate(CurrentTime.startYear());
	VctVo avctVo = vctDao.selectRemainVct(aVo);
	request.setAttribute("avctVo", avctVo);
	
	// 대체휴가 남은 데이터 조회하는 vo 만들기
	VctVo rVo = new VctVo(); // 대체휴가용 vo
	rVo.setEno(vVo.getEno());
	rVo.setVct_type("R");
	rVo.setRegdate(CurrentTime.makeStartQuater());
	VctVo rvctVo = vctDao.selectRemainVct(rVo);
	request.setAttribute("rvctVo", rvctVo);
	
	// 대체휴가 수정일 경우 select box change에 대한 이벤트 처리
	int trackingId = 0;
	if(request.getParameter("trackingId") != null) {
		trackingId = Integer.parseInt(request.getParameter("trackingId"));
		String fromDate = vVo.getFrom_vctdate();
		String toDate = vVo.getTo_vctdate();
		float rvctTime = vVo.getVct_time();
		RVConfirmVo trackingContentVo = vctDao.selectTrackingSiteReason(trackingId);
		request.setAttribute("trackingContentVo", trackingContentVo);
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("rvctTime", rvctTime);
	}
	
	// 트래킹 서비스 정보 가져오기
	Calendar cal = Calendar.getInstance();
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DAY_OF_MONTH);
	
	if(vVo.getVct_type().equals("R")) {
		// 분기인지 확인
		List<RVConfirmVo> trackingList = null;
		RVConfirmVo trackingVo = new RVConfirmVo();
		
		if(month % 3 == 1) {	// 분기일 경우
			if(day <= 10) {	// 분기가 바뀌는 달의 01 ~ 10 일 사이일 경우
				/*
					현재 날짜 2015-07-06일 기준일 경우
					regdate = '2015-04-01' and enddate = '2015-07-10' 데이터 사용
				*/
				// trackingList 만들기 : eno, 전 분기의 시작 ~ 끝 날짜로 조회
				trackingVo.setEno(vVo.getEno());
				trackingVo.setWork_date(CurrentTime.makeStartLastQuater());
				trackingVo.setWork_end_date(CurrentTime.makeEndLastQuater());
				trackingList = vctDao.selectTrackingList(trackingVo);
			} else { // 분기가 바뀌는 달의 11일 이후일 경우
				/*
					현재 날짜 2015-07-11일 기준일 경우
					regdate = '2015-07-01' and enddate = '2015-10-10' 데이터 사용
				*/
				// trackingList 만들기 : eno, 이번 분기의 시작 ~ 끝 날짜로 조회
				trackingVo.setEno(vVo.getEno());
				trackingVo.setWork_date(CurrentTime.makeStartQuater());
				trackingVo.setWork_end_date(CurrentTime.makeEndQuater());
				trackingList = vctDao.selectTrackingList(trackingVo);
			}
		} else {	// 분기가 아닐 경우
			// trackingList 만들기 : eno, 이번 분기의 시작 ~ 끝 날짜로 조회
			trackingVo.setEno(vVo.getEno());
			trackingVo.setWork_date(CurrentTime.makeStartQuater());
			trackingVo.setWork_end_date(CurrentTime.makeEndQuater());
			trackingList = vctDao.selectTrackingList(trackingVo);
		}
		request.setAttribute("trackingList", trackingList);
	}
	
	switch(vVo.getDno()) {
	case 401:
		dnoName = "기술지원 1팀";
		break;
	case 402:
		dnoName = "기술지원 2팀";
		break;
	case 411:
		dnoName = "대전사무소";
		break;
	case 301:
		dnoName = "개발 1팀";
		break;
	case 302:
		dnoName = "개발 2팀";
		break;
	case 311:
		dnoName = "QA팀";
		break;
	case 100:
		dnoName = "팀장";
		break;
	}
	request.setAttribute("dnoName", dnoName);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 내용 수정</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#trackingId").change(function() {
			var trackingId = $("#trackingId").val();
			var id = <%=id%>;
			$("#regVctContent").load("./regVctUpdate.jsp?id=" + id + "&trackingId=" + trackingId);
		});
		
		$("#regVctUpdateBtn").click(function() {
			var id = $("#id").val();
			var eno = $("#eno").val();
			var avctId = $("#avctId").val();
			var rvctId = $("#rvctId").val();	
			var avctCount = parseFloat($("#avctCount").val());
			var rvctCount = parseFloat($("#rvctTime").val());
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var avctTime = parseFloat($("#avctTime").val());
			var rvctTime = parseFloat($("#rvctTime").val());
			var svctTime = $("#svctTime").val();
			var vctReason = $("#vctReason").val();
			var trackingPkey = $("#trackingPkey").val();
			var trackingSite = $("#trackingSite").val();
			var trackingReason = $("#trackingContent").val();
			
			if(fromDate == null || fromDate == "") {
				alert("휴가 시작일을 입력하세요.");
				return false;
			}
			
			if(toDate == null || toDate == "") {
				alert("휴가 종료일을 입력하세요.");
				return false;
			}
			
			if(avctTime != 0 && avctTime > avctCount) {
				alert("신청하신 연차휴가 일수가 보유한 연차휴가를 초과되었습니다.\n\n보유한 연차휴가 : " + avctCount + "\n신청한 연차휴가 : " + avctTime);
				return false;
			}
			
			if(rvctTime != 0 && rvctTime > rvctCount) {
				alert("신청하신 대체휴가 일수가 보유한 대체휴가를 초과되었습니다.\n\n보유한 대체휴가 : " + rvctCount + "\n신청한 대체휴가 : " + rvctTime);
				return false;
			}
			
			if(avctTime != 0 && rvctTime != 0) {
				alert("현재 연차휴가와 대체휴가를 한번에 사용하실 수 없습니다.\n각각 등록하여 사용하여 주시기 바랍니다.");
				return false;
			}
			
			if(avctTime == 0 && rvctTime == 0) {
				alert("휴가 날짜를 입력해 주시기 바랍니다.");
				return false;
			}

			if(rvctTime != 0 && trackingPkey == "") {
				alert("대체휴가 신청시 사이트를 선택해 주시기 바랍니다.");
				return false;
			}
			
			if(vctReason == null || vctReason == "") {
				alert("휴가 사유를 입력하세요.");
				return false;
			}
			
			location.href="../controller/regVctUpdate.jsp?eno=" + eno + "&avctId=" + avctId + "&rvctId=" + rvctId + "&avctCount=" + avctCount + "&rvctCount=" + rvctCount + "&fromDate=" + fromDate + "&toDate=" + toDate + "&avctTime=" + avctTime + "&rvctTime=" + rvctTime + "&vctReason=" + vctReason + "&svctTime=" + svctTime + "&id=" + id + "&trackingPkey=" + trackingPkey + "&trackingSite=" + trackingSite + "&trackingReason=" + trackingReason;
		});
	});
</script>
</head>
<body>
	<div id="regVctContent" style="text-align: center">
		<form>
			<table class="infoTbl">
				<tr>
					<td class="askTitle" style="width:100px">부서</td>
					<td class="contentTd" style="width:200px">${dnoName}</td>
					<td class="askTitle" style="width:100px">신청자</td>
					<td class="contentTd" style="width:200px">${vVo.ename}</td>
				</tr>
			</table>
			<br>
			
			<table class="infoTbl">
				<tr>
					<td class="askTitle" rowspan="2" style="width:125px">휴가선택</td>
					<td class="askTitle" style="width:75px">시작시간</td>
					<td style="width:100px">
						<input type="date" id="fromDate" value="${vVo.from_vctdate}"/>
					</td>
					<td class="askTitle" style="width: 190px">연차휴가</td>
					<td class="askTitle" style="width: 190px">대체휴가</td>
					<td class="askTitle" style="width: 190px">사이트  선택
						<select id="trackingId" style="width: 20px">
							<c:choose>
								<c:when test="${trackingContentVo.id != null}">
									<option value="${trackingContentVo.id}">${trackingContentVo.site}</option>
								</c:when>
								<c:otherwise>
									<option value="">선택</option>
								</c:otherwise>
							</c:choose>
							<c:forEach var="list" items="${trackingList}">
								<option value="${list.id}">${list.site}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="askTitle">종료시간</td>
					<td>
						<input type="date" id="toDate" value="${vVo.to_vctdate}"/>
					</td>
						<c:choose>
							<c:when test="${vVo.vct_type == 'A'}">
								<td>
									<input type="number" min="0" max="7" id="avctTime" value="${vVo.vct_time}"> &#47; ${avctVo.vctcount} 일
								</td>
								<td>
									<input type="number" min="0" max="${rvctVo.vctcount}" id="rvctTime" value="0"> &#47; ${rvctVo.vctcount} 시간
								</td>
							</c:when>
							<c:when test="${vVo.vct_type == 'R'}">
								<td>
									<input type="number" min="0" max="7" id="avctTime" value="0"> &#47; ${avctVo.vctcount} 일
								</td>
								<td>
									<input type="number" min="0" max="${rvctVo.vctcount}" id="rvctTime" value="${vVo.vct_time}"> &#47; ${rvctVo.vctcount} 시간
								</td>
							</c:when>
						</c:choose>
					<td class="askTitle"> 
						${trackingContentVo.pkey}
					</td>
				</tr>
				<tr>
					<td class="askTitle" >휴가사유</td>
					<td colspan="4">
						<textarea id="vctReason" cols="82" rows="13">${vVo.reason}</textarea>
					</td>
					<td class="contentTd">
						<c:if test="${trackingContentVo ne null}">
							[${trackingContentVo.site}]<br>
							${trackingContentVo.reason}
						</c:if>
					</td>
				</tr>
			</table>
			<br>
			<input type="button" value="등록" id="regVctUpdateBtn">
			<input type="button" value="취소" onclick="window.close();">
		</form>
		<input type="hidden" value="${vVo.id}" id="id">
		<input type="hidden" value="${vVo.eno}" id="eno">
		<input type="hidden" value="${avctVo.id}" id="avctId">
		<input type="hidden" value="${rvctVo.id}" id="rvctId">
		<input type="hidden" value="${avctVo.vctcount}" id="avctCount">
		<input type="hidden" value="${rvctVo.vctcount}" id="rvctCount">
		<input type="hidden" value="${trackingContentVo.site}" id="trackingSite">
		<input type="hidden" value="${trackingContentVo.reason}" id="trackingContent">
		<input type="hidden" value="${trackingContentVo.pkey}" id="trackingPkey">
	</div>
</body>
</html>
