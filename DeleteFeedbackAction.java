package com.collegemagazine.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.collegemagazine.delegate.QueryMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.FeedBackformbean;


public class DeleteFeedbackAction extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path="";
        Vector<FeedBackformbean> allFeedbacks=null;
        boolean flag=false;

	  try{
		  FeedBackformbean f=new FeedBackformbean();
		  
		 int fid=Integer.parseInt(request.getParameter("fid"));
		 System.out.println("id is "+fid);
		f.setFid(fid);
		  flag= new QueryMgrDelegate().cancelFeedBack(fid);
		 
		  System.out.println("in Action class vcb Feedback..........."+allFeedbacks);
		   if(flag)
		  {
			  request.setAttribute("FeedBackVector", allFeedbacks);
			  request.setAttribute("status", " Feedbacks Deleted Successfully");
			  path="./ViewFeedBackAction";
			 }
		  else {
			  request.setAttribute("status", "Deletion of Feedback is failed");
			  path="./ViewFeedBackAction";
			}
		  }
	  catch (NullPointerException e) {
		  request.setAttribute("status", "Invalid data");
		  path="./ViewFeedBackAction";
		e.printStackTrace();		
	} catch (ConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	RequestDispatcher rd=request.getRequestDispatcher(path);
		rd.forward(request,response);
	}

}
