package com.collegemagazine.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.collegemagazine.delegate.SecurityMgrDelegate;
import com.collegemagazine.formbeans.profileformbean;

/*
 * The ChangeQuestionAction Class implements the Change 
 * the Question for Security purpose
 */
public class ChangeQuestionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "./jsps/changequestion.jsp?status=Change Failed";
		try {
			HttpSession session = request.getSession();
			profileformbean rb = new profileformbean();
			String loginid = request.getParameter("username");
			String password = request.getParameter("password");
			String sanswer = request.getParameter("sanswer");
			String squestid = "";
			if (request.getParameter("ch") != null) {
				rb.setSquest(request.getParameter("ownquest"));
			} else {
				squestid = request.getParameter("squest");
				rb.setSquest(squestid);
			}
			rb.setLoginid(loginid);
			rb.setPassword(password);
			rb.setSecrete(sanswer);
			boolean flag = new SecurityMgrDelegate().changeQuestion(rb);
			String pathstring = "loginform.jsp";
			if (((String) session.getAttribute("role")).equals("Admin"))
				pathstring = "./jsps/adminhome.jsp";
			else if (((String) session.getAttribute("role")).equals("student"))
				pathstring = "./jsps/studenthome.jsp";
			else if (((String) session.getAttribute("role")).equals("faculty"))
				pathstring = "./jsps/facultyhome.jsp";
			else
				pathstring = "./jsps/moderatorshome.jsp";
			if (flag)
				target = pathstring + "?status=Change Success";
			else
				target = "./jsps/changequestion.jsp?status=Change Failed";
		} 
		
		catch (Exception e) {
		}
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}
}
