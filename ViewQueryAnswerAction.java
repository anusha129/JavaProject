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
import com.collegemagazine.formbeans.Queryformbean;
import com.collegemagazine.util.UtilConstants;


public class ViewQueryAnswerAction extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
doPost(request, response);
}


	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
     Queryformbean sb = new Queryformbean();
    
     String path = "";
    try {
	


      Vector<QueryTo> queryStatus = null;

	
	String login = request.getParameter("from");
	System.out.println(login);


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
	
}finally {
RequestDispatcher rd = request.getRequestDispatcher(path);
rd.forward(request, response);
}}
}
