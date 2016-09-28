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
    
    <title>My JSP 'updateuserarticles.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="style.css">
	
<%
			 String path1;
			String s;
			if (session.getAttribute("user") == null) {
				RequestDispatcher rd = request
						.getRequestDispatcher("/loginform.jsp");
				rd.forward(request, response);
			}
		%>
  </head>
  
  <body>
   <jsp:include page="header.jsp"></jsp:include>
               <h4 align="center"><font color="PRASAD">UPDATE ARTICLES</font> </h4>
               
               <%
			Vector c = (Vector) request.getAttribute("articlesinfo");
			Iterator it = c.listIterator();
			if (it.hasNext()) {
				ArticleTo ato= (ArticleTo) it.next();
		%>
		<form action="./UpdateArticlesAction" name="updatearticles" method="post">
            <table align="center" border="">
                  <tr align="center">
                     <td><b>ArticleName:</b></td>
                     <td><input type="text" name="articlename" value="<%=ato.getArticlename() %>"></td>
                  </tr>
                  <tr align="center">
                      <td><b>ArticleTyepe:</b></td>
                      <td><input type="text" name="articletype" value="<%=ato.getArticletype() %>"></td>
                  </tr>
                  <tr align="center">
                       <td><b>CategoryName</b></td>
                       <td><input type="text" name="categoryname" value="<%=ato.getCategoryname() %>"></td>  
                  </tr>                             
                  <tr align="center">
                        <td><b>CategoryType:</b></td>
                        <td><input type="text" name="categorytype" value="<%=ato.getCategorytype() %>"></td>
                  </tr>
                  <tr align="center">
                       <td><b>PostedDate:</b></td>
                       <td><input type="text" name="posteddate" value="<%=ato.getPosteddate() %>"></td>
                  </tr>
                  </table>
                  <table align="center">
                  <tr align="center"> 
                      <td align="center">
                           <input type="submit" name="update" value="Update">
                      </td>
                  </tr>                                 
            </table>
          
            <%
             }
            %>
            </form>  
    <script language="JavaScript" type="text/javascript">
//You should create the validator only after the definition of the HTML form
  var frmvalidator  = new Validator("updatearticles");
  
 
  frmvalidator.addValidation("articlename","req","Please enter your Article Name");
  
  frmvalidator.addValidation("articletype","req","Please enter your Article Type");
  
   frmvalidator.addValidation("posteddate","req","Please enter your Posted Date"); 
  
   frmvalidator.addValidation("categoryname","req","Please enter Your Category Name"); 
  
   frmvalidator.addValidation("categorytype","req","Please enter your Category Type");
   
    </script>
            
            
  </body>
</html>
