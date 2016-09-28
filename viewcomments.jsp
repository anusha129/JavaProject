<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.collegemagazine.bean.ArticleTo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'viewusersarticles.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script language="javascript">
function SingleSelect(regex,current)
{
re = new RegExp(regex);

for(i = 0; i < document.forms[0].elements.length; i++) {

elm = document.forms[0].elements[i];

if (elm.type == 'checkbox') {

if (re.test(elm.name)) {

elm.checked = false;

}
}
}

current.checked = true;

}
</script>
 
  </head>
  
  <body>

  <jsp:include page="header.jsp"></jsp:include>
 <h5 align="center"><font color="#nm65gg">COMMENTS INFORMATION</font></h5>
   <table align="center" border="1">
   
         <tr align="center">
                     <td align="center"><b>SNo</b></td>
            <td align="center"><b>ArticleName</b></td>
            <td align="center"><b>Comments</b></td>
            <td align="center"><b>Name</b></td>
            
            <%
					Vector v = (Vector) request.getAttribute("commentsinfo");
					Iterator it = v.listIterator();
					//System.out.println("no.of profiles is"+v);
					int count = 1;
					while (it.hasNext()) {
						System.out.println(count);
						ArticleTo ato = (ArticleTo) it.next();
				%>
         </tr>
         <tr>
            
            <td><%=count++%></td>
            <td><%=ato.getArticlename() %></td>
            <td><%=ato.getComment() %></td>
            <td><%=ato.getName() %></td>
            <input type='hidden' name='userName' value="<%=ato.getCommentid()%>">
         </tr>
            <%
         }
         %> 
     </table>
            
      
     
 <script >
function Redirect1()
{
 location.href="./DeleteArticleAction";
 }
 function Redirect2()
 {
  location.href="./jsps/updateuserarticles.jsp";
  }
 </script>      

  <br><br><br><br><br><br>
  <jsp:include page="footer.jsp"></jsp:include>
  </body>
  
  
</html>
