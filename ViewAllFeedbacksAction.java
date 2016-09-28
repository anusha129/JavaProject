package com.collegemagazine.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import com.collegemagazine.bean.FeedBackTo;
import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.exception.DataNotFoundException;
import com.collegemagazine.formbeans.FeedBackformbean;
import com.collegemagazine.util.UtilConstants;

/*
 * This ViewAllFeedbacksAction Class indicate to view all the feedbacks 
 * by administrator which are posted by the article reader in the system
 */
public class ViewAllFeedbacksAction extends HttpServlet {
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
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FeedBackformbean fb = new FeedBackformbean();
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(fb, map);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		String path = "";
		Vector<FeedBackTo> feedbackInfo = null;
		try {
		
				feedbackInfo = new QueryMgrDelegate().getFeedBack();
			
			if (!feedbackInfo.isEmpty()) {
				request.setAttribute("Feedbackvector", feedbackInfo);
				request.setAttribute("status",
						UtilConstants._VIEW_ALL_FEEDBACKS);
				path = UtilConstants._VIEW_ALL_FEEDBACK;
			} else {
				request.setAttribute("status",
						UtilConstants._ALL_FEEDBACKS_FAILED);
				path = UtilConstants._VIEW_ALL_FEEDBACK;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
			request.setAttribute("status", e.getMessage());
			//path = "./jsps/viewallfeedbacks.jsp?status=Invalid Entries";
			path = "./jsps/viewallfeedbacks.jsp";
		
		}catch (DataNotFoundException e) {
			// TODO: handle exception
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
