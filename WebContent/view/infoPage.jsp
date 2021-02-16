<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<String> dateList = CurrentTime.dateList();
	request.setAttribute("dateList", dateList);
	
	// select 태그에서 default 및 파라미터로 받은 값들을 저장하는 부분
	String curYearMonth = request.getParameter("yearMonth");
	String curCostQuater = request.getParameter("yearCostMonth");
	
	if(curYearMonth == null) {
		curYearMonth = CurrentTime.getTime().substring(0, 7);
	}
	if(curCostQuater == null) {
		curCostQuater = CurrentTime.makeKorQuater();
	}
	request.setAttribute("curYearMonth", curYearMonth);
	request.setAttribute("curCostQuater", curCostQuater);
	
	// 대체휴가 정산 정보의 select 태그에서 2015년도부터 현재 년도까지 분기별 option을 생성하는 부분
	List<String> costOptionList = CurrentTime.quaterToKor(CurrentTime.makeEndQuater());
	request.setAttribute("costOptionList", costOptionList);
	
	
	VctDaoImpl vctDao = new VctDaoImpl();
	// 휴가 사용 현황의 DB 데이터 불러오기
	List<VConfirmVo> vctInfoList = vctDao.selectConfirmedVctInfo(curYearMonth);
	request.setAttribute("vctInfoList", vctInfoList);
	// 대체휴가 정산 정보의 DB 데이터 불러오기
	List<VctVo> costList = vctDao.selectRvctCostInfo(CurrentTime.korToQuater(curCostQuater));
	request.setAttribute("costList", costList);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		
		$("#selectYearMonth").change(function() {
			var infoVal = $("#selectYearMonth").val();
			var costVal = $("#selectCostYearMonth").val(); 
			$("#tabs-3").load("view/infoPage.jsp?yearMonth=" + infoVal + "&yearCostMonth=" + costVal);
		});

		$("#selectCostYearMonth").change(function() {
			var costVal = $("#selectCostYearMonth").val();
			var infoVal = $("#selectYearMonth").val();
			$("#tabs-3").load("view/infoPage.jsp?yearMonth=" + infoVal + "&yearCostMonth=" + costVal);
		});
	});
</script>
</head>
<body>
	<div id="selectArea">
		<select id="selectYearMonth" onchange="">
			<option value="${curYearMonth}">${curYearMonth}</option>
			<c:forEach var="YearMonth" items="${dateList}">
				<option value="${YearMonth}">${YearMonth}</option>
			</c:forEach>
		</select>
	</div>
	<div></div>
	<h2>휴가 사용 현황</h2>
	<table class="infoTbl" id="historyTbl" style="width: 880px">
		<tr class="titleTr">
			<td class="titleTd" style="width:120px;height:30px">이름</td>
			<td class="titleTd">휴가 구분</td>
			<td class="titleTd">사용 시간</td>
			<td class="titleTd" style="width:220px;">휴가 시작</td>
			<td class="titleTd" style="width:220px;">휴가 종료</td>
		</tr>
		<c:forEach var="infoList" items="${vctInfoList}">
			<tr class="contentTr">
				<td class="contentTd" style="height:30px" title="${infoList.reason}">${infoList.ename}</td>
				<c:choose>
					<c:when test="${infoList.vct_type == 'R'}">
						<td class="contentTd" title="${infoList.reason}">대체휴가</td>
						<td class="contentTd">${infoList.vct_time} H</td>
					</c:when>
					<c:otherwise>
						<td class="contentTd" title="${infoList.reason}">연차휴가</td>
						<td class="contentTd">${infoList.vct_time} D</td>
					</c:otherwise>
				</c:choose>
				<td class="contentTd">${infoList.from_vctdate}</td>
				<td class="contentTd">${infoList.to_vctdate}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<div id="selectCostArea">
		<select id="selectCostYearMonth">
			<option value="${curCostQuater}">${curCostQuater}</option>
			<c:forEach var="costOptionList" items="${costOptionList}">
				<option value="${costOptionList}">${costOptionList}</option>
			</c:forEach>
		</select>
	</div>
	<h2>대체휴가 정산 정보</h2>
	<table class="infoTbl" id="rewardTbl" style="width: 880px">
		<tr class="titleTr">
			<td class="titleTd" style="width:150px;height:30px">부서명</td>
			<td class="titleTd" style="width:150px">이름</td>
			<td class="titleTd">대체휴가 시간</td>
			<td class="titleTd">대체휴가 수당</td>
			<td class="titleTd">상세보기</td>
		</tr>
		<c:forEach var="costTblList" items="${costList}">
			<tr class="contentTr">
				<td class="contentTd">
					<c:choose>
						<c:when test="${costTblList.dno == 401}">
							기술지원 1팀
						</c:when>
						<c:when test="${costTblList.dno == 402}">
							기술지원 2팀
						</c:when>
						<c:when test="${costTblList.dno == 411}">
							대전사무소
						</c:when>
						<c:when test="${costTblList.dno == 301}">
							개발 1팀
						</c:when>
						<c:when test="${costTblList.dno == 302}">
							개발 2팀
						</c:when>
						<c:when test="${costTblList.dno == 303}">
							개발 3팀
						</c:when>
						<c:when test="${costTblList.dno == 311}">
							QA팀
						</c:when>
						<c:when test="${costTblList.dno == 100}">
							<c:choose>
								<c:when test="${costTblList.ename == '엄기호'}">
									기술지원 1팀장
								</c:when>
								<c:when test="${costTblList.ename == '권호일'}">
									기술지원 2팀장
								</c:when>
								<c:when test="${costTblList.ename == '남재우'}">
									대전사무소장
								</c:when>
								<c:when test="${costTblList.ename == '한기수'}">
									개발 1팀장
								</c:when>
								<c:when test="${costTblList.ename == '민준'}">
									개발 2팀장
								</c:when>
								<c:when test="${costTblList.ename == '김병욱'}">
									개발 3팀장
								</c:when>
								<c:when test="${costTblList.ename == '손승일'}">
									QA팀장
								</c:when>
								<c:when test="${costTblList.ename == '오명환'}">
									전체관리자
								</c:when>
							</c:choose>
						</c:when>
					</c:choose>
				</td>
				<td class="contentTd">${costTblList.ename}</td>
				<td class="contentTd">${costTblList.vctcount} H</td>
				<td class="contentTd">${costTblList.cost}</td>
				<td class="contentTd">
					<input type="button" value="보기" onclick="showDetail('${costTblList.eno}', '${curCostQuater}')" />
				</td>
			</tr>		
		</c:forEach>
	</table>
	
</body>
</html>
