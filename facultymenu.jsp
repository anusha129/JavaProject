<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml2/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Faculty Menu</title>
		<meta name="Author" content="Stu Nicholls" />
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		
</script>

	</head>

	<body>

<span class="preload1"></span>
<span class="preload2"></span>

<nav class="navbar navbar-default">
<div class="container-fluid">
 <ul class="nav navbar-nav">
         	 
         	 
			<li>
				<a href="./facultyhome.jsp" class="top_link"><span>Home</span></a>				
			</li>

				<li class="dropdown">
				<a href="#nogo22" id="services" class="dropdown-toggle" data-toggle="dropdown">Profile <span class="caret"></span></a>				
					
				<ul class="dropdown-menu">

							<li>
								<a href="./ViewProfileAction?user=<%=session.getAttribute("user")%>&requesttype=update">UpdateProfile</a>
							</li>
							<li>
								<a href="./ViewProfileAction?user=<%=session.getAttribute("user")%>&requesttype=view">ViewProfile</a>
							</li>
				</ul>
				</li>
				
				
				<li class="dropdown">
					<a href="#nogo22" id="services" class="dropdown-toggle" data-toggle="dropdown" >Articles<span class="caret"></span></a>
					
					<ul class="dropdown-menu">
						<li>
							<a href="./articles.jsp">AddArticles</a>
						</li>
						
						<li>
							<a href="./ViewUsersArticlesAction?user=<%=session.getAttribute("user")%>&requesttype=view">ViewArticles</a>	
						</li>						
					</ul>
				</li>

			

				<li class="dropdown">
					<a href="#nogo22" id="services" class="dropdown-toggle" data-toggle="dropdown">Query<span class="caret"></span> </a>
						
					<ul class="dropdown-menu">

						<li>
							<a href="./queries.jsp">PostQuery</a>
						</li>
						<li>
							<a href="./ViewQueryStatusAction?from=<%=(String)session.getAttribute("user")%>">ViewSolution</a>
						</li>
					</ul>
				</li>
	
				
				
				<li class="dropdown">
				<a href="#nogo22" id="services" class="dropdown-toggle" data-toggle="dropdown">Security<span class="caret"></span> </a>					
				<ul class="dropdown-menu">

					<li>
						<a
							href="./changepassword.jsp?userid=<%=session.getAttribute("user")%>">changepassword</a>
					</li>
					<li>
						<a
							href="./changequestion.jsp?userid=<%=session.getAttribute("user")%>">changequestion</a>
					</li>
				</ul>
				</li>
							
				
			
			<li class="top">
				<a href="./LogOutAction" class="top_link"><span>Logout</span> </a>
			</li>
		</ul>
		</div>
		</nav>
    




	</body>
</html>
