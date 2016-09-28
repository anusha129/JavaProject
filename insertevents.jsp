<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'insertevents.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<form action="./InsetEventsAction" method="get" name="events">
			<h3 align="center">
				<font color="brown">Add Event</font>
			</h3>
			<table align="center" border="1">
				<tr align="center" bordercolor="">
					<td align="right">
						<b>EventName:</b>
						<input type="text" name="eventname">
					</td>
				</tr>
				<tr align="center">
					<td align="right">
						<b>EventType:</b>
						<input type="text" name="eventtype">
					</td>
				</tr>
				<tr align="center">
					<td align="right">
						<b>EventDesc:</b>
						<input type="text" name="eventdesc">
					</td>
				</tr>
			</table>
			<table align="center">
				<tr align="center">
					<td>
						<input type="submit" value="Submit">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>

		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
