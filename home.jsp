<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
	<head>
	<style>
	h1 {
  font-size: 72px;
  
}
#inp{
  background:green;
  border:red;
  font-size:2em;
  color:yellow;
}
	</style>
		<base href="<%=basePath%>">

		<title>NPU magazine </title>
		<link href="style.css" rel="stylesheet" />

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		    
		

<script type="text/javascript">
function disableBackButton()
{
window.history.forward();
}
setTimeout("disableBackButton()", 0);
</script>


</head>
	<body  onload="javascript:disableBackButton()" >
    <jsp:include page="header.jsp"></jsp:include>
		
		
		<form  action="./SearchEngine" method="post" name="search">
		<center> <input type="submit" id="inp" name="search" value="CHECK ARTICLES">
		</center><br>
		<marquee scrolldelay="20" >
			<div>
				<img src="<%=request.getContextPath() + "/images/1.jpg"%>"
					align="top" height="450" width="400" />
				<img src="<%=request.getContextPath() + "/images/2.jpg"%>"
					align="top" height="450" width="400" />
				<img src="<%=request.getContextPath() + "/images/3.jpg"%>"
					align="top" height="450" width="400" />
				<img src="<%=request.getContextPath() + "/images/4.jpg"%>"
					align="top" height="450" width="400" />
				<img src="<%=request.getContextPath() + "/images/5.jpg"%>"
					align="top" height="450" width="400" />
				<img src="<%=request.getContextPath() + "/images/6.jpg"%>"
					align="top" height="450" width="400" />
			</div>

		</marquee >
		
		
			<div >
		<!--  <marquee><h1>NORTHWESTERN POLYTECHNIC UNIVERSITY</h1></marquee> -->
		</div>
			
				
		</form> 		 		 
			 
			 <br/><br/><br><br>				 	 
			
                 <jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>
