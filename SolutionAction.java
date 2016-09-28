package com.collegemagazine.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.collegemagazine.bean.QueryTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

/*
 * This SolutionAction Class gives the solution for posted queries from 
 * faculty and students. Depending on the query it gives the right answer
 */
public class SolutionAction extends HttpServlet {
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
		Vector<QueryTo> vqb = null;
		try {
			int qid = Integer.parseInt(request.getParameter("qid"));

			vqb = new QueryMgrDelegate().getQueriesAt(qid);

			System.out.println("in Action class vcb..........." + vqb);
			if (!vqb.isEmpty()) {
				request.setAttribute("Querysolution", vqb);
				request.setAttribute("status", UtilConstants._QUERY_INFO);
				path = UtilConstants._SOLUTION_HOME;
			} else {
				request.setAttribute("status", UtilConstants._NO_QUERY);
				path = UtilConstants._SOLUTION_HOME;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._SOLUTION_HOME;
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
}
