<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 휴가 사용 신청 등록 페이지 //
	int eno = Integer.parseInt(request.getParameter("eno"));
	System.out.println("eno : " + eno);
	
	VctVo vctVo = new VctVo();	// 연차휴가용 vo
	String vctRegDate = CurrentTime.startYear();
	vctVo.setEno(eno);
	vctVo.setVct_type("A");
	vctVo.setRegdate(vctRegDate);
	
	// DB에서 정보 가져오기
	VctDaoImpl vctDao = new VctDaoImpl();
	// 현재 날짜를 기준으로 분기가 바뀌는 달이면서, 10 작은지 큰지 판단
	
	Calendar cal = Calendar.getInstance();
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DAY_OF_MONTH);
	
	int dno = 0;
	List<RVConfirmVo> trackingList = null;
	RVConfirmVo trackingVo = new RVConfirmVo();
	
	// 트래킹 정보 출력할 id 값 파라미터로 받기
	int id = 0;
	if(request.getParameter("id") != null) {
		id = Integer.parseInt(request.getParameter("id"));
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		int rvctTime = Integer.parseInt(request.getParameter("rvctTime"));
		RVConfirmVo trackingContentVo = vctDao.selectTrackingSiteReason(id);
		request.setAttribute("trackingContentVo", trackingContentVo);
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("rvctTime", rvctTime);
	}
	
	// 분기인지 확인
	if(month % 3 == 1) {	// 분기일 경우
		if(day <= 10) {	// 분기가 바뀌는 달의 01 ~ 10 일 사이일 경우
			/*
				현재 날짜 2015-07-06일 기준일 경우
				regdate = '2015-04-01' and enddate = '2015-07-10' 데이터 사용
			*/
			VctVo rvctVo = new VctVo(); // 대체휴가용 vo
			String rvctRegDate = CurrentTime.makeStartLastQuater();
			rvctVo.setEno(eno);
			rvctVo.setVct_type("R");
			rvctVo.setRegdate(rvctRegDate);
			
			VctVo rvctViewVo = vctDao.selectRemainVct(rvctVo);
			
			request.setAttribute("rvctVo", rvctViewVo);
			
			dno = rvctViewVo.getDno();
			
			// trackingList 만들기 : eno, 전 분기의 시작 ~ 끝 날짜로 조회
			trackingVo.setEno(eno);
			trackingVo.setWork_date(CurrentTime.makeStartLastQuater());
			trackingVo.setWork_end_date(CurrentTime.makeEndLastQuater());
			trackingList = vctDao.selectTrackingList(trackingVo);
		} else {	// 분기가 바뀌는 달의 11일 이후일 경우
			/*
				현재 날짜 2015-07-11일 기준일 경우
				regdate = '2015-07-01' and enddate = '2015-10-10' 데이터 사용
			*/
			VctVo rvctVo = new VctVo(); // 대체휴가용 vo
			String rvctRegDate = CurrentTime.makeStartQuater();
			rvctVo.setEno(eno);
			rvctVo.setVct_type("R");
			rvctVo.setRegdate(rvctRegDate);
			
			VctVo rvctViewVo = vctDao.selectRemainVct(rvctVo);
			request.setAttribute("rvctVo", rvctViewVo);
			
			dno = rvctViewVo.getDno();
			
			// trackingList 만들기 : eno, 이번 분기의 시작 ~ 끝 날짜로 조회
			trackingVo.setEno(eno);
			trackingVo.setWork_date(CurrentTime.makeStartQuater());
			trackingVo.setWork_end_date(CurrentTime.makeEndQuater());
			trackingList = vctDao.selectTrackingList(trackingVo);
		}
		
	} else {	// 분기가 아닐 경우
		VctVo rvctVo = new VctVo(); // 대체휴가용 vo
		String rvctRegDate = CurrentTime.makeStartQuater();
		rvctVo.setEno(eno);
		rvctVo.setVct_type("R");
		rvctVo.setRegdate(rvctRegDate);
		
		VctVo rvctViewVo = vctDao.selectRemainVct(rvctVo);
		request.setAttribute("rvctVo", rvctViewVo);
		
		dno = rvctViewVo.getDno();
		
		// trackingList 만들기 : eno, 이번 분기의 시작 ~ 끝 날짜로 조회
		trackingVo.setEno(eno);
		trackingVo.setWork_date(CurrentTime.makeStartQuater());
		trackingVo.setWork_end_date(CurrentTime.makeEndQuater());
		trackingList = vctDao.selectTrackingList(trackingVo);
	}
	VctVo vctViewVo = vctDao.selectRemainVct(vctVo);
	request.setAttribute("vctVo", vctViewVo);
	request.setAttribute("trackingList", trackingList);
	if(dno == 0) dno = vctViewVo.getDno();
	
	String dnoName = null;
	switch(dno) {
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
	case 303:
		dnoName = "개발 3팀";
		break;
	case 311:
		dnoName = "QA팀";
		break;
    case 100:
        switch(eno) {
        case 6: dnoName = "기술지원 1팀장"; break;
        case 7: dnoName = "기술지원 2팀장"; break;
        case 3: dnoName = "대전사무소장"; break;
        case 27: dnoName = "개발 1팀장"; break;
        case 29: dnoName = "개발 2팀장"; break;
        case 28: dnoName = "개발 3팀장"; break;
        case 5: dnoName = "QA팀장"; break;
        case 20: dnoName = "전체관리자"; break;
        }
		break;
	}
	request.setAttribute("dnoName", dnoName);

// 분기 전환 문제로 추가
	VctVo pVctVo = vctDao.selectAllVct(eno).get(0);
	request.setAttribute("pVctVo", pVctVo);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 등록</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/main.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#trackingId").change(function() {
			var id = $("#trackingId").val();
			var eno = <%=eno%>;
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var rvctTime = $("#rvctTime").val();
			var vctReason = $("#vctReason").val();
			$("#regVctContent").load("./regVct.jsp?eno=" + eno + "&id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&rvctTime=" + rvctTime + "&vctReason=" + vctReason);
		});
	});
</script>
<script type="text/javascript">
/**
 * 휴가 신청 시 중복 클릭 방지
 **/
	$(document).ready(function() {
		$("#regVctConfirmBtn").click(function() {
			if($("#fromDate").val() != ""){
				if($("#toDate").val() != ""){
					if($("#avctTime").val() > 0 || ($("#trackingId").val() !== "" && $("#rvctTime").val() > 0)){
						if($("#vctReason").val() != ""){
							this.setAttribute("disabled", "disabled");
						}
					}
				}
			}
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
					<td class="contentTd" style="width:200px">${vctVo.ename}</td>
				</tr>
			</table>
			<br>
			
			<table class="infoTbl">
				<tr>
					<td class="askTitle" rowspan="2" style="width:125px">휴가선택</td>
					<td class="askTitle" style="width:75px">시작시간</td>
					<td style="width:100px">
						<input type="date" id="fromDate" value="${fromDate}"/>
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
						<input type="date" id="toDate" value="${toDate}"/>
					</td>
					<td>
						<input type="number" min="0" max="7" id="avctTime" value="0"> &#47; ${vctVo.vctcount} 일
					</td>
					<td>
						<c:choose>
							<c:when test="${trackingContentVo ne null}">
								<input type="number" min="0" max="${pVctVo.vctcount_tmp}" id="rvctTime" value="${pVctVo.vctcount_tmp}"> &#47; ${pVctVo.vctcount_tmp} 시간
							</c:when>
							<c:otherwise>
								<input type="number" min="0" max="${pVctVo.vctcount_tmp}" id="rvctTime" value="0"> &#47; ${pVctVo.vctcount_tmp} 시간
							</c:otherwise>
						</c:choose>
					</td>
					<td class="askTitle"> 
						${trackingContentVo.pkey}
					</td>
				</tr>
				<tr>
					<td class="askTitle">휴가사유</td>
					<td colspan="4">
						<textarea id="vctReason" cols="82" rows="13"></textarea>
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
			<input type="button" value="등록" id="regVctConfirmBtn">
			<input type="button" value="취소" onclick="window.close();">
		</form>
		<input type="hidden" value="${vctVo.eno}" id="eno">
		<input type="hidden" value="${vctVo.id}" id="avctId">
		<input type="hidden" value="${rvctVo.id}" id="rvctId">
		<input type="hidden" value="${vctVo.vctcount}" id="avctCount">
		<input type="hidden" value="${rvctVo.vctcount}" id="rvctCount">
		<input type="hidden" value="${trackingContentVo.site}" id="trackingSite">
		<input type="hidden" value="${trackingContentVo.reason}" id="trackingContent">
		<input type="hidden" value="${trackingContentVo.pkey}" id="trackingPkey">
	</div>
</body>
</html>
