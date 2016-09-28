package com.collegemagazine.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
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
 * This ViewArticleDataAction Class represents the Article Data 
 * Using this class the user can be down load the article data 
 */
public class ViewArticleDataAction extends HttpServlet {
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
	@SuppressWarnings( { "unused", "deprecation" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Hashtable<String, ArrayList<String>> ht = new Hashtable<String, ArrayList<String>>();
		Scanner kb = new Scanner(System.in);
		BufferedReader br = null;
		String path = "";
		String articlename = "";
		HttpSession session = request.getSession();
		Vector<ArticleTo> vato = null;
		try {
			path = request.getRealPath("./data");
			vato = new ArticleMgrDelegate().viewdata(path, articlename);
			if (!vato.isEmpty()) {
				request.setAttribute("status", UtilConstants._VIEW_ARTICLE);
				request.setAttribute("articleinfo", vato);
				path = UtilConstants._VIEW_DATA;
			} else {
				request.setAttribute("status", UtilConstants._VIEW_NO_ARTICLE);
				path = UtilConstants._VIEW_DATA;
			}
		}
		catch (ConnectionException e) {
			request.setAttribute("status",UtilConstants._VIEW_NO_ARTICLE);
			path=UtilConstants._VIEW_DATA;
		}
		
		catch (Exception e) {
			request.setAttribute("status", UtilConstants._VIEW_NO_ARTICLE);
			path = UtilConstants._VIEW_DATA;
			System.out.println(e);
		}finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}}
