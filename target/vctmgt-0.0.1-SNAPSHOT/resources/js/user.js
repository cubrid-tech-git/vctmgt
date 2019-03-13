$(function() {
	$(".insertBtn").click(function() {
		var userName = $(this).parent().prev().attr("title"); 
		var userDept = $(this).parent().prev().prev().children().val();
		var userId = $(this).parent().prev().prev().prev().attr("title");

		if(userDept == "default") {
			alert("");
			return false;
		}
		
		var url = "regUser.jsp?userName=" 
			+ userName + "&userDept=" + userDept + "&userId=" + userId;
		
		$("#popup").load(url);
	});
	
	$("#regUserBtn").click(function() {
		var dno = $("#cubDno").val();
		var ename = $("#cubName").val();
		var email = $("#cubEmail").val();
		
		var url = "../controller/RegUserController.jsp?cubDno="
			+ dno + "&cubName=" + ename + "&cubEmail=" + email; 
		
		if(dno != 0 && (ename != "" || ename != null ) && (email != "" || email != null))
			$("#popup").load(url);
	});
	
	$(".editBtn").click(function() {
		/*var data = $(this).attr("title");
		var eno = data.split(":")[0];
		var ename = data.split(":")[1];*/
		var eno = $(this).attr("title")
		var url = "./editUser.jsp?eno=" + eno;
		$("#popup").load(url);
	});
	
	$("#userEdit").click(function() {
		var eno = $("#cubEno").val();
		var ename = $("#cubEname").val();
		var dno = $("#cubDno").val();
		var email = $("#cubEmail").val();
		var emailPwd = $("#cubEmailPwd").val();
		var cubAdminCheck = 0;	// 관리자 여부 확인 변수
		// 관리자 변경 여부 확인
		if($("#cubAdminCheck").is(":checked") == true) {
			alert("admin is checked");
			cubAdminCheck = 1;
		} else {
			alert("admin is not checked.");
		}
		
		var url = "../controller/editUserController.jsp?eno="
			+ eno + "&ename=" + ename + "&dno=" + dno
			+ "&email=" + email + "&emailPwd=" + emailPwd
			+ "&cubAdminCheck=" + cubAdminCheck;
			
		$("#popup").load(url);
	});
	
	$("#userDel").click(function() {
		// delete 수행
		var check = confirm("해당 사용자를 삭제하시겠습니까?");
		
		if(check) {
			var eno = $("#cubEno").val();
			var ename = $("#cubEname").val();
			var url = "../controller/DelUserController.jsp?eno=" + eno + "&ename=" + ename;
			$("#popup").load(url);
		}
	});
	
	$("#userCancle").click(function() {
		location.reload();
	});
});