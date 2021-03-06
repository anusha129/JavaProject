package com.collegemagazine.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.collegemagazine.delegate.ProfileMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

/*
 * This UpdateUserStatusAction Class represents the user 
 * status here administrator can update the user status
 */
public class UpdateUserStatusAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		boolean flag = false;
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		int userid = Integer.parseInt(request.getParameter("userid"));
		try {
			flag = new ProfileMgrDelegate().updateUserStatus(userid);
			if (flag) {
				request.setAttribute("status", UtilConstants._USER_STATUS);
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute("status", UtilConstants._USER_STATUS_FAIL);
				path = UtilConstants._STATUS;
			}
		} 
		
		catch (ConnectionException e) {
		request.setAttribute("status", e.getMessage());
		path=UtilConstants._STATUS;
			
		
		}
		catch (Exception e) {
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
