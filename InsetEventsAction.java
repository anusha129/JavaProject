package com.collegemagazine.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.collegemagazine.bean.EventsTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * The InsertEventAction class implements 
 * the adding events which are stored into the Database
 */
public class InsetEventsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		EventsTo eb = new EventsTo();
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(eb, map);
				flag = new QueryMgrDelegate().insertevents(eb);
			
			if (flag) {
				request.setAttribute("status", UtilConstants._INSERT_EVENTS);
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute("status",
						UtilConstants._EVENTS_INSERTION_FAILED);
				path = UtilConstants._STATUS;
			}
		}
		catch (ConnectionException e) {
		request.setAttribute("status",e.getMessage());
		path=UtilConstants._STATUS;
		
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("status", UtilConstants._EVENTS_INSERTION_FAILED);
			path = UtilConstants._STATUS;
		}finally{
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
