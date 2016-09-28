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

/*
 * This PostSolutionAction Class which implements the solution
 *  for the posted queries from faculty and student 
 */
public class PostSolutionAction extends HttpServlet {
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
		Queryformbean qfb = new Queryformbean();
		Map map = request.getParameterMap();
		String path = "";
		try {
			BeanUtils.populate(qfb, map);
			boolean flag = false;
			QueryTo qto = new QueryTo(qfb);
			flag = new QueryMgrDelegate().insertSolution(qto);
			if (flag == true) {
				request.setAttribute("status",
						UtilConstants._POST_SOLUTION_SUCCESS);
				path = UtilConstants._SOLUTION_HOME;
			} else {
				request.setAttribute("status",
						UtilConstants._POST_SOLUTION_FAILED);
				path = UtilConstants._SOLUTION_HOME;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._SOLUTION_HOME;
			e.printStackTrace();
		}catch (Exception e) {
			request.setAttribute("status", UtilConstants._INVALIED_DATA);
			path = UtilConstants._SOLUTION_HOME;
			e.printStackTrace();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
}
