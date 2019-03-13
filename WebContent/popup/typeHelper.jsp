<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>구분 설명</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css">
<script charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/jquery-1.10.2.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<table class="infoTbl" style="width: 585px">
		<tr>
			<td class="askTitle" >구분 명</td>
			<td class="askTitle" >시간</td>
			<td class="askTitle" >가중치</td>
		</tr>
		<tr>
			<td class="contentTd">평일-초과</td>
			<td class="contentTd">21:00 ~ 24:00</td>
			<td class="contentTd">1</td>
		</tr>
		<tr>
			<td class="contentTd">평일-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.3</td>
		</tr>
		<tr>
			<td class="contentTd">평일긴급-초과</td>
			<td class="contentTd">21:00 ~ 24:00</td>
			<td class="contentTd">1.3</td>
		</tr>
		<tr>
			<td class="contentTd">평일긴급-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.5</td>
		</tr>
		<tr>
			<td class="contentTd">대체휴가</td>
			<td class="contentTd">07:00 ~ 24:00</td>
			<td class="contentTd">1</td>
		</tr>
		<tr>
			<td class="contentTd">대체휴가-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.3</td>
		</tr>
		<tr>
			<td class="contentTd">휴일/긴급</td>
			<td class="contentTd">00:00 ~ 24:00</td>
			<td class="contentTd">1.5</td>
		</tr>
	</table>
</body>
</html>