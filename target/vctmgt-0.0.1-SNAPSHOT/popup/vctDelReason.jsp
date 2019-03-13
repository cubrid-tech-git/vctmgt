<%@page import="com.cubrid.vo.VConfirmVo"%>
<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@page import="com.cubrid.dao.VctDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	VctDao vctDao = new VctDaoImpl();
	VConfirmVo vVo = vctDao.selectVctConfirmListById(id);
	request.setAttribute("vVo", vVo);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴가 삭제 사유</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.modal.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/site.js"></script>
<style type="text/css">
#reasonContainer {
	text-align: center;
	width: 500;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#delSubmit").click(function() {
			$("#delForm").submit();
		});

		$("#delCancel").click(function() {
			window.close();
		});
	});
</script>
</head>
<body>
	<div id="reasonContainer">
		<form action="../controller/vctDelController.jsp" id="delForm" method="post">
			<table class="infoTbl" style="width: 500;">
				<tr class="titleTr">
					<td class="titleTd" style="width: 125; height: 30;">구분</td>
					<td class="titleTd" style="width: 250; height: 30;">휴가 기간</td>
					<td class="titleTd" style="width: 125; height: 30;">휴가 시간</td>
				</tr>
				<tr class="contentTr">
					<c:choose>
						<c:when test="${vVo.vct_type == 'A'}">
							<td class="contentTd">연차</td>
						</c:when>
						<c:when test="${vVo.vct_type == 'R'}">
							<td class="contentTd">대체</td>
						</c:when>
						<c:when test="${vVo.vct_type == 'S'}">
							<td class="contentTd">경조</td>
						</c:when>
						<c:when test="${vVo.vct_type == 'C'}">
							<td class="contentTd">복합</td>
						</c:when>
					</c:choose>
					<td class="contentTd">${vVo.from_vctdate} ~ ${vVo.to_vctdate}</td>
					<c:choose>
						<c:when test="${vVo.vct_type == 'R'}">
							<td class="contentTd">${vVo.vct_time} 시간</td>
						</c:when>
						<c:otherwise>
							<td class="contentTd">${vVo.vct_time} 일</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
			<hr>
			<table class="infoTbl" style="width: 500;">
				<tr class="titleTr" style="height: 30">
					<td class="titleTd">삭제 사유</td>
				</tr>
				<tr class="contentTr">
					<td class="contentTd">
						<textarea rows="10" cols="68" id="delReason" name="delReason"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<button id="delSubmit">확인</button>
			<button id="delCancel">취소</button>
			<input type="hidden" name="id" value="${vVo.id}">
		</form>
	</div>
</body>
</html>