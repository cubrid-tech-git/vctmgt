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

<b>※ 구분 1번째 선택 : 시작일자에 적용</b>
<br/>
<b>※ 구분 2번째 선택 : 종료일자에 적용</b>
<br/>
<b style="color:red">※ 일반적으로 초과근무 하는 경우에는 구분을 "심야"로 선택하면 됩니다.</b>
<hr/>
<br/>

<body>
	<table class="infoTbl" style="width: 100%">
		<tr>
			<td class="askTitle" >구분 명</td>
			<td class="askTitle" >시간</td>
			<td class="askTitle" >가중치</td>
			<td class="askTitle" style="width: 50%" >상세 내용</td>
		</tr>
		<tr>
			<td class="contentTd">평일-초과</td>
			<td class="contentTd">19:00 ~ 24:00</td>
			<td class="contentTd">1</td>
			<td class="contentTd" style="text-align:left">평일 초과근무를 할 경우 선택</td>
		</tr>
		<tr>
			<td class="contentTd">평일-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.3</td>
			<td class="contentTd" style="text-align:left">평일 심야근무를 할 경우 선택 <br/>(07시부터는 정상근무이므로 계산에서 제외)</td>
		</tr>
		<tr>
			<td class="contentTd">평일긴급-초과</td>
			<td class="contentTd">19:00 ~ 24:00</td>
			<td class="contentTd">1.3</td>
			<td class="contentTd" style="text-align:left">평일 긴급하게 근무를 할 경우 선택</td>
		</tr>
		<tr>
			<td class="contentTd">평일긴급-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.5</td>
			<td class="contentTd" style="text-align:left">평일 긴급하게 근무를 할 경우 선택<br/>(07시부터는 정상근무이므로 계산에서 제외)</td>
		</tr>
		<tr>
			<td class="contentTd">대체휴가</td>
			<td class="contentTd">07:00 ~ 24:00</td>
			<td class="contentTd">1</td>
			<td class="contentTd" style="text-align:left">대체휴가 또는 연차 사용하는 중에 근무가 발생하는 경우 선택</td>
		</tr>
		<tr>
			<td class="contentTd">대체휴가-심야</td>
			<td class="contentTd">00:00 ~ 07:00</td>
			<td class="contentTd">1.3</td>
			<td class="contentTd" style="text-align:left">대체휴가 또는 연차 사용하는 중에 근무가 발생하는 경우 선택<br/>(07시부터는 정상근무이므로 계산에서 제외)</td>
		</tr>
		<tr>
			<td class="contentTd">휴일/긴급</td>
			<td class="contentTd">00:00 ~ 24:00</td>
			<td class="contentTd">1.5</td>
			<td class="contentTd" style="text-align:left">휴일에 근무할 경우 선택</td>
		</tr>
	</table>
	<hr/>
	<br/>
	<b>※예시)</b><br/>
	<b>1) 평일 초과, 심야 근무를 했을 경우</b>
	<ul>
		<li>시작일 : 2020년 5월 26일(화) 18:30</li>
		<li>종료일 : 2020년 5월 27일(수) 3:00</li>
		<li>구분 1번째 : 심야</li>
		<li>구분 2번쨰 : 심야</li>
		<li>결과 : 8.9시간</li>
		<li>설명 : 5시간(19:00 ~ 24:00) + 3.9시간(00:00 ~ 03:00, 3시간 * 1.3) = 8.9시간</li>
	</ul>
	<b>2) 금요일 ~ 토요일 근무를 했을 경우</b>
	<ul>
		<li>시작일 : 2020년 5월 22일(금) 18:30</li>
		<li>종료일 : 2020년 5월 23일(토) 9:00</li>
		<li>구분 1번째 : 심야</li>
		<li>구분 2번쨰 : 휴일/긴급</li>
		<li>결과 : 18.5시간</li>
		<li>설명 : 5시간(19:00 ~ 24:00) + 13.5시간(00:00 ~ 09:00, 9시간 * 1.5) = 18.5시간</li>
	</ul>	
	<b>3) 일요일 ~ 월요일 근무를 했을 경우</b>
	<ul>
		<li>시작일 : 2020년 5월 24일(일) 22:00</li>
		<li>종료일 : 2020년 5월 25일(월) 9:00</li>
		<li>구분 1번째 : 휴일/긴급</li>
		<li>구분 2번쨰 : 심야</li>
		<li>결과 : 12.1시간</li>
		<li>설명 : 3시간(22:00 ~ 24:00, 2시간 * 1.5) + 9.1시간(00:00 ~ 09:00, 7시간 * 1.3) = 12.1시간 <br/> <span style="color:blue">※ 월요일은 정상 근무로 오전 7시 까지 대체휴가가 발생되며, 2시간은 근무한 것임으로 6시간 대체휴가를 사용하면 월요일 휴가가 인정 됩니다.</span></li>
	</ul>	
</body>
</html>