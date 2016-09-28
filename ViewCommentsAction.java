package com.collegemagazine.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.collegemagazine.bean.ArticleTo;
import com.collegemagazine.bean.EventsTo;
import com.collegemagazine.delegate.ArticleMgrDelegate;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

public class ViewCommentsAction extends HttpServlet {

	
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
@SuppressWarnings("unused")
HttpSession session = request.getSession();
String articlename = request.getParameter("articlename");
Vector<ArticleTo> opv = null;
try {
	opv = new ArticleMgrDelegate().viewcomments(articlename);
	if (!opv.isEmpty()) {
		request.setAttribute("commentsinfo", opv);
		request.setAttribute("articlename", articlename);
		path = UtilConstants._DISPLAY_COMMENTS;
	} else if (opv.isEmpty()) {
		request.setAttribute("status", UtilConstants._NO_COMMENTS_AVAILABLE);
		request.setAttribute("articlename", articlename);
		path = UtilConstants._DISPLAY_COMMENTS;
		throw new NullPointerException("No Events are Available");
	} else {
		request.setAttribute("status", UtilConstants._NO_COMMENTS_AVAILABLE);
		request.setAttribute("articlename", articlename);
		path = UtilConstants._DISPLAY_COMMENTS;
	}
}

catch (ConnectionException e) {
	request.setAttribute("status",e.getMessage());
	path=UtilConstants._DISPLAY_COMMENTS;
}
catch (Exception e) {
	request.setAttribute("status", UtilConstants._NO_COMMENTS_AVAILABLE);
	path = UtilConstants._DISPLAY_COMMENTS;
}finally {
RequestDispatcher rd = request.getRequestDispatcher(path);
rd.forward(request, response);
}
}

}
