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

import com.sun.org.apache.commons.beanutils.BeanUtils;

import com.collegemagazine.bean.ArticleTo;
import com.collegemagazine.delegate.ArticleMgrDelegate;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.Articleformbean;
import com.collegemagazine.util.UtilConstants;

/**
 * This PostArticlesAction Class implements 
 * Adding the Articles And Store the Data in to the DataBase
 */
public class PostArticleAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
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
		
				flag = new ArticleMgrDelegate().insertarticle(ato);
			
			if (flag == true) {
				request.setAttribute("status", UtilConstants._INSERT_ARTICLE);
				path = UtilConstants._ARTICLE_HOME;
			} else {
				request.setAttribute("status",
						UtilConstants._ARTICLE_INSERTION_FAILED);
				path = UtilConstants._ARTICLE_HOME;
			}
		} 
		catch (ConnectionException e) {
			request.setAttribute("status", e.getMessage());
			path=UtilConstants._ARTICLE_HOME;
		}
		catch (Exception e) {
			request.setAttribute("status",
					UtilConstants._ARTICLE_INSERTION_FAILED);
			path = UtilConstants._ARTICLE_HOME;
			e.printStackTrace();
		}
		finally {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}}
}
