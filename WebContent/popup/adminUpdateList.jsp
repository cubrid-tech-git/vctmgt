<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int vctId = Integer.parseInt(request.getParameter("vctId"));
	float vctCount = Float.parseFloat(request.getParameter("vctCount"));
	float originVctCount = Float.parseFloat(request.getParameter("originVctCount"));
	float updateAvlCount = Math.round( (originVctCount - vctCount) * 100f ) / 100f;
	System.out.println("vctId : " + vctId + "\nvctCount : " + vctCount + "\noriginVctCount : " + originVctCount + "\nupdateAvlCount : " + updateAvlCount);
	String vctType = request.getParameter("vctType");
	request.setAttribute("updateAvlCount", updateAvlCount);
	request.setAttribute("vctType", vctType);
	request.setAttribute("vctCount", vctCount);
	request.setAttribute("originVctCount", originVctCount);
	VctDao vctDao = new VctDaoImpl();
	List<VConfirmVo> vList = vctDao.adminPageUpdateVctConfirmList(vctId);
	int listSize = vList.size();
	request.setAttribute("vList", vList);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>admin update page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.modal.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#adminUpdate").click(function() {
			var vctId = <%=vctId%>;
			var vctcount = <%=vctCount%>;
			var listSize = <%=listSize%>;
			var avlCount = <%=updateAvlCount%>;
			var count = 0;
			var vctConfirmId = 0;
			var vctConfirmCount = 0;
			var fromDate = "";
			var toDate = "";
			
			for(i = 0; i < listSize; i++) {
				var toBeId = "#updatedVctCount" + i;
				var asIsId = "#originVctCount" + i;
				var vctConfirmIdId = "#vctConfirmId" + i; 
				var fromDateId = "#fromDate" + i;
				var toDateId = "#toDate" + i;
				var toBe = $(toBeId).val();
				var asIs = $(asIsId).val();
				fromDate = $(fromDateId).val();
				toDate = $(toDateId).val();
				vctConfirmId = $(vctConfirmIdId).val();
				count = Math.round((toBe - asIs) * 100) / 100;
				
				if(count != 0) {
					vctConfirmCount = toBe;
					break;
				}
			}
		
			if(count != avlCount) {
				alert("수정하려는 휴가 일(시간)이 일치하지 않습니다.\n수정 가능 일(시간) : " + avlCount + "\n수정한 일(시간) : " + count);
				location.reload();
			} else {
				//alert("수정!\nvctId : " + vctId + "\nvctcount : " + vctcount + "\nvctConfirmId : " + vctConfirmId + "\nvctConfirmCount : " + vctConfirmCount + "\ndate : " + fromDate + "~" + toDate );
				location.href="../controller/adminUpdateListController.jsp?vctId=" + vctId + "&vctcount=" + vctcount + "&vctConfirmId=" + vctConfirmId + "&vctConfirmCount=" + vctConfirmCount + "&fromDate=" + fromDate + "&toDate=" + toDate;
			}
		});
	});
</script>
</head>
<body>
	<h3>
		<c:choose>
			<c:when test="${vctType eq 'A'}">
				반영할 연차휴가 일자 : ${updateAvlCount}			
			</c:when>
			<c:when test="${vctType eq 'R'}">
				반영할 대체휴가 시간 : ${updateAvlCount}
			</c:when>
		</c:choose>
	</h3>
	<div id="tblDiv" style="text-align: center;width: 600px">
	<table class="infoTbl" style="width: 600px">
		<tr class="titleTr">
			<td class="titleTd" style="width: 60px">이름</td>
			<td class="titleTd" style="width: 80px">휴가 구분</td>
			<td class="titleTd" style="width: 50px">상태</td>
			<td class="titleTd" style="width: 180px">휴가 기간</td>
			<td class="titleTd" style="width: 60px">시간/일</td>
			<td class="titleTd" style="width: 170px">내용</td>
		</tr>
		<c:set var="index" value="0" />
		<c:forEach var="list" items="${vList}">
			<tr class="contentTr">
				<td class="contentTd">${list.ename}</td>
				<td class="contentTd">
					<c:choose>
						<c:when test="${list.vct_type eq 'A'}">
							연차휴가
						</c:when>
						<c:when test="${list.vct_type eq 'R'}">
							대체휴가
						</c:when>
					</c:choose>
				</td>
				<td class="contentTd">
					<c:choose>
						<c:when test="${list.status eq 'W'}">
							승인대기
						</c:when>
						<c:when test="${list.status eq 'Y'}">
							승인
						</c:when>
						<c:when test="${list.status eq 'N'}">
							미승인
						</c:when>
					</c:choose>
				</td>
				<td class="contentTd">
					시작: <input type="date" value="${list.from_vctdate}" id="fromDate${index}"/><br>
					종료: <input type="date" value="${list.to_vctdate}" id="toDate${index}"/>
				</td>
				<td class="contentTd">
					<input type="number" value="${list.vct_time}" id="updatedVctCount${index}" style="width: 55px">
					<input type="hidden" value="${list.vct_time}" id="originVctCount${index}">
					<input type="hidden" value="${list.id}" id="vctConfirmId${index}">
				<td class="contentTd">${list.reason}</td>
			</tr>
			<c:set var="index" value="${index + 1}"/>
		</c:forEach>
	</table>
	<font color="red">* 주의 : 하나의 항목만 수정이 가능합니다.</font>
	<br>
	<input type="button" id="adminUpdate" value="수정">
	<input type="button" value="닫기" onclick="window.close()">
	</div>
</body>
</html>