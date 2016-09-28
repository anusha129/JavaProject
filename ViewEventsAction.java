package com.collegemagazine.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.collegemagazine.bean.EventsTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

/**
 * This ViewEventsAction class retrieving the events details 
 * which are posted by students and display it in the screen 
 */
public class ViewEventsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		String eventname = request.getParameter("eventname");
		Vector<EventsTo> opv = null;
		try {
			opv = new QueryMgrDelegate().viewevents(eventname);
			if (!opv.isEmpty()) {
				request.setAttribute("eventsinfo", opv);
				request.setAttribute("eventname", eventname);
				path = UtilConstants._VIEW_EVENTS;
			} else if (opv.isEmpty()) {
				request.setAttribute("status", UtilConstants._NO_EVENTS);
				request.setAttribute("eventname", eventname);
				path = UtilConstants._VIEW_ALL__EVENTS;
				throw new NullPointerException("No Events are Available");
			} else {
				request.setAttribute("status", UtilConstants._NO_EVENTS);
				request.setAttribute("eventname", eventname);
				path = UtilConstants._VIEW_ALL__EVENTS;
			}
		}
		
		catch (ConnectionException e) {
			request.setAttribute("status",e.getMessage());
			path=UtilConstants._VIEW_ALL__EVENTS;
		}
		catch (Exception e) {
			request.setAttribute("status", UtilConstants._INVALIED_ENTRY);
			path = UtilConstants._VIEW_ALL__EVENTS;
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		}
	}

}
