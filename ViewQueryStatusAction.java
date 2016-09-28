package com.collegemagazine.action;

import java.io.IOException;

import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.collegemagazine.bean.QueryTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.Queryformbean;

import com.collegemagazine.util.UtilConstants;

/**
 * The ViewQueryStatusAction Class represents the status of the query which is posted 
 * by the faculty or student then they can view the solution depending on the query
 */
public class ViewQueryStatusAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Queryformbean sb = new Queryformbean();
		Map map = request.getParameterMap();
		String path = "";
		try {
			BeanUtils.populate(sb, map);
		
	
		Vector<QueryTo> queryStatus = null;

			int qid = Integer.parseInt(request.getParameter("qid"));
			String login = request.getParameter("from");
		
	
				queryStatus = new QueryMgrDelegate().getQueryStatus(login);
			
			System.out.println("QueruStatus is" + queryStatus);
			if (!queryStatus.isEmpty()) {
				request.setAttribute("Statusvector", queryStatus);
				request.setAttribute("status", UtilConstants._QUERY_STATUS);
				path = UtilConstants._DISPLAY_QUERY_STATUS;
			} else {
				request.setAttribute("status", UtilConstants._NO_QUERY_STATUS);
				path = UtilConstants._DISPLAY_QUERY_STATUS;
			}
		} catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._DISPLAY_QUERY_STATUS;
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
