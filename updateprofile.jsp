
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.collegemagazine.formbeans.profileformbean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="userexceptionhandler.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
 <base href="<%=basePath%>">
    
    <title>My JSP 'articles.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->




	

		<script language="JavaScript" src="js/gen_validatorv31.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="js/ts_picker.js"></script>
		<script language="JavaScript1.1" src="js/pass.js">
</script>
		<script type="text/javascript" src="js/image.js"> </script>
		<script type="text/javascript" src="js/general.js"> </script>
		<script type="text/javascript" src="js/adi.js"> </script>
		<script type="text/javascript" src="js/form_validation.js"> </script>

		<script language="JavaScript" src="images/javascripts.js"></script>
		<script language="JavaScript" src="images/pop-closeup.js"></script>


		<style type="text/css">
.Title {
	font-family: Verdana;
	font-weight: bold;
	font-size: 8pt
}

.Title1 {
	font-family: Verdana;
	font-weight: bold;
	font-size: 8pt
}
</style>
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
		<br />
		<center>
			<h3>

				<span class=subHead><br />Updation Form </span>
			</h3>
		</center>
		<%
			Vector c = (Vector) request.getAttribute("userinfo");
			Iterator it = c.listIterator();
			if (it.hasNext()) {
				profileformbean prof= (profileformbean) it.next();
		%>
		<form action="./UpdateAction" name="register"
			onsubmit="return validate()">
			<!--<table border="1"><tr><td></td></tr></table>-->
			<input type="hidden" name="port" value="<%=request.getLocalPort()%>" />
			<input type="hidden" name="host" value="<%=request.getServerName()%>" />
			<br />

			<table border='0' align="center" width=70%>
				<tr>
					<td align='right'>
						User Name :
					</td>

					<td>
						<input type="text" name="userName"
							value="<%=session.getAttribute("user")%>" size="20" readonly/>
					</td>
					<td border="4" align="left" rowspan="5" colspan='2'>
						<img alt="See Photo Here" id="previewField"
							src="<%=prof.getPhoto()%>" height="150" width="120" />

					</td>
				</tr>


				<tr>
					<td align='right'>
						First Name :
					</td>
					<td width="276">
						<input type="text" name="firstName"
							value="<%=prof.getFirstName()%>" />
					</td>

				</tr>
				<tr>
					<td align='right'>
						Last Name :
					</td>
					<td width="276">
						<input type="text" name="lastName" value="<%=prof.getLastName()%>"
							size="20" />
					</td>

				</tr>
				<tr>
					<td align='right'>
						Birth Date :
					</td>
					<td>
						<input type="text" name="birthdate"
							value="<%=prof.getBirthdate()%>" size="20" readonly="readonly" />
						<a
							href="javascript:show_calendar('document.register.birthdate', document.register.birthdate.value);">
							<img src="images/cal.gif" alt="a" width="18" height="18"
								border="0" /> </a>
					</td>
				</tr>
				<tr>
					<td align='right'>
						Browse Photo :
					</td>
					<td>
					<input  type="hidden" name="photo" 
							value="<%=prof.getPhoto()%>"  />
						<input  type="file" name="photo1" class="textfield"
							value="<%=prof.getPhoto()%>"  />
					</td>
				<tr>
				<tr>

					<td align='right'>
						House No :
					</td>
					<td>
						<input type="text" name="houseNo" value="<%=prof.getHouseNo()%>"
							size="20" />
					</td>
					<td align='right'>
						Street :
					</td>
					<td>
						<input type="text" name="street" value="<%=prof.getStreet()%>"
							size="20" />
					</td>

				</tr>

				<tr>
					<td width="120" align='right'>
						City :
					</td>
					<td width="273">
						<input type="text" name="city" value="<%=prof.getCity()%>"
							size="20" />
					</td>
					
					
				</tr>
				<tr>
					<td align='right'>
						State :
					</td>
					<td>
						<input type="text" name="state" value="<%=prof.getState()%>"
							size="20" />
					</td>
					<td align='right'>
						Country :
					</td>
					<td>
						<input type="text" name="country" value="<%=prof.getCountry()%>"
							size="20" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Pin :
					</td>
					<td>
						<input type="text" name="pin" value="<%=prof.getPin()%>" size="20"
							onChange="showStatus()" />
					</td>
					<td align='right'>
						Email :
					</td>
					<td>
						<input type="text" name="email" value="<%=prof.getEmail()%>"
							size="20" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Fax No :
					</td>
					<td>
						<input type="text" name="fax" value="<%=prof.getFax()%>" size="20" />
					</td>
					<td align='right'>
						Phone No :
					</td>
					<td>
						<input type="text" name="phoneNo" value="<%=prof.getPhoneNo()%>"
							size="20" onBlur="ValidateForm()" />
					</td>
				</tr>



				<tr>

				</tr>

				<tr>
					<td></td>
					<td align="right">
						<font size="3" face="Verdana"> <input type="submit"
								name="update" value="update" />&nbsp; </font>
					</td>
					<td align="left">
					</td>
					<td></td>
				</tr>
			</table>
			<%
				}
			%>
		</form>

		<script language="JavaScript" type="text/javascript">
//You should create the validator only after the definition of the HTML form
  var frmvalidator  = new Validator("register");
  
 
  frmvalidator.addValidation("firstName","req","Please enter your First Name");
  frmvalidator.addValidation("firstName","maxlen=20",	"Max length for FirstName is 20");
  frmvalidator.addValidation("firstName","alpha"," First Name Alphabetic chars only");
  
  frmvalidator.addValidation("lastName","req","Please enter your Last Name");
  frmvalidator.addValidation("lastName","maxlen=20","Max length is 20");
  frmvalidator.addValidation("lastName","alpha"," Last Name Alphabetic chars only");
  
   frmvalidator.addValidation("birthdate","req","Please enter your birthdate"); 
  
   frmvalidator.addValidation("photo","req","Please Load Your Photo"); 
  
  frmvalidator.addValidation("email","maxlen=50");
  frmvalidator.addValidation("email","req");
  frmvalidator.addValidation("email","email");
   
  
   frmvalidator.addValidation("addressType","dontselect=0");
   
   frmvalidator.addValidation("houseNo","req","Please enter your House Number");
   
   frmvalidator.addValidation("street","req","Please enter your Street Number");
     frmvalidator.addValidation("phoneType","dontselect=0");
   frmvalidator.addValidation("phoneNo","req");
  
  frmvalidator.addValidation("phoneNo","maxlen=50");
  frmvalidator.addValidation("phoneNo","numeric");
 frmvalidator.addValidation("phoneNo","Phone");
   
   frmvalidator.addValidation("city","req","Please enter your city Name");
   frmvalidator.addValidation("state","req","Please enter your State Name");
   frmvalidator.addValidation("country","req","Please enter your Country Name");
   frmvalidator.addValidation("pin","req","Please enter your pin Number");
   
   frmvalidator.addValidation("userName","req","Please enter your Username");
   frmvalidator.addValidation("fax","req","Please enter Fax Number");
  
   
    
 </script>

		<br />
		<br />

		<jsp:include page="footer.jsp"></jsp:include>


	</body>
</html>
