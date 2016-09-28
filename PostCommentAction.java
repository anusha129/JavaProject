package com.collegemagazine.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.collegemagazine.bean.ArticleTo;
import com.collegemagazine.delegate.ArticleMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.Articleformbean;
import com.collegemagazine.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * This PostCommentsAction class implements inserting the Comments into the 
 * Database posted by theUsers of the system about the different Articles
 */
public class PostCommentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		Articleformbean ab = new Articleformbean();
		HttpSession hs = request.getSession();
		hs.setAttribute("articlebean", ab);
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(ab, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String path = "";
		boolean flag = false;
		try {
			ArticleTo ato = new ArticleTo(ab);
			
				flag = new ArticleMgrDelegate().insertcomment(ato);
			
			if (flag == true) {
				request.setAttribute("status", UtilConstants._INSERT_COMMENTS);
				path = UtilConstants._COMMENTS_HOME;
			} else {
				request.setAttribute("status", UtilConstants._COMMENTS_FAILED);
				path = UtilConstants._COMMENTS_HOME;
			}
		}
		catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path=UtilConstants._COMMENTS_HOME;
		}
		catch (Exception e) {
			request.setAttribute("status", UtilConstants._COMMENTS_FAILED);
			path = UtilConstants._COMMENTS_HOME;
			e.printStackTrace();
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}}
