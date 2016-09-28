package com.collegemagazine.action;

import java.io.IOException;

import java.util.Map;
import java.util.Vector;

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

/**
 * The ViewQueriesAction Class represents the moderator 
 * can view the queries which are posted by the faculty and student
 */
public class ViewQueriesAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Queryformbean qb = new Queryformbean();
		
		String path = "";
		try {
			
		
		
		Vector<QueryTo> queryInfo = null;

		
				queryInfo = new QueryMgrDelegate().getQueries();
			
			System.out.println("in Action class vcb..........." + queryInfo);
			if (!queryInfo.isEmpty()) {
				request.setAttribute("Queryvector", queryInfo);
				request.setAttribute("status", UtilConstants._QUERY_INFO);
				path = UtilConstants._DISPLAY_QUERY;
			} else {
				request.setAttribute("status", UtilConstants._QUERY_INFO);
				path = UtilConstants._DISPLAY_QUERY;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._DISPLAY_QUERY;
			e.printStackTrace();
		}
		
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
