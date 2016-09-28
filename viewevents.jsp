<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.collegemagazine.bean.EventsTo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'viewevents.jsp' starting page</title>
    
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
                 <h5 align="center"><font color="#nm65gg">EVENTS INFORMATION</font></h5>
          <table align="center" border="2">
                 <tr align="center">
                     <td align="center"><b>EventName</b></td>
                     <td align="center"><b>EventType</b></td>
                     <td align="center"><b>EventDescription</b></td>
                     <td align="center"><b>ConductedDate</b></td>
                <%
                     Vector c = (Vector) request.getAttribute("eventsinfo");
			         Iterator it = c.listIterator();
			         while(it.hasNext()) {
				    EventsTo eto = (EventsTo) it.next();
				%>
                 </tr>    
                 <tr>
                     <td><%=eto.getEventname() %></td>
                      <td><%=eto.getEventtype() %> </td>
                      <td><%=eto.getEventdesc() %></td>
                     <td><%=eto.getEventdate() %></td>
                </tr>
         
          <input type="hidden" name="" value="">
          <%
            }
          %> </table>
          <br><br><br><br><br><br><br><br><br><br>
   <jsp:include page="footer.jsp"></jsp:include>       
  </body>
</html>
