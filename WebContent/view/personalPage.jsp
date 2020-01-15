<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.util.CurrentTime"%>
<%@page import="com.cubrid.vo.RVConfirmVo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.dao.UserDaoImpl"%>
<%@page import="com.cubrid.dao.UserDao"%>
<%@page import="com.cubrid.vo.VctVo"%>
<%@page import="com.cubrid.vo.UserVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	UserDao userDao = new UserDaoImpl();
	System.out.println(request.getParameter("eno"));
	int eno = 0;
	String selectComment = "선택하세요";
	UserVo userVo = new UserVo();
	if(Integer.parseInt(request.getParameter("eno")) != 0) {
		eno = Integer.parseInt(request.getParameter("eno"));
		userVo = userDao.selectCubUser(eno).get(0);
		selectComment = userVo.getEname();
		request.setAttribute("ename", selectComment);
		System.out.println("eno : " + userVo.getEno() + "\tename : " + userVo.getEname());
		request.setAttribute("userVo", userVo);
		
		String dnoName = null;
		switch(userVo.getDno()) {
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
			dnoName = "팀장";
			break;
		}
		request.setAttribute("dnoName", dnoName);
		
		VctDaoImpl vctDao = new VctDaoImpl();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("confirmType", "E");
		map.put("confirmEno", eno);
		/*
			map에 저장할 StartQuater의 날짜는 분기마다 다르지만,
			분기 바뀌는 달 (4, 7, 10, 1)의 경우 10일 이후에 처음 달로 바뀌어야 한다.
			근데 이럴 경우 
		*/
		map.put("work_date", CurrentTime.makeStartQuater());
		List<RVConfirmVo> workList = vctDao.selectRvctConfirm(map);
		request.setAttribute("workList", workList);
		
		/*
			작성자 : 2017-01-03(화) 주영진
			내용 : vctDao.selectAllVct(eno) 값이 null이 나올 때 발생하는 오류 수정

			VctVo pVctVo = vctDao.selectAllVct(eno).get(0);
		*/
		VctVo pVctVo = null;
		if  (vctDao.selectAllVct(eno).size() > 0) {
			pVctVo = vctDao.selectAllVct(eno).get(0);
		}
		/* END */

		request.setAttribute("pVctVo", pVctVo);
		
		List<VConfirmVo> vList = vctDao.selectVctConfirmList(eno);
		request.setAttribute("vList", vList);
	}
	request.setAttribute("eno", userVo.getEno());
	request.setAttribute("selectComment", selectComment);
	
	List<UserVo> userList = userDao.selectCubUser(0);
	request.setAttribute("userList", userList);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.modal.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.bpopup.min.js"></script>
<style>
#hiddenFrame {
	display: none;
}
 
.Pstyle {
    opacity: 0;
    display: none;
    position: relative;
    width: auto;
    border: 5px solid #fff;
    padding: 20px;
    background-color: #fff;
}
 
.b-close {
    display: inline-block;
    cursor: pointer;
}
</style>
<script>
	$(document).ready(function() {
		$("#selectName").change(function() {
			var eno = $("#selectName").val();
			$("#tabs-2").load("view/personalPage.jsp?eno=" + eno);
		});
		
		$("#vctRegSubmit").click(function() {
			alert("click");
		});

		$("#mgtEmail").click(function() {
			var eno = $(this).val();
			open("popup/emailMgt.jsp?eno=" + eno, "", "width=300, height=190");
		});

		$(".vctStatusUpdate").click(function() {
			var id = $(this).val();
			open("popup/regVctUpdate.jsp?id=" + id, "", "width=905, height=380");
		});

		$(".vctStatusDel").click(function() {
			var id = $(this).val();
			var now = new Date();
			var fromDate = new Date($(this).attr("title"));
			if(now < fromDate) {
				var check = confirm("삭제하시겠습니까?");
				if(check) {
					open("popup/vctDelReason.jsp?id=" + id, "", "width=520, height=330");		
				} else {
					return false;
				}
			} else {
				alert("이미 사용한 휴가는 삭제가 불가능 합니다.");
			}
		});
	});
</script>
</head>
<body>
	<div id="selectArea">
		<c:choose>
			<c:when test="${selectComment ne '선택하세요'}">
				<button id="mgtEmail" value="${eno}">email PW 설정</button>
				<button id="askVacationBtn" onclick="regVct('${eno}');">휴가 신청</button>
			</c:when>
			<c:otherwise>
				사용자 : 
			</c:otherwise>
		</c:choose>
		<select id="selectName">
			<option value="${eno}">${selectComment}</option>
			<c:forEach var="user" items="${userList}">
				<option value="${user.eno}">${user.ename}</option>
			</c:forEach>
		</select>
	</div>
	
	<h2>남은 휴가 정보</h2>
	<table class="infoTbl">
		<tr class="titleTr">
			<td class="titleTd" style="width:440px;height:30px">연차 휴가 (일)</td>
			<td class="titleTd" style="width:440px;height:30px">대체 휴가 (시간)</td>
		</tr>
		<tr class="contentTr">
			<td class="contentTd" style="width:440px;height:30px">${pVctVo.vctcount}</td>
			<td class="contentTd" style="width:440px;height:30px">${pVctVo.vctcount_tmp}</td>
		</tr>
	</table>
	
	<br>
	
	<div id="rvctBtnArea">
		<c:if test="${selectComment ne '선택하세요'}">
			<button id="rvctRegBtn" onclick="rvctRegBtnFn('${eno}')">발생내역 등록 신청</button>
		</c:if> 
	</div>
		
	<h2>대체휴가 발생 내역</h2>
	<table class="infoTbl">
		<tr class="titleTr">
			<td class="trackingTd" style="width:130px;height:30px">트래킹 ID</td>
			<td class="trackingTd" style="width:60px;height:30px">사이트</td>
			<td class="trackingTd" style="width:100px">시작 시일</td>
			<td class="trackingTd" style="width:100px">종료 시일</td>
			<td class="trackingTd" style="width:30px">시간</td>
			<td class="trackingTd">내용</td>
			<td class="trackingTd" style="width:100px">등록일</td>
			<td class="trackingTd" style="width:40px">상태</td>
			<td class="trackingTd" style="width:50px">삭제</td>
		</tr>
		<c:forEach var="work" items="${workList}">
			<tr class="contentTr">
				<td class="contentTd" style="height:30px">
				<c:choose>
					<c:when test="${dnoName == '큐브리드'}">
						<a href="http://www.cubrid.com:8888/browse/${work.pkey}" target=_new>${work.pkey}</a>
					</c:when>
					<c:otherwise>
						<a href="http://jira.cubrid.com:8888/browse/${work.pkey}" target=_new>${work.pkey}</a>
					</c:otherwise>
				</c:choose>
				</td>
				<td class="contentTd">${work.site}</td>
				<td class="contentTd">${work.work_date}</td>
				<td class="contentTd">${work.work_end_date}</td>
				<td class="contentTd">${work.work_time}</td>
				<td class="contentTd">${work.reason}</td>
				<td class="contentTd">${work.regdate}</td>
					<c:choose>
						<c:when test="${work.status == 'Y'}">
							<td class="contentTd">승인</td>
						</c:when>
						<c:when test="${work.status == 'W'}">
							<td class="contentTd">대기</td>
						</c:when>
						<c:when test="${work.status == 'N'}">
							<td class="contentTd">거절</td>
						</c:when>															
					</c:choose>
				
				<td class="contentTd">
					<c:if test="${work.status eq 'W'}">
						<input type="button" value="수정" onclick="rvctConfirmUpdate('${work.id}', '${work.status}')"/>
						<input type="button" value="삭제" onclick="rvctConfirmDel('${work.id}')"/>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<br>
	
	<h2>휴가 신청 현황</h2>
	<table class="infoTbl">
		<tr class="titleTr">
			<td class="trackingTd" style="width: 100px; height: 30px">구분</td>
			<td class="trackingTd" style="width: 100px">상태</td>
			<td class="trackingTd" style="width: 200px">휴가 시작 날짜</td>
			<td class="trackingTd" style="width: 200px">휴가 종료 날짜</td>
			<td class="trackingTd">휴가 시간</td>
			<td class="trackingTd" style="width: 120px">수정/삭제</td>
		</tr>
		
		<c:forEach var="vctList" items="${vList}">
			<tr class="contentTr">
				<c:choose>
					<c:when test="${vctList.vct_type == 'A'}">
						<td class="contentTd">연차</td>
					</c:when>
					<c:when test="${vctList.vct_type == 'R'}">
						<td class="contentTd">대체</td>
					</c:when>
					<c:when test="${vctList.vct_type == 'S'}">
						<td class="contentTd">경조</td>
					</c:when>
					<c:when test="${vctList.vct_type == 'C'}">
						<td class="contentTd">복합</td>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${vctList.status == 'Y'}">
						<td class="contentTd">승인</td>
					</c:when>
					<c:when test="${vctList.status == 'W'}">
						<td class="contentTd">대기</td>
					</c:when>
					<c:when test="${vctList.status == 'N'}">
						<td class="contentTd">거절</td>
					</c:when>
				</c:choose>
				<td class="contentTd">${vctList.from_vctdate}</td>
				<td class="contentTd">${vctList.to_vctdate}</td>
				<c:choose>
					<c:when test="${vctList.vct_type == 'A'}">
						<td class="contentTd">${vctList.vct_time} 일</td>					
					</c:when>
					<c:when test="${vctList.vct_type == 'R'}">
						<td class="contentTd">${vctList.vct_time} 시간</td>					
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${vctList.status == 'W'}">
						<td class="contentTd">
							<button value="${vctList.id}" title="${vctList.status}" class="vctStatusUpdate">수정</button>
							<button value="${vctList.id}" title="2222-12-31" class="vctStatusDel">삭제</button>
						</td>		
					</c:when>
					<c:when test="${vctList.status == 'Y'}">
						<td class="contentTd">
							<button value="${vctList.id}" title="${vctList.from_vctdate}" class="vctStatusDel">삭제</button>
						</td>		
					</c:when>
				</c:choose>
			</tr>					
		</c:forEach>
	</table>
	<br>
	
	<div id="hiddenFrame"></div>
	<input id="hiddenEno" type="hidden" value="${eno}"/>
	<input id="hiddenEname" type="hidden" value="${ename}"/>
</body>
</html>
