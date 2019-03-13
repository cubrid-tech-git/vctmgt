$(function() {
	$("#typeHelperBtn").click(function() {
		open("./typeHelper.jsp", "", "width=600, height=220");
	});
	
	$("#rvctRegSubmit").click(function() {
		var rvctEname = $("#hiddenEnameVal").val();
		var rvctEno = $("#hiddenEnoVal").val();
		var rvctSiteName = $("#rvctSiteName").val();
		var rvctJiraName = $("#rvctJiraName").val().toUpperCase();
		var rvctStartDate = $("#rvctStartDate").val();
		var rvctEndDate = $("#rvctEndDate").val();
		var rvctStartTime = $("#rvctStartTime").val();
		var rvctEndTime = $("#rvctEndTime").val();
		var rvctStartType = $("#rvctStartType").val();
		var rvctEndType = $("#rvctEndType").val();
		var rvctReason = $("#rvctReason").val();
		var rvctHiddenType = $("#hiddenType").val();

		if(rvctStartTime == "") rvctStartTime = "00:00";
		if(rvctEndTime == "") rvctEndTime = "00:00";

		var beforeMidHour = rvctStartTime.split(":")[0];
		var beforeMidSecond = rvctStartTime.split(":")[1];;
		var afterMidHour = rvctEndTime.split(":")[0];;
		var afterMidSecond = rvctEndTime.split(":")[1];;
		var rvctWorkTime = 0;
		
		// 가중치
		var startWeight = 0;
		var endWeight = 0;
		
		// 계산값을 저장할 변수
		var rvctTempTime = 0;
		
		// 데이터 정합성 검사
		if(rvctSiteName == "" || rvctSiteName == null) {
			alert("사이트 이름을 입력하세요.");
			return false;
		}
		if(rvctJiraName == "" || rvctJiraName == null) {
			alert("트래킹 ID를 입력하세요.");
			return false;
		}
		if(rvctStartDate == "" || rvctStartDate == null) {
			alert("시작 날짜를 입력하세요.");
			return false;
		}
		if(rvctStartTime == "" || rvctStartTime == null) {
			alert("시작 시간을 입력하세요.");
			return false;
		}
		if(rvctEndDate == "" || rvctEndDate == null) {
			alert("종료 날짜를 입력하세요.");
			return false;
		}
		if(rvctEndTime == "" || rvctEndTime == null) {
			alert("종료 시간을 입력하세요.");
			return false;
		}
		if(rvctStartType == "no") {
			alert("시작 구분을 입력하세요.");
			return false;
		}
		if(rvctEndType == "no") {
			alert("종료 구분을 입력하세요.");
			return false;
		}
		if(rvctReason == "" || rvctReason == null) {
			alert("내용을 입력하세요.");
			return false;
		}
		
		// 자정 전에 초과근무 끝 (같은날 시작 및 종료)
		if(rvctStartDate == rvctEndDate) {
			if(rvctStartType != rvctEndType) {
				alert("동일한 날짜에 서로다른 가중치는 적용이 불가능 합니다.");
				return false;
			}
			if(afterMidHour < beforeMidHour || (afterMidHour == beforeMidHour && beforeMidSecond > afterMidSecond)) {
				alert("종료 시간이 시작 시간보다 작을 수는 없습니다.");
				return false;
			}
			if(rvctStartType == "co") {	// 평일 초과근무 : weight 1
				// 시간 : 21:00 ~ 24:00
				startWeight = 1;
				
				if(beforeMidHour < 21) {	// 21시 이전이면 21:00 으로 변경
					beforeMidHour = 21;
					beforeMidSecond = 0;
				}
				if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
					afterMidHour = afterMidHour - 1;
					afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
				} 
			} else if(rvctStartType == "cd") {	// 평일 심야 근무 : weight 1.3
				// 시간 : 00:00 ~ 07:00
				startWeight = 1.3;
				
				if(afterMidHour > 7) {	// 끝나는 시간이 7시 이후면 07:00 으로 변경
					afterMidHour = 7;
					afterMidSecond = 0;
				}
				
				if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
					afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
					afterMidHour = afterMidHour - 1;
				}
			} else if(rvctStartType == "eo") {	// 긴급 초과 근무 : weight 1.3
				// 시간 : 21:00 ~ 24:00
				startWeight = 1.3;
				
				if(beforeMidHour < 21) {	// 21시 이전이면 21:00 으로 변경
					beforeMidHour = 21;
					beforeMidSecond = 0;
				}
				if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
					afterMidHour = afterMidHour - 1;
					afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
				} 
			} else if(rvctStartType == "ed") {	// 긴급 심야 근무 : weight 1.5
				// 시간 : 00:00 ~ 07:00
				startWeight = 1.5;
				
				if(afterMidHour > 7) {	// 끝나는 시간이 7시 이후면 07:00 으로 변경
					afterMidHour = 7;
					afterMidSecond = 0;
				}
				
				if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
					afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
					afterMidHour = afterMidHour - 1;
				}
			} else if(rvctStartType == "ve") {	// 휴일 / 긴급 근무 : weight 1.5
				// 시간 : 00:00 ~ 24:00
				startWeight = 1.5;
				
				if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
					afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
					afterMidHour = afterMidHour - 1;
				}
			} else if(rvctStartType == "ro" || rvctStartType == "rd") {	// 대체 휴가시 : [weight - ro : 1, rd : 1.3]
				// 시간 [ro : 07:00 ~ 24:00], [rd : 00:00 ~ 07:00]
				
				if(afterMidHour >= 0 && afterMidHour <= 7) {	// 대체 심야 근무 : 00:00 ~ 07:00 사이
					startWeight = 1.3;
					
					if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
						afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
						afterMidHour = afterMidHour - 1;
					}
				} else if(beforeMidHour < 7 && afterMidHour > 7) {	// 대체 심야 + 대체 초과 근무 : 00:00 ~ 07:00, 07:00 ~ 24:00
					startWeight = 1;
					
					var tmpBeforeMidHour = 7;
					var tmpBeforeMidSecond = 0;
					
					// 07:00 이전 대체휴가 계산
					if(beforeMidSecond != 0) {
						tmpBeforeMidSecond = parseInt(60);
						tmpBeforeMidHour = 6;
					}
					
					// 00:00 ~ 07:00 계산 결과 임시 변수에 저장
					rvctTempTime = ((tmpBeforeMidHour - beforeMidHour) + ((tmpBeforeMidSecond - beforeMidSecond) / 60)) * parseFloat(1.3);
					
					// 07:00 이후 대체휴가 계산
					beforeMidHour = 7;
					beforeMidSecond = 0;
					
					
				} else if(beforeMidHour >= 7) {
					startWeight = 1;
					
					if(beforeMidSecond > afterMidSecond) {	// 분단위 반올림 처리
						afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
						afterMidHour = afterMidHour - 1;
					}
				}
			} 
			rvctWorkTime = rvctTempTime + ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60)) * parseFloat(startWeight);
		// 자정을 넘어서 까지 초과근무 (다른날 시작 및 종료)
		} else {
			var tmpBeforeMidHour = beforeMidHour;
			var tmpBeforeMidSecond = beforeMidSecond;
			beforeMidHour = 0;
			beforeMidSecond = 0;
			
			if(rvctStartType == "co") {	// 시작 타입이 평일 초과 근무 (21:00 ~ 24:00)
				startWeight = 1;
				
				// 첫 시작 타입 먼저 계산
				if(tmpBeforeMidHour < 21) {	// 21시 이전이면 21:00 으로 변경
					tmpBeforeMidHour = 21;
					tmpBeforeMidSecond = 0;
				}
				if(tmpBeforeMidSecond != 0) {	// 분단위 반올림 처리
					rvctTempTime = (23 - tmpBeforeMidHour) + ((60 - tmpBeforeMidSecond) / 60);
				} else {
					rvctTempTime = (24 - tmpBeforeMidHour);
				}
					
				// 두 번째 타입 계산
				if(rvctEndType == "cd") {	// 다음날 작업이 평일 심야 (00:00 ~ 07:00)
					endWeight = 1.3;
					
					if(afterMidHour > 7) {	// 끝나는 시간이 7시 이후면 07:00 으로 변경
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					
				} else if(rvctEndType == "eo") {
					alert("종료 구분 에러 : 다른날짜에 평일 초과와 긴급 초과 사용 불가");
					return false;
				} else if(rvctEndType == "ed") {	// 다음날 작업이 긴급 심야 (00:00 ~ 07:00)
					endWeight = 1.5;
					
					if(afterMidHour > 7) {	// 끝나는 시간이 7시 이후면 07:00 으로 변경
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					
				} else if(rvctEndType == "ro") {
					alert("종료 구분 에러 : 다른날짜에 평일 초과와 대체 초과 사용 불가");
					return false;
				} else if(rvctEndType == "rd") {	// 다음날 작업이 대체 심야 (00:00 ~ 07:00)
					endWeight = 1.3;
					
					if(afterMidHour > 7) {	// 끝나는 시간이 7시 이후면 07:00 으로 변경
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					
				} else if(rvctEndType == "ve") {	// 다음날 작업이 휴일 / 긴급 (00:00 ~ 24:00)
					endWeight = 1.5;
				} 
			} else if(rvctStartType == "cd") {	// 시작 타입이 평일 심야 근무 (00:00 ~ 07:00)
				startWeight = 1.3;

				alert("시작 구분 에러 : 다른 날짜간 평일 심야 시작");
				return false;
			} else if(rvctStartType == "eo") {	// 시작 타입이 긴급 초과 근무 (21:00 ~ 24:00)
				startWeight = 1.3;

				// 첫 시작 타입 먼저 계산
				
				
				// 두 번째 타입 계산
				
			} else if(rvctStartType == "ed") {	// 시작 타입이 긴급 심야 근무 (00:00 ~ 07:00)
				startWeight = 1.5;

				alert("시작 구분 에러 : 다른 날짜간 긴급 심야 시작");
				return false;
			} else if(rvctStartType == "ro") {	// 시작 타입이 대체 초과 근무 (21:00 ~ 24:00)
				startWeight = 1;

				// 첫 시작 타입 먼저 계산
				
				
				// 두 번째 타입 계산
				
			} else if(rvctStartType == "rd") {	// 시작 타입이 대체 심야 근무 (00:00 ~ 07:00)
				startWeight = 1.3;

				// 첫 시작 타입 먼저 계산
				
				
				// 두 번째 타입 계산
				
			} else if(rvctStartType == "ve") {	// 시작 타입이 휴일 / 긴급 근무 (00:00 ~ 24:00)
				startWeight = 1.5;

				// 첫 시작 타입 먼저 계산
				
				
				// 두 번째 타입 계산
				
			}
			rvctWorkTime = (rvctTempTime * parseFloat(startWeight)) + ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60)) * parseFloat(endWeight);
		}
		
		if(rvctWorkTime.toFixed(1) < 0) rvctWorkTime = 0;
		
		alert("신청자 : " + rvctEname + "\n"
				+ "사번 : " + rvctEno + "\n"
				+ "사이트 : " + rvctSiteName + "\n"
				+ "트래킹 : " + rvctJiraName + "\n"
				+ "시작일 : " + rvctStartDate + " " + rvctStartTime + " (" + rvctStartType + ")\n"
				+ "종료일 : " + rvctEndDate + " " + rvctEndTime + " (" + rvctEndType + ")\n"
				+ "내용 : " + rvctReason + "\n"
				+ "hidden : " + rvctHiddenType + "\n"
				+ "start : " + beforeMidHour + " : " + beforeMidSecond + "\n"
				+ "end   : " + afterMidHour + " : " + afterMidSecond + "\n"
				+ "total : " + rvctWorkTime.toFixed(1) + "\n"
				);
	});
});