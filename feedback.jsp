<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title>My JSP 'feedback.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


		<script language="JavaScript1.2">

function checknumber() {
	var x = document.f.accno.value
	var anum = /(^\d+$)|(^\d+\.\d+$)/
	if (anum.test(x))
		testresult = true
	else {
		alert("Please input a valid number!")
		testresult = false
	}
	return (testresult)
}
</script>
		<script>
function checkban() {
	if (validateFields()) {
		if (document.layers || document.all || document.getElementById)
			return checknumber()
		else
			return true;
	} else
		return false;
}
</script>
		<script type="text/javascript" src="js/project.js">
</script>

		<head>
			<script language="JavaScript"
				src="<%=request.getContextPath() + "/js/gen_validatorv31.js"%>"
				type="text/javascript">
</script>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

			<meta http-equiv="Last-Modified"
				content="Thu, 02 Aug 2007 10:30:00 GMT" />




			<link rel="icon" href="http://www.bis.org/favicon.ico"
				type="image/x-icon" />
			<link rel="shortcut icon" href="http://www.bis.org/favicon.ico"
				type="image/x-icon" />

			<link href="cbanks_files/standard.css" type="text/css"
				rel="stylesheet" />

			<!-- JavaScript variable to set the active Menu -->
			<script type="text/javascript" src="cbanks_files/standard.js">
</script>
			<script type="text/javascript">

</script>
			<style type="text/css">
<!--
.style1 {
	font-size: 50px
}
-->
</style>
		</head>
		<body onload="mainOnLoad();">
			<!-- providing keyboard shortcuts to important links -->
			<div id="accessibilityLinks"></div>
			<div id="header">
				<div class="style1" id="header_left">
				</div>
				<div id="header_right">
					<div id="header_links">



					</div>
					<div class="hr">
					</div>
					<div id="search_form">
						<p></p>

					</div>
				</div>
			</div>
			<div id="header_separator">
			</div>
			<div id="navigation_main">
				<ul id="main_menu">




					<jsp:include page="header.jsp"></jsp:include>




					<center>
						<h3>
							<i><font color="#Fa9170">FeedBack Form</font>
							</i>
						</h3>
					</center>
					<body>

						<form action="PostFeedBackAction" method="post" name="feedback">
							<table width="379" border="2" align="center"
								bordercolor="#000000">
								<tr>
									<td bgcolor="#FFFFFF" class="style2 style3">
										<strong><font color="#419af2">Title</font>
										</strong>
									</td>
									<td bordercolor="#000000" bgcolor="#FFFFFF">
										<input type="text" name=title value="">
									</td>
								</tr>
								<tr>
									<td bgcolor="#FFFFFF" class="style2 style3">
										<strong><font color="#419af2">FeedBack</font>
										</strong>
									</td>
									<td bordercolor="#000000" bgcolor="#FFFFFF">
										<textarea name="feedback"></textarea>
									</td>
								</tr>
								<tr>
									<td width="85" bgcolor="#FFFFFF">
										<span class="style2 style3"><strong><font
												color="#419af2">Name</font> </strong>
										</span>
									</td>
									<td width="284" bordercolor="#000000" bgcolor="#FFFFFF">
										<input type="text" name="name" value="" />
									</td>
								</tr>

								<tr>
									<td bgcolor="#FFFFFF" class="style2 style3">
										<strong><font color="#419af2">Address</font>
										</strong>
									</td>
									<td bordercolor="#000000" bgcolor="#FFFFFF">
										<textarea name="address"></textarea>
									</td>
								</tr>
								<tr>
									<td width="85" bgcolor="#FFFFFF">
										<span class="style2 style3"><strong><font
												color="#419af2">PhoneNo</font>
										</strong>
										</span>
									</td>
									<td width="284" bordercolor="#000000" bgcolor="#FFFFFF">
										<input type="text" name="phone" />
									</td>
								</tr>
								<tr>
									<td width="85" bgcolor="#FFFFFF">
										<span class="style2 style3"><strong><font
												color="#419af2">EmailID</font>
										</strong>
										</span>
									</td>
									<td width="284" bordercolor="#000000" bgcolor="#FFFFFF">
										<input type="text" name="email" />
									</td>
								</tr>



								<tr bgcolor="#FFFFFF">
									<td colspan="2">
										<div align="center" class="style3">
											<br />
											<input type="submit" name="send" value="PostFeedBack">
											&nbsp;

										</div>
									</td>
								</tr>
							</table>






						</form>
						<script language="JavaScript" type="text/javascript">
//You should create the validator only after the definition of the HTML form
var frmvalidator = new Validator("feedback");
frmvalidator.addValidation("title", "req", "title is required");
frmvalidator.addValidation("feedback", "req", "Feedback is required");
frmvalidator.addValidation("name", "req", "Name is required");
frmvalidator.addValidation("address", "req", "Address is required");

frmvalidator.addValidation("phoneType", "dontselect=0");
frmvalidator.addValidation("phoneNo", "req");

frmvalidator.addValidation("phoneNo", "maxlen=50");
frmvalidator.addValidation("phoneNo", "numeric");
frmvalidator.addValidation("phoneNo", "Phone");

frmvalidator.addValidation("email", "maxlen=50");
frmvalidator.addValidation("email", "req");
frmvalidator.addValidation("email", "email");
</script>
						<jsp:include page="footer.jsp"></jsp:include>
					</body>
</html>
