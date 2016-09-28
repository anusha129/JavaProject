package com.collegemagazine.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.collegemagazine.bean.QueryTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.Queryformbean;
import com.collegemagazine.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class SendSmsAlertsAction extends HttpServlet {
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
		try {
			boolean flag = false;
			flag = new QueryMgrDelegate().sendSms(request
					.getParameter("message"), request.getParameter("smspass"));
			if (flag == true) {
				request.setAttribute("status", UtilConstants._SMS_SEND_SUCCESS);
				path = UtilConstants._SMS_HOME;
			} else {
				request.setAttribute("status", UtilConstants._SMS_SEND_FAILED);
				path = UtilConstants._SMS_HOME;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._SMS_HOME;
			e.printStackTrace();
		} catch (Exception e) {
			request.setAttribute("status", UtilConstants._SMS_SEND_FAILED);
			path = UtilConstants._SMS_HOME;
			e.printStackTrace();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
}
