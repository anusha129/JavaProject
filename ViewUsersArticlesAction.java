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

/**
 * The View UsersArticlesAction Class represents the view the articles 
 * which are posted by the faculty or student can view their own articles.
 */
public class ViewUsersArticlesAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		String requesttype = request.getParameter("requesttype");
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		// int userid =Integer.parseInt(request.getParameter("userid"));
		String user = request.getParameter("user");
		
		System.out.println(user);
		// String useridref=request.getParameter("userid");
		Vector<ArticleTo> vato = null;
		try {
			vato = new ArticleMgrDelegate().viewusersarticles(user);
			
			System.out.println(vato);
			if (!vato.isEmpty()) {
				request.setAttribute("status", UtilConstants._VIEW_ARTICLES);
				request.setAttribute("articlesinfo", vato);
				request.setAttribute("user", user);
				if (requesttype.equals("view"))
					path = UtilConstants._VIEW_USERS_ARTICLES;
				else
					path = UtilConstants._UPDATE_USER_ARTICLES;
			} else {
				request.setAttribute("status",
						UtilConstants._NO_ARTICLES_AVAILABLE);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_USERS_ARTICLES;
			}
		}catch (ConnectionException e) {
			
			request.setAttribute("status", e.getMessage());
			path=UtilConstants._VIEW_USERS_ARTICLES;
			
		}catch (Exception e) {
			request
					.setAttribute("status",
							UtilConstants._NO_ARTICLES_AVAILABLE);
			request.setAttribute("user", user);
			path = UtilConstants._VIEW_USERS_ARTICLES;
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);

	}}
}
