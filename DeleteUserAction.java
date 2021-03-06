package com.collegemagazine.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.collegemagazine.delegate.ProfileMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

/*
 * DeleteUserAction Class implements Deleting  
 * the user from database successfully
 */
public class DeleteUserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

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
		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		String ch[] = request.getParameterValues("ch");
		try {
			for (int i = 0; i < ch.length; i++) {
				System.out.println(ch[i]);
				flag = new ProfileMgrDelegate()
						.deleteUser(Integer.parseInt(ch[i]));
			}
		
		
			if (flag) {
				request.setAttribute("status",
						UtilConstants._USER_DELETE_SUCCESS);
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute("status", UtilConstants._USER_DELETE_FAIL);
				path = UtilConstants._STATUS;
			}
		}
		catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path=UtilConstants._STATUS;
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("status", UtilConstants._USER_DELETE_FAIL);
			path = UtilConstants._STATUS;
		}finally {
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}}
