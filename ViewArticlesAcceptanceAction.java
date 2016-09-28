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
import com.collegemagazine.delegate.ArticleMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.UtilConstants;

/*
 * The ViewArticlesAcceptanceAction class represents article posted by 
 * the faculty or student it will be accepted or rejected by moderator 
 */
public class ViewArticlesAcceptanceAction extends HttpServlet {
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
	@SuppressWarnings({ "unused", "deprecation" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		HttpSession session = request.getSession();
		Vector<ArticleTo> vato = null;
		try {
			path = request.getRealPath("./data");
			vato = new ArticleMgrDelegate().viewarticleacceptance(path);
			if (!vato.isEmpty()) {
				request.setAttribute("status", UtilConstants._VIEW_ARTICLES);
				request.setAttribute("acceptarticles", vato);
				path = UtilConstants._DISPLAY_ARTICLE;
			} else {
				request.setAttribute("status",
						UtilConstants._NO_ARTICLES_AVAILABLE);
				path = UtilConstants._DISPLAY_ARTICLE;
			}
		}

		
		catch (ConnectionException e) {
			request.setAttribute("status",UtilConstants._DISPLAY_ARTICLE);
			path=UtilConstants._DISPLAY_ARTICLE;
		}
		catch (Exception e) {
			request
					.setAttribute("status",
							UtilConstants._NO_ARTICLES_AVAILABLE);
			path = UtilConstants._DISPLAY_ARTICLE;
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}}
