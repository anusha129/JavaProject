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
	
	<link rel="stylesheet" type="text/css" href="style.css">
	
<%
	String path1;
	String usertype;
	String s;
%>
<%
	if (session.getAttribute("user") == null) {
		RequestDispatcher rd = request
				.getRequestDispatcher("/loginform.jsp");
		rd.forward(request, response);
%>
<%
	}
%>
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
 
   <table align="center" border="1">
         <tr align="center">
         <td align="center"><b></b> </td>
            <td align="center"><b>SNo</b></td>
            <td align="center"><b>ArticleName</b></td>
            <td align="center"><b>ArticleType</b></td>
            <td align="center"><b>CategoryName</b></td>
            <td align="center"><b>CategoryType</b></td>
            <td align="center"><b>PostedDate</b></td>
             </tr>
            <%
					Vector v = (Vector) request.getAttribute("articlesinfo");
					Iterator it = v.listIterator();
					//System.out.println("no.of profiles is"+v);
					int count = 1;
					while (it.hasNext()) {
						System.out.println(count);
						ArticleTo ato = (ArticleTo) it.next();
				%>
        
         <tr>
         <col width="30">
            <td><input type="checkbox"> </td>
            <td><%=count++ %></td>
            <td><%=ato.getArticlename() %></td>
            <td><%=ato.getArticletype() %></td>
            <td><%=ato.getCategoryname() %></td>
            <td><%=ato.getCategorytype() %></td>
            <td><%=ato.getPosteddate() %></td>
         </tr>
            <%
         }
         %> 
     </table>
      <table align="center">
             <tr align="center">
                    <td><input type="submit" name="" value="Delete" onclick="Redirect1()"></td>
           
                      <td align="center">
                        <input type="submit" name="" value="Update" onclick="Redirect2()"></td>
                 </tr>           
      
      </table>
         
 <script >
function Redirect1()
{
 location.href="./DeleteArticleAction";
 }
 function Redirect2()
 {
  location.href="./updateuserarticles.jsp";
  }
 </script>      

  <br><br><br><br><br><br>
  <jsp:include page="footer.jsp"></jsp:include>
  </body>
  
  
</html>
