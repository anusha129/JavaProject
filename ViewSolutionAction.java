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


public class ViewSolutionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Queryformbean sb = new Queryformbean();
		
		String path = "";
		try {
			
		
	
		Vector<QueryTo> getsolution = null;

			int qid = Integer.parseInt(request.getParameter("qid"));
			System.out.println(qid);
			String login = request.getParameter("from");
			
			System.out.println(login);
		
				getsolution = new QueryMgrDelegate().getSolution(qid, login);
			
			System.out.println("in Action class vcb..........." + getsolution);
			if (!getsolution.isEmpty()) {
				request.setAttribute("Solutionvector", getsolution);
				request.setAttribute("status",
						UtilConstants._POST_SOLUTION_INFO);
				path = UtilConstants._VIEW_SOLUTION;
			} else {
				request.setAttribute("status", UtilConstants._NO_SOLUTION);
				path = UtilConstants._VIEW_SOLUTION;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._VIEW_SOLUTION;
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
