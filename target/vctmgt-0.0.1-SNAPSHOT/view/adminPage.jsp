<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int adminEno = Integer.parseInt(session.getAttribute("adminEno").toString());
	request.setAttribute("adminEno", adminEno);	

	UserDaoImpl userDao = new UserDaoImpl();
	List<UserVo> list = userDao.selectCubUser(adminEno);
	UserVo vo = list.get(0);
	String adminEname = vo.getEname();
	request.setAttribute("adminEname", adminEname);
	System.out.println("adminEname : " + adminEname);
	
	VctDaoImpl vctDao = new VctDaoImpl();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("confirmType", "M");
	map.put("confirmEno", adminEno);
	map.put("work_date", CurrentTime.makeStartQuater());
	List<RVConfirmVo> workList = vctDao.selectRvctConfirm(map);
	request.setAttribute("workList", workList);
	
	List<VConfirmVo> vctList = vctDao.selectVctConfirmForAdmin(adminEno);
	request.setAttribute("vctList", vctList);
	
	// 휴가 수정을 위한 휴가 리스트 출력
	Map<String, String> dateMap = new HashMap<String, String>();
	dateMap.put("yearStartDate", CurrentTime.startYear());
	dateMap.put("yearEndDate", CurrentTime.endYear());
	dateMap.put("quaterStartDate", CurrentTime.makeStartQuater());
	dateMap.put("quaterEndDate", CurrentTime.makeEndQuater());
	List<VctVo> adminVctMgtList = vctDao.adminPageSelectAllVctList(dateMap);
	request.setAttribute("adminVctMgtList", adminVctMgtList);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
</head>
<body>
	<div id="selectArea">
		<button id="userMgt" onclick="userMgt()">직원 관리</button>
	</div>
	<h2>휴가 승인 요청</h2>
	<table class="infoTbl" id="historyTbl">
		<tr class="titleTr">
			<td class="titleTd" style="width:60px">구분</td>
			<td class="titleTd" style="width:60px">이름</td>
			<td class="titleTd" style="width:150px">휴가 기간</td>
			<td class="titleTd" style="width:100px">사용 시간</td>
			<td class="titleTd">내용</td>
			<td class="titleTd" style="width:60px">확인</td>
		</tr>
		<c:forEach var="vctConfirmList" items="${vctList}">
		<tr class="contentTr">
			<c:choose>
				<c:when test="${vctConfirmList.vct_type == 'A'}">
					<td class="contentTd">연차</td>
				</c:when>
				<c:when test="${vctConfirmList.vct_type == 'R'}">
					<td class="contentTd">대체</td>
				</c:when>
				<c:when test="${vctConfirmList.vct_type == 'C'}">
					<td class="contentTd">연차<br>대체</td>
				</c:when>
				<c:when test="${vctConfirmList.vct_type == 'S'}">
					<td class="contentTd">경조<br>특별</td>
				</c:when>
			</c:choose>
			<td class="contentTd">${vctConfirmList.ename}</td>
			<td class="contentTd"> ${vctConfirmList.from_vctdate} ~ <br> ${vctConfirmList.to_vctdate} </td>
			<c:choose>
				<c:when test="${vctConfirmList.vct_type == 'A'}">
					<td class="contentTd">${vctConfirmList.vct_time} 일</td>
				</c:when>
				<c:otherwise>
					<td class="contentTd">${vctConfirmList.vct_time} 시간</td>
				</c:otherwise>
			</c:choose>
			<td class="contentTd">${vctConfirmList.reason}</td>
			<td class="contentTd">
				<button onclick="vctConfirmFn('${vctConfirmList.id}', ${vctConfirmList.vct_id}, ${vctConfirmList.vct_time}, ${vctConfirmList.eno})">승인</button>
			</td>
		</tr>
		</c:forEach>
	</table>
	<br>
	<div id="confirmPastRvctBtnArea">
		<button id="rvctRegBtn" onclick="showPastRvct(${adminEno})">미승인 전체 내역</button>
	</div>
	<h2>대체휴가 승인 요청</h2>
	<table class="infoTbl" id="historyTbl">
		<tr class="titleTr">
			<td class="titleTd" style="width: 55px;">이름</td>
			<td class="titleTd" style="width: 130px;">트래킹 ID</td>
			<td class="titleTd" style="width: 100px;">사이트</td>
			<td class="titleTd" style="width: 90px;">시작 시일</td>
			<td class="titleTd" style="width: 90px;">종료 시일</td>
			<td class="titleTd" style="width: 35px;">시간</td>
			<td class="titleTd">내용</td>
			<td class="titleTd" style="width: 60px;">확인</td>
		</tr>
		<c:set var="index" value="0"/>
		<c:forEach var="work" items="${workList}">
			<tr class="contentTr">
				<td class="contentTd">${work.ename}</td>
				<td class="contentTd"><a href="http://www.cubrid.com:8888/browse/${work.pkey}">${work.pkey}</a></td>
				<td class="contentTd">${work.site}</td>
				<td class="contentTd">${work.work_date}</td>
				<td class="contentTd">${work.work_end_date}</td>
				<td class="contentTd">${work.work_time}</td>
				<td class="contentTd">${work.reason}</td>
				<td class="contentTd">
					<button class="rvctConfirmBtn" onclick="rvctConfirmFn('${work.id}', '${work.work_time}', '${work.eno}')">승인</button>
					<button class="rvctCancelBtn" onclick="rvctCancelFn('${work.id}', '${work.eno}')">삭제</button>
				</td>
				<c:set var="index" value="${index+1}" />
			</tr>
		</c:forEach>
	</table>
	<br>
	<div id="mgtCheck">
		<input type="radio" id="checkVal" name="checkVal" value="0" checked="checked"> 사용 내역 함께 수정
		<br> 
		<input type="radio" id="checkVal" name="checkVal" value="1"> 사용 내역 제외 수정
	</div>
	<h2>휴가 관리</h2>
	<table class="infoTbl" id="historyTbl">
		<tr class="titleTr">
			<td class="titleTd" style="width:130px">이름</td>
			<td class="titleTd" style="width:130px">구분</td>
			<td class="titleTd">기간</td>
			<td class="titleTd" style="width:180px">수정 / 잔량</td>
			<td class="titleTd" style="width:60px">수정</td>
		</tr>
		<c:set var="index" value="1"/>
		<c:forEach var="avmList" items="${adminVctMgtList}">
			<tr class="contentTr">
				<c:choose>
					<c:when test="${index % 2 eq 1}">
						<td class="contentTd" rowspan="2">${avmList.ename}</td>
						<td class="contentTd">연차휴가</td>
					</c:when>
					<c:otherwise>
						<td class="contentTd">대체휴가</td>
					</c:otherwise>
				</c:choose>
				<td class="contentTd">${avmList.regdate} ~ ${avmList.enddate}</td> 
				<td class="contentTd">
					<input type="text" id="vctCount${index}" value="${avmList.vctcount}" size="2"> / 
					<input type="text" id="originVctCount${index}" value="${avmList.vctcount}" size="2" disabled="disabled"></td>
				<td class="contentTd">
					<input type="button" value="수정" onclick="updateVctByAdmin(${avmList.id}, ${index})">
				</td>
			</tr>
			<c:if test="${index % 2 eq 0 }">
				<tr><td colspan="5"></td></tr>
			</c:if>
			<c:set var="index" value="${index + 1}"/>
		</c:forEach>
	</table>
	<br>
</body>
</html>