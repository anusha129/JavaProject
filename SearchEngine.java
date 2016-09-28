package com.collegemagazine.action;

import java.util.*;
import java.io.*;

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
 * This SearchEngineAction Class represents the  searching
 *  articles and displays the articles list in the browser
 */
public class SearchEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	@SuppressWarnings({ "unused", "deprecation" })
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
			path = request.getRealPath("/images");
			vato = new ArticleMgrDelegate().searcharticle(path, articlename);
			if (!vato.isEmpty()) {
				request.setAttribute("status", UtilConstants._VIEW_ARTICLES);
				request.setAttribute("articleinfo", vato);
				path = UtilConstants._SEARCHED_ARTICLE;
			} else {
				request.setAttribute("status",
						UtilConstants._NO_ARTICLES_AVAILABLE);
				path = UtilConstants._SEARCHED_ARTICLE;
			}
		} 
		catch (ConnectionException e) {
			request.setAttribute("status",e.getMessage());
			path=UtilConstants._SEARCHED_ARTICLE;
		}
		catch (Exception e) {
			request
					.setAttribute("status",
							UtilConstants._NO_ARTICLES_AVAILABLE);
			path = UtilConstants._SEARCHED_ARTICLE;
			System.out.println(e);
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		/*
		 * try{
		 * 
		 * br = new BufferedReader(new FileReader(kb.nextLine()));//reads
		 * information from the file specified by user inputSystem.out.println(
		 * "The file was read. Processing information, please wait...");
		 * 
		 * while(br.ready()){//should repeat until there are no more lines to
		 * read String line = br.readLine();//assigns the line read by the
		 * reader to line String[] result = line.split("\\s");//tokenizes the
		 * line into seperate strings, based on spaces only
		 * 
		 * for(int i = 0; i < result.length; i++){
		 * if(!ht.containsKey(result[i])){ ArrayList<String> temp = new
		 * ArrayList<String>(1); temp.add(line); ht.put(result[i],
		 * temp);//assigns a key to anonymous ArrayList that stores the value
		 * 
		 * }
		 * 
		 * else{ ArrayList<String> temp =
		 * (ArrayList<String>)ht.get(result[i]);//if the key has already been
		 * assigned, thats ok temp.add(line); //just add the argument to the
		 * ArrayList!
		 * 
		 * } } } } catch(Exception e){ System.out.println(e); System.exit(1); }
		 * System.out.println(ht); do{System.out.println(
		 * "Enter a key to search for the value it is associated with.\n");
		 * System.out.println(ht.get(kb.nextLine()));System.out.println(
		 * "\nKeep searching? Enter any key to continue, or type <NO> to end the process"
		 * ); }while(!kb.nextLine().equalsIgnoreCase("<NO>")); try{ br.close();
		 * } catch(Exception e){ System.out.println(e); System.exit(1);
		 * 
		 * } RequestDispatcher
		 * rd=request.getRequestDispatcher("./jsps/home.jsp");
		 * rd.forward(request, response);
		 */
	}
}// end class