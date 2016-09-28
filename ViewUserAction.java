package com.collegemagazine.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.collegemagazine.delegate.ProfileMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.profileformbean;
import com.collegemagazine.util.UtilConstants;

/**
 * The ViewUserAction Class represents the view the number of user present 
 * in the system.Administrator can have only this permission 
 */
public class ViewUserAction extends HttpServlet {
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
	@SuppressWarnings( { "unused", "deprecation" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		HttpSession session = request.getSession();
		String user = request.getParameter("role");
		String status = request.getParameter("user_status");
		Vector<profileformbean> opv = null;
		try {
			path = request.getRealPath("/images");
			System.out.println(path);
			opv = new ProfileMgrDelegate().viewUser(path, user, status);
			if (!opv.isEmpty()) {
				request.setAttribute("userinfo", opv);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_USER;
			} else if (opv.isEmpty()) {
				request.setAttribute("status", UtilConstants._NO_USER);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_ALL__USER;
				throw new NullPointerException("No users are Available");
			} else {
				request.setAttribute("status", UtilConstants._NO_USER);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_ALL__USER;
			}
		}
		catch (ConnectionException e) {
			request.setAttribute("status", UtilConstants._VIEW_ALL__USER);
			path=UtilConstants._VIEW_ALL__USER;
		}
		
		catch (Exception e) {
			request.setAttribute("status", UtilConstants._INVALIED_ENTRY);
			path = UtilConstants._VIEW_ALL__USER;
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
