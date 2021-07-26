$(function() {
	$("#tab1").click(function() {
		$("#tabs-1").load("view/summaryPage.jsp");
	});

	$("#tab2").click(function() {
		$("#tabs-2").load("view/personalPage.jsp?eno=0");
	});

	$("#tab3").click(function() {
		$("#tabs-3").load("view/infoPage.jsp");
	});
	
	$("#tab4").click(function() {
		open("./popup/adminLoginCheck.jsp", "", "width=260, height=170, location=no");
		//$("#tabs-4").load("view/adminPage.jsp");
	});
	
	$("#typeHelperBtn").click(function() {
		open("./typeHelper.jsp", "", "width=600, height=220");
	});
	
	$("#rvctSubmit").click(function() {
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

		// 대체휴가 시간 계산 (시작 / 끝 비교)
		// 같은날, 즉 20:00 ~ 24:00 이전 종료
		// 2021-08-01 부터 19:00 ~ 24:00 이전 종료로 변경.
		if(rvctStartDate == rvctEndDate) {
			// 평일 심야일 경우
			
			if(rvctStartType != rvctEndType) {
				alert("시작 시간과 종료 시간이 같은 날일 경우 구분이 서로 다를 수 없습니다.");
				return false;
			}
			
			if(rvctStartType == "ni") {
				if(afterMidHour > 19) {
					if(beforeMidHour < 19) {
						beforeMidHour = 19;
						beforeMidSecond = 0;
					} 
					rvctWorkTime = (afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60 );
				// 00:00이 지난 새벽시간에 작업이 시작했을 경우
				} else if(beforeMidHour <= afterMidHour && beforeMidHour >= 0 && beforeMidHour <= 7) {
					// 끝난시간이 7시 이전일 경우 
					if(afterMidHour <= 7) {
						// 분 처리 (끝나는 시간의 분이 작을 경우, 반올림 수행)
						if(afterMidSecond < beforeMidSecond) {
							afterMidHour = afterMidHour - 1;
							afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
						}
					// 끝난 시간이 7시가 넘었을 경우, 무조건 7로 빼기
					} else {
						// 분 처리 (끝나는 시간의 분이 작을 경우, 반올림 수행)
						afterMidHour = 7;
						if(afterMidSecond < beforeMidSecond) {
							afterMidHour = afterMidHour - 1;
							afterMidSecond = parseInt(afterMidSecond) + parseInt(60);;
						}
					} 
					rvctWorkTime = ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60 )) * 1.3;
				} else {
					rvctWorkTime = 0;
				}
			// 휴일일 경우
			} else if(rvctStartType == "va") {
				// 가중치 1.5
				rvctWorkTime = ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60)) * 1.5;
			// 대체휴가일 경우
			} else if(rvctStartType == "rv") {
				// 가중치 1
				rvctWorkTime = ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60));
			// 평일 긴급일 경우
			} else if(rvctStartType == "ev") {
				if(afterMidHour > 19) {
					if(beforeMidHour < 19) {
						beforeMidHour = 19;
						beforeMidSecond = 0;
					} 
					rvctWorkTime = ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60 )) * 1.3;
				// 00:00이 지난 새벽시간에 작업이 시작했을 경우
				} else if(beforeMidHour <= afterMidHour && beforeMidHour >= 0 && beforeMidHour <= 7) {
					// 끝난시간이 7시 이전일 경우 
					if(afterMidHour <= 7) {
						// 분 처리 (끝나는 시간의 분이 작을 경우, 반올림 수행)
						if(afterMidSecond < beforeMidSecond) {
							afterMidHour = afterMidHour - 1;
							afterMidSecond = parseInt(afterMidSecond) + parseInt(60);
						}
					// 끝난 시간이 7시가 넘었을 경우, 무조건 7로 빼기
					} else {
						// 분 처리 (끝나는 시간의 분이 작을 경우, 반올림 수행)
						afterMidHour = 7;
						if(afterMidSecond < beforeMidSecond) {
							afterMidHour = afterMidHour - 1;
							afterMidSecond = parseInt(afterMidSecond) + parseInt(60);;
						}
					} 
					rvctWorkTime = ((afterMidHour - beforeMidHour) + ((afterMidSecond - beforeMidSecond) / 60 )) * 1.5;
				} else {
					rvctWorkTime = 0;
				}
			}
			
		// 19:00 ~ 익)??:?? 에 끝날 경우
		} else {
			// 평일 심야일 경우
			if(rvctStartType == "ni") {
				// 19:00 ~ 24:00 계산
				beforeMidHour = 24 - beforeMidHour;
				if(beforeMidHour > 5) { // 19시 이전에 야근을 시작한 경우
					beforeMidHour = 5;
					beforeMidSecond = 0;
				} else if(beforeMidHour <= 5) { // 19시 이후에 야근을 시작한 경우
					if(beforeMidSecond != 0) beforeMidHour = beforeMidHour - 1;
				}
				if(rvctEndType == "ni") {
					// 00:00 ~ 07:00 계산
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = (beforeMidHour + (beforeMidSecond / 60)) + ((afterMidHour * 1.3) + (afterMidSecond / 60 * 1.3));
				} else if(rvctEndType == "va") {
					rvctWorkTime = (beforeMidHour + (beforeMidSecond / 60)) + ((afterMidHour * 1.5) + (afterMidSecond / 60 * 1.5));
				} else if(rvctEndType == "rv") {
					if(afterMidHour >= 7) {
						rvctWorkTime = (beforeMidHour + (beforeMidSecond / 60)) + (7 * 1.3);
						rvctWorkTime += ((afterMidHour - 7) + (afterMidSecond / 60));
					} else {
						rvctWorkTime = (beforeMidHour + (beforeMidSecond / 60)) + ((afterMidHour * 1.3) + (afterMidSecond / 60 * 1.3));
					}
				} else if(rvctEndType == "ev") {
					alert("평일 야간 지원과 평일 긴급 지원은 같이 사용하실 수 없습니다.");
					return false;
				}
				
			// 휴일일 경우
			} else if(rvctStartType == "va") {
				// 반올림 계산
				if(beforeMidSecond > 0) {
					beforeMidHour = 23 - beforeMidHour;
					beforeMidSecond = 60 - beforeMidSecond;
				} else {
					beforeMidHour = 24 - beforeMidHour;
				}
			
				if(rvctEndType == "ni") {
					// 00:00 ~ 07:00 계산
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = ((beforeMidHour * 1.5) + (beforeMidSecond / 60 * 1.5)) + ((afterMidHour * 1.3) + (afterMidSecond / 60 * 1.3));	
				} else if(rvctEndType == "va") {
					rvctWorkTime = ((beforeMidHour * 1.5) + (beforeMidSecond / 60 * 1.5)) + ((afterMidHour * 1.5) + (afterMidSecond / 60 * 1.5));
				} else if(rvctEndType == "rv") {
					if(afterMidHour >= 7) {
						rvctWorkTime = ((beforeMidHour + (beforeMidSecond / 60)) * 1.5) + (7 * 1.3);
						rvctWorkTime += ((afterMidHour - 7) + (afterMidSecond / 60));
					} else {
						rvctWorkTime = ((beforeMidHour + (beforeMidSecond / 60)) * 1.5) + ((parseInt(afterMidHour) + (afterMidSecond / 60)) * 1.3);
					}
				} else if(rvctEndType == "ev") {
					// // 00:00 ~ 07:00 계산
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = ((beforeMidHour * 1.5) + (beforeMidSecond / 60 * 1.5)) + ((afterMidHour * 1.5) + (afterMidSecond / 60 * 1.5));
				}
			// 시작 타입이 대체휴가일 경우
			} else if(rvctStartType == "rv") {
				
				if(beforeMidHour < 7) {
					// 반올림
					if(beforeMidSecond != 0) {
						rvctWorkTime = ((6 - beforeMidHour) + (60 - beforeMidSecond) / 60) * 1.3;
					} else {
						rvctWorkTime = ((7 - beforeMidHour) + (beforeMidSecond) / 60) * 1.3;
					}
					rvctWorkTime = rvctWorkTime + (24 - 7);
				} else if(beforeMidHour >= 7) {
					// 반올림
					if(beforeMidSecond != 0) {
						rvctWorkTime = ((23 - beforeMidHour) + (60 - beforeMidSecond) / 60) * 1;
					} else {
						rvctWorkTime = ((24 - beforeMidHour) + (beforeMidSecond) / 60) * 1;
					}
				}
				
				if(rvctEndType == "ni") {
					// 00:00 ~ 07:00 계산
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = rvctWorkTime + ((afterMidHour * 1.3) + (afterMidSecond / 60 * 1.3));
				} else if(rvctEndType == "va") {
					rvctWorkTime = rvctWorkTime + (afterMidHour * 1.5) + (afterMidSecond / 60 * 1.5);
				} else if(rvctEndType == "rv") {
					if(afterMidHour >= 7) {
						rvctWorkTime = rvctWorkTime + (7 * 1.3);
						rvctWorkTime = rvctWorkTime + ((afterMidHour - 7) + (afterMidSecond / 60));
					} else {
						rvctWorkTime = rvctWorkTime + ((afterMidHour * 1.3) + (afterMidSecond / 60 * 1.3));
					}
				} else if(rvctEndType == "ev") {
					// 00:00 ~ 07:00 계산
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = rvctWorkTime + ((afterMidHour * 1.5) + (afterMidSecond / 60 * 1.5));
				}
			} else if(rvctStartType == "ev") {
				// 19:00 ~ 24:00 계산
				beforeMidHour = 24 - beforeMidHour;
				if(beforeMidHour > 5) {
					beforeMidHour = 5;
					beforeMidSecond = 0;
				} else if(beforeMidHour <= 5) {
					if(beforeMidSecond != 0) beforeMidHour = beforeMidHour - 1;
				}
				
				if(rvctEndType == "ni") {
					alert("평일 긴급 지원과 평일 야간 지원은 같이 사용하실 수 없습니다.");
					return false;
				} else if(rvctEndType == "va") {
					rvctWorkTime = ((beforeMidHour * 1.3) + (beforeMidSecond / 60) * 1.3) + ((afterMidHour)* 1.5 + (afterMidSecond / 60) * 1.5);
				} else if(rvctEndType == "rv") {
					alert("평일 긴급 지원과 대체휴가 중 야간 지원은 같이 사용하실 수 없습니다.");
					return false;
				} else if(rvctEndType == "ev") {
					if(afterMidHour >= 7) {
						afterMidHour = 7;
						afterMidSecond = 0;
					}
					rvctWorkTime = (((beforeMidHour) + (beforeMidSecond / 60)) * 1.3) + (((parseInt(afterMidHour)) + (afterMidSecond / 60)) * 1.5);
				}
			}
		}
		var korStartType = "";
		var korEndType = "";
		if(rvctStartType == "ni") korStartType = "심야";
		else if (rvctStartType == "va") korStartType = "휴일/긴급";
		else if (rvctStartType == "rv") korStartType = "대체휴가";
		else if (rvctStartType == "ev") korStartType = "평일긴급";
		if(rvctEndType == "ni") korEndType = "심야";
		else if (rvctEndType == "va") korEndType = "휴일/긴급";
		else if (rvctEndType == "rv") korEndType = "대체휴가";
		else if (rvctEndType == "ev") korEndType = "평일긴급";
		
		var check = confirm("신청자 : " + rvctEname + "\n사이트 : " + rvctSiteName + "\n트래킹ID : " + rvctJiraName + "\n시작일 : " + rvctStartDate + "\n종료일 : " + rvctEndDate + "\n시작 시간 : " + rvctStartTime + "\n종료 시간 : " + rvctEndTime + "\n시작 구분 : " + korStartType + "\n종료 구분 : " + korEndType + "\n내용 : " + rvctReason + "\n대체휴가 시간 : " + rvctWorkTime.toFixed(1));
		if(check) {
			var url = "";
			if(rvctHiddenType == "I") {
				url = "../controller/regRvctConfirm.jsp?rvctSiteName=" + encodeURIComponent(rvctSiteName) +"&rvctJiraName=" + encodeURIComponent(rvctJiraName) + "&rvctStartDate=" + rvctStartDate + "&rvctEndDate=" + rvctEndDate + "&rvctStartTime=" + rvctStartTime + "&rvctEndTime=" + rvctEndTime + "&rvctStartType=" + rvctStartType + "&rvctEndType=" + rvctEndType + "&rvctReason=" + encodeURIComponent(rvctReason) + "&rvctWorkTime=" + rvctWorkTime.toFixed(1) + "&rvctEno=" + rvctEno + "&rvctEname=" + rvctEname;
			} else if(rvctHiddenType == "U") {
				var rvctId = $("#rvctId").val();
				url = "../controller/updateRvctController.jsp?rvctSiteName=" + encodeURIComponent(rvctSiteName) +"&rvctJiraName=" + encodeURIComponent(rvctJiraName) + "&rvctStartDate=" + rvctStartDate + "&rvctEndDate=" + rvctEndDate + "&rvctStartTime=" + rvctStartTime + "&rvctEndTime=" + rvctEndTime + "&rvctStartType=" + rvctStartType + "&rvctEndType=" + rvctEndType + "&rvctReason=" + encodeURIComponent(rvctReason) + "&rvctWorkTime=" + rvctWorkTime.toFixed(1) + "&rvctEno=" + rvctEno + "&rvctEname=" + rvctEname + "&rvctId=" + rvctId;
			}
			//window.open(url, "", "width=0, height=0");
			location.href = encodeURI(url);
		} else {
			alert('false');
		}
	});
	
	$("#regVctConfirmBtn").click(function() {
		var eno = $("#eno").val();
		var avctId = $("#avctId").val();
		var rvctId = $("#rvctId").val();	
		var avctCount = parseFloat($("#avctCount").val());
		var rvctCount = parseFloat($("#rvctTime").val());
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var avctTime = parseFloat($("#avctTime").val());
		var rvctTime = parseFloat($("#rvctTime").val());
		//var svctTime = $("#svctTime").val();
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
		
		/*if(avctTime != 0 && avctTime > avctCount) {
			alert("신청하신 연차휴가 일수가 보유한 연차휴가를 초과되었습니다.\n\n보유한 연차휴가 : " + avctCount + "\n신청한 연차휴가 : " + avctTime);
			return false;
		}*/
		
		if(rvctTime != 0 && rvctTime > rvctCount) {
			alert("신청하신 대체휴가 일수가 보유한 대체휴가를 초과되었습니다.\n\n보유한 대체휴가 : " + rvctCount + "\n신청한 대체휴가 : " + rvctTime);
			return false;
		}
		
		if(rvctTime != 0 && trackingPkey == "") {
			alert("대체휴가 신청시 사이트를 선택해 주시기 바랍니다.");
			return false;
		}
		
		/*if(svctTime != 0 && (avctTime != 0 || rvctTime != 0)) {
			alert("경조휴가는 다른 휴가와 함께 사용할 수 없습니다.");
			return false;
		}*/
		
		if(avctTime != 0 && rvctTime != 0) {
			alert("현재 연차휴가와 대체휴가를 한번에 사용하실 수 없습니다.\n각각 등록하여 사용하여 주시기 바랍니다.");
			return false;
		}
		
		if(avctTime == 0 && rvctTime == 0 /*&& svctTime == 0*/) {
			alert("휴가 날짜를 입력해 주시기 바랍니다.");
			return false;
		}
		
		if(vctReason == null || vctReason == "") {
			alert("휴가 사유를 입력하세요.");
			return false;
		}
		
		// location.href="../controller/regVctConfirm.jsp?eno=" + eno + "&avctId=" + avctId + "&rvctId=" + rvctId + "&avctCount=" + avctCount + "&rvctCount=" + rvctCount + "&fromDate=" + fromDate + "&toDate=" + toDate + "&avctTime=" + avctTime + "&rvctTime=" + rvctTime + "&vctReason=" + vctReason /*+ "&svctTime=" + svctTime*/ + "&trackingPkey=" + trackingPkey + "&trackingSite=" + trackingSite + "&trackingReason=" + trackingReason;
		// location.href="../controller/regVctConfirm.jsp?eno=" + encodeURI(eno) + "&avctId=" + encodeURI(avctId) + "&rvctId=" + encodeURI(rvctId) + "&avctCount=" + encodeURI(avctCount) + "&rvctCount=" + encodeURI(rvctCount) + "&fromDate=" + encodeURI(fromDate) + "&toDate=" + encodeURI(toDate) + "&avctTime=" + encodeURI(avctTime) + "&rvctTime=" + encodeURI(rvctTime) + "&vctReason=" + encodeURI(vctReason) /*+ "&svctTime=" + svctTime*/ + "&trackingPkey=" + encodeURI(trackingPkey) + "&trackingSite=" + encodeURI(trackingSite) + "&trackingReason=" + encodeURI(trackingReason);
		
		var url = "";
		url = "../controller/regVctConfirm.jsp?eno=" + eno + "&avctId=" + avctId + "&rvctId=" + rvctId + "&avctCount=" + avctCount + "&rvctCount=" + rvctCount + "&fromDate=" + fromDate + "&toDate=" + toDate + "&avctTime=" + avctTime + "&rvctTime=" + rvctTime + "&vctReason=" + encodeURIComponent(vctReason) /*+ "&svctTime=" + svctTime*/ + "&trackingPkey=" + trackingPkey + "&trackingSite=" + trackingSite + "&trackingReason=" + trackingReason;
		location.href = encodeURI(url);
	});
});

function userMgt() {
	open("./popup/regUser.jsp", "", "width=500, height=600");
}

function rvctConfirmFn(rvctId, rvctWorkTime, rvctEno) {
	var check = confirm('승인하시겠습니까?');
	if(check) {
		open("controller/RvctConfirm.jsp?rvctId=" + rvctId + "&rvctWorkTime=" + rvctWorkTime + "&rvctEno=" + rvctEno + "&isType=Y", "", "width=1, height=1");
	} else {
		return false;
	}
}

function rvctConfirmFnA(rvctId, rvctWorkTime, rvctEno) {
	var check = confirm('승인하시겠습니까?');
	if(check) {
		open("../controller/RvctConfirm.jsp?rvctId=" + rvctId + "&rvctWorkTime=" + rvctWorkTime + "&rvctEno=" + rvctEno + "&isType=Y", "", "width=1, height=1");
	} else {
		return false;
	}
}

function rvctCancelFn(rvctId, rvctEno) {
	var check = confirm('미승인 하시겠습니까?');
	if(check) {
		open("controller/RvctConfirm.jsp?rvctId=" + rvctId + "&isType=N&rvctEno=" + rvctEno, "", "width=1, height=1");
	} else {
		return false;
	}
}

function rvctCancelFnA(rvctId, rvctEno) {
	var check = confirm('미승인 하시겠습니까?');
	if(check) {
		open("../controller/RvctConfirm.jsp?rvctId=" + rvctId + "&isType=N&rvctEno=" + rvctEno, "", "width=1, height=1");
	} else {
		return false;
	}
}

function rvctRegBtnFn(eno) {
	open("./popup/regRVct.jsp?eno=" + eno, "", "width=816, height=372");
}

function rvctConfirmDel(rvctId) {
	var check = confirm('삭제하시겠습니까?');
	
	if(check) {
		open("./controller/rvctConfirmDel.jsp?rvctId=" + rvctId, "", "width=200, height=100");
	} else {
		return false;
	}
	
}

function rvctConfirmUpdate(rvctId, rvctStatus) {
	if(rvctStatus != "W") {
		alert("대체휴가 등록 수정은 관리자가 확인 전에만 수정할 수 있습니다.");
		return false;
	}
	open("./popup/updateRegRvct.jsp?rvctId=" + rvctId, "", "width=816, height=372");
}

function regVct(userEno) {
	open("./popup/regVct.jsp?eno=" + userEno, "", "width=905, height=380");
}

function vctConfirmFn(vctConfirmId, vctId, vctcount, eno) {
	var check = confirm("승인 하시겠습니까?");
	if(check) {
		var isSend = confirm("E-MAIL을 발송하시겠습니까?");
		open("./controller/vctConfirm.jsp?vctConfirmId=" + vctConfirmId + "&vctId=" + vctId + "&vctcount=" + vctcount + "&eno=" + eno + "&isSend=" + isSend, "", "width=50, height=50");
	} else {
		return false;
	}
}

function showDetail(eno, quater) {
	open("./popup/showDetailRvct.jsp?quater=" + quater + "&eno=" + eno, "", "width=815, height=370");
}

function updateVctByAdmin(vctId, index) {
	var vctCountId = "vctCount" + index;
	var originVctCountId = "originVctCount" + index;
	var vctCount = document.getElementById(vctCountId).value;
	var originVctCount = document.getElementById(originVctCountId).value;
	var checkVal = document.getElementsByName("checkVal");
	var checkedIndex = -1;
	var checkedValue = "";
	var vctType = "";
	
	for(i = 0; i < checkVal.length; i++) {
		if(checkVal[i].checked) {
			checkedIndex = i;
			checkedValue = checkVal[i].value;
		}
	}
	
	if(index % 2 == 1) vctType = "A";
	else vctType = "R";
	
	if(checkedValue == 0) {
		open("./popup/adminUpdateList.jsp?vctId=" + vctId + "&vctCount=" + vctCount + "&originVctCount=" + originVctCount + "&vctType=" + vctType, "", "width=615, height=450");
	} else if(checkedValue == 1) {
		// 여기 만들기
		open("./controller/adminUpdateController.jsp?vctId=" + vctId + "&vctCount=" + vctCount + "&originVctCount=" + originVctCount + "&vctType=" + vctType, "", "width=0, height=0");
	}
}

function showPastRvct(adminEno) {
	open("./popup/showPastRvct.jsp?adminEno=" + adminEno, "", "width=920, height=900");
}