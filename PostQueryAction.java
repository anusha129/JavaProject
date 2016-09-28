package com.collegemagazine.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import com.collegemagazine.bean.QueryTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.Queryformbean;
import com.collegemagazine.util.UtilConstants;

/**
 * This PostQueryAction Class implements the adding Query into the database
 * which are posted by faculty and Student
 */
public class PostQueryAction extends HttpServlet {
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
		Queryformbean qb = new Queryformbean();
		Map map = request.getParameterMap();
		String path = "";
		try {
			BeanUtils.populate(qb, map);
			boolean flag = false;
			QueryTo qto = new QueryTo(qb);
			flag = new QueryMgrDelegate().insertQuery(qto);
			if (flag == true) {
				request.setAttribute("status",
						UtilConstants._POST_QUERY_SUCCESS);
				path = UtilConstants._QUERY_HOME;
			} else {
				request
						.setAttribute("status",
								UtilConstants._POST_QUERY_FAILED);
				path = UtilConstants._QUERY_HOME;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._QUERY_HOME;

		} catch (Exception e) {
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
}
