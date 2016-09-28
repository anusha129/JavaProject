<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<body>

		<table align="center">

			<tr align="center">

				<!-- <input type="text" value="" name="search" align="right"> -->
				<!-- <input type="submit" value="Search" name="search"> -->

			</tr>
			<tr>
				<td colspan="1" width="900" height="180" valign="top">
					<img src="<%=request.getContextPath() + "/images/npu.JPG"%>"
						align="top" height="300" width="1000" />
				</td>
			</tr>

			<tr>
				<td colspan="1" width="900" height="20">
					<center>
						<font color="#E851AF" size="6"><b><i> </i>
						</b>
						</font><font color="#4D88DB" size="6"><b><i> </i>
						</b>
						</font>
					</center>
				</td>
			</tr>

		</table>
		<table width="1000">



			<c:choose>
				


				<c:when test="${sessionScope.role=='faculty'}">
					<jsp:include page="./facultymenu.jsp" />
				</c:when>



				<c:when test="${sessionScope.role=='student'}">
					<jsp:include page="./studentmenu.jsp" />
				</c:when>

				<c:when test="${sessionScope.role=='moderator'}">
					<jsp:include page="./moderatorsmenu.jsp" />
				</c:when>

				<c:otherwise>
					<jsp:include page="./menubeforelogin.jsp" />
				</c:otherwise>
			</c:choose>

		</table>
		<br />
		<br />

		<center>
			<font color="red"> <c:if
					test="${'requestScope.status'!='null'}">

					<c:out value="${requestScope.status}"></c:out>
				</c:if> </font>
		</center>




	</body>
</html>
