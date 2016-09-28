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
 * This ViewArticleAction Class displays  the list of articles when the user 
 * searching for the  articles based on the article name it display the article list 
 */
public class ViewArticleAction extends HttpServlet {
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
		String requesttype = request.getParameter("requesttype");
		HttpSession session = request.getSession();
		Vector<ArticleTo> vato = null;
		try {
			path = request.getRealPath("./data");
			vato = new ArticleMgrDelegate().viewarticles(path);
			if (!vato.isEmpty()) {
				request.setAttribute("status", UtilConstants._VIEW_ARTICLES);
				request.setAttribute("articleinfo", vato);
				path = UtilConstants._DISPLAY_ARTICLES;
			} else {
				request.setAttribute("status",
						UtilConstants._NO_ARTICLES_AVAILABLE);
				path = UtilConstants._DISPLAY_ARTICLES;
			}
		} 
		catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path=UtilConstants._DISPLAY_ARTICLES;
		}
		catch (Exception e) {
			request
					.setAttribute("status",
							UtilConstants._NO_ARTICLES_AVAILABLE);
			path = UtilConstants._DISPLAY_ARTICLES;
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
}
