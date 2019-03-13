<%@page import="com.cubrid.dao.VctDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int rvctId = Integer.parseInt(request.getParameter("rvctId"));
	VctDaoImpl vctDao = new VctDaoImpl();
	int result = vctDao.rvctConfirmDel(rvctId);
	System.out.println("rvct_confirm deleted - id : " + rvctId);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RvctConfirm Delete Popup</title>
<script type="text/javascript">
	var check = <%=result%>
	if(check == 1) {
		window.close();
	}
</script>
</head>
<body>

</body>
</html>