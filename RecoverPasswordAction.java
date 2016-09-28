package com.collegemagazine.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.collegemagazine.delegate.SecurityMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.profileformbean;
import com.collegemagazine.util.LoggerManager;
import com.collegemagazine.util.UtilConstants;

/**
 * This RecoverPasswordAction Class implements the recover the
 * password in any case the user can forget the password using the 
 * security question and proper answer it will gain from database
 */
public class RecoverPasswordAction extends HttpServlet {
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
		String target = "./jsps/recoverpassword.jsp?status=<font color=red>Entries not Matched... Try Again</font>";
		;
		try {
			profileformbean rb = new profileformbean();
			String loginid = request.getParameter(UtilConstants._USERNAME);
			String sanswer = request.getParameter("sanswer");
			rb.setLoginid(loginid);
			rb.setSecrete(sanswer);
			String squestid = "";
			String password = "";
			squestid = request.getParameter("squest");
			if (request.getParameter("ch") != null) {
				squestid = request.getParameter("ownquest");
			}
			rb.setSquest(squestid);
			password = new SecurityMgrDelegate().recoverPasswordByQuestion(rb);
			if (password.equals("") || password == null)
				target = "./jsps/recoverpassword.jsp?status=<font color=red>Entries not Matched... Try Again</font>";
			else
				target = "./jsps/loginform.jsp";
			request.setAttribute("status", "Password is " + password);
		}
		catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			target= "./jsps/loginform.jsp";
		}
		catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}
}}
