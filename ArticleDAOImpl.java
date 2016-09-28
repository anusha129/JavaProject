package com.collegemagazine.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleCallableStatement;

import com.collegemagazine.bean.ArticleTo;
import com.collegemagazine.daoI.ArticleDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.dbutil.SqlConstants;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.util.Corehash;
import com.collegemagazine.util.DateWrapper;
import com.collegemagazine.util.LoggerManager;


public class ArticleDAOImpl implements ArticleDAOI {
	public Connection con;
	PreparedStatement psmt,psmt1,pstmt;
	Statement stmt;
	ResultSet rs;

	public void closeConnection() {
		try {

			if (psmt != null)
				psmt.close();

			if (con != null)
				con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public boolean insertarticle(ArticleTo ab) throws ConnectionException {
		boolean flag = false;
		String articlename = ab.getArticlename();
		String articletype = ab.getArticletype();
		String categoryname = ab.getCategoryname();
		String categorytype = ab.getCategorytype();
		String article = ab.getArticledata();
		String userid=ab.getUserid();
		int uid=0;
		System.out.println("userid=" +userid);
		try {
			con = DBConnectionFactory.getConnection();
			System.out.println("article=" + article);
			File f = new File(article);
			FileInputStream fis = new FileInputStream(f);
			System.out.println("fole=" + f.length());
			String fileType = "";
			String fullpath = article;
			String[] extension = fullpath.split("\\.");
			int i;
			int j=0;
			for (i = 0; i < extension.length; i++) {
				System.out.println(extension[i]);
				if (extension[i].equals("txt") || extension[i].equals("doc")
						|| extension[i].equals("pdf"))
					fileType = extension[i];
				
			}
			
			 pstmt=con.prepareStatement("select userid from userdetails where loginid=?");
			 pstmt.setString(1, userid);
			 rs=pstmt.executeQuery();
			 
			 
			 if(rs.next()) {
					
					uid=rs.getInt(1);
					System.out.println("useridddd"+uid);		
				
				}
			psmt = con.prepareStatement("insert into ARTICLES values(?,(select nvl(max(articleid),1000)+1 from articles),?,?,sysdate,?,?,'activate')");
			psmt.setInt(1, uid);
	          System.out.println("userid==="+uid);
	        psmt.setString(2, articletype);
	        System.out.println(articletype);
	        psmt.setString(3, articlename);
	        System.out.println(articlename);
	        
	        File file4 = new File(article);
			FileInputStream fis4 = new FileInputStream(file4);
			
			psmt.setBinaryStream(4, fis4, (int) file4.length());
	        psmt.setString(5, fileType);
	        System.out.println(fileType);
	        i = psmt.executeUpdate();
			System.out.println("iiiiiiiiiiiiii="+i);
			
			if (i == 1) {
				
				psmt1 = con.prepareStatement("insert into categories values((select max(articleid) from articles), (select nvl(max(categoryid),1000)+1 from categories),?,?)");
				psmt1.setString(1, categorytype);
		        psmt1.setString(2, categoryname);
		       
		        j = psmt1.executeUpdate();
		        
		        System.out.println("jjjjjjjjjjjjjj="+j);
				
				if(j == 1)
				{
				flag = true;
				}
			} else 
			
			{
				flag = false;
			}
			con.commit();
		} catch (SQLException e) {
			
			
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * This Method InsertComments implements posted comments which are posted by
	 * article reader.
	 * 
	 * @param article
	 *            is a variable which represents the Article name that is read
	 *            by the user.
	 * 
	 @param comment
	 *            is a variable which indicates the comments on the particular
	 *            article.
	 * 
	 @param name
	 *            is a variable which represents the name of the particular
	 *            article reader.
	 * 
	 @return flag is a variable which is gives the answer either true or false.
	 */
	public boolean insertcomment(ArticleTo ab) throws ConnectionException {
		boolean flag = false;
		String article = ab.getArticle();
		String comment = ab.getComment();
		String name = ab.getName();
		try {
			con = DBConnectionFactory.getConnection();
			CallableStatement cstmt = con
					.prepareCall("{call insertcomments(?,?,?)}");
			cstmt.setString(1, article);
			cstmt.setString(2, comment);
			cstmt.setString(3, name);
			int i = cstmt.executeUpdate();
			if (i >= 0) {
				stmt = con.createStatement();
				flag = true;
			} else {
				flag = false;
			}
			con.commit();
		} catch (SQLException e) {
			System.out.println(e.toString());
			if (e
					.toString()
					.equalsIgnoreCase(
							"java.sql.SQLException: [Microsoft][ODBC driver for Oracle][Oracle]ORA-12571: TNS:packet writer failure")) {
				flag = true;
				System.out.println("n===" + flag);
			}
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return flag;
	}
	public Vector<ArticleTo> viewcomments(String articlename)
	throws ConnectionException {
Vector<ArticleTo> vat = new Vector<ArticleTo>();
// System.out.println("article:"+article);
vat.clear();
try {
	con = DBConnectionFactory.getConnection();
	psmt = con.prepareStatement(SqlConstants._VIEW_COMMENTS);
	// psmt.setString(1, article);
	rs = psmt.executeQuery();
	
	while (rs.next()) {
		
		ArticleTo ab = new ArticleTo();
		ab.setCommentid(rs.getInt(1));
		ab.setArticlename(rs.getString(2));
		ab.setComment(rs.getString(3));
		ab.setName(rs.getString(4));
		
		vat.add(ab);
	}
} catch (Exception e) {
	e.printStackTrace();
}
return vat;
}

	/**
	 * This ViewArticle method displays the list of articles when the user
	 * searching for the articles based on the article name it display the
	 * article list
	 * 
	 * @return vat is variable which represents the vector value.
	 */
	public Vector<ArticleTo> viewarticles(String realpath)
			throws ConnectionException {
		Vector<ArticleTo> vat = new Vector<ArticleTo>();
		// System.out.println("article:"+article);
		vat.clear();
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._VIEW_ARTICLES);
			// psmt.setString(1, article);
			rs = psmt.executeQuery();
			System.out.println(" path :" + realpath);
			while (rs.next()) {
				String path = realpath;
				ArticleTo ab = new ArticleTo();
				ab.setArticlename(rs.getString(1));
				ab.setArticletype(rs.getString(2));
				ab.setCategoryname(rs.getString(3));
				ab.setCategorytype(rs.getString(4));
				ab.setPosteddate(rs.getString(5));
				ab.setArticledata(path);
				vat.add(ab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vat;
	}

	/**
	 * This Search article method represents the searching articles and displays
	 * the articles list in the browser
	 * 
	 * @return vato is a variable gives the vector
	 */
	public Vector<ArticleTo> searcharticle(String realpath, String articlename)
			throws ConnectionException {
		Vector<ArticleTo> vato = new Vector<ArticleTo>();
		System.out.println("article:" + articlename);
		vato.clear();
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con
					.prepareStatement("select  articlename,articletype,article_data from articles where articlename like '"
							+ articlename + "%'");
			rs = psmt.executeQuery();
			System.out.println("path is:" + realpath);
			while (rs.next()) {
				String path = realpath;
				ArticleTo ab = new ArticleTo();
				ab.setArticlename(rs.getString(1));
				ab.setArticletype(rs.getString(2));
				Blob b = rs.getBlob(3);
				byte b1[] = b.getBytes(1, (int) b.length());
			//	path = path + "/" + rs.getString(1) + ".";
				System.out.println("path  :" + path);
				OutputStream fout = new FileOutputStream(path +  rs.getString(1)
						+ ".");
				fout.write(b1);
				/*
				 * byte b5[]=b4.getBytes(1,(int)b4.length()); OutputStream
				 * fout2=new FileOutputStream(storepath+"/"+id+".doc");
				 * fout2.write(b5);
				 */
				ab.setArticledata(path+rs.getString(1)+ ".");
				vato.add(ab);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return vato;
	}

	/**
	 * This ViewArticle method displays the list of articles when the user
	 * searching for the articles based on the article name it display the
	 * article list
	 * 
	 * @return vato is variable which represents the vector value.
	 * @return article data is variable which gives the data of the particular
	 *         article
	 */
	public Vector<ArticleTo> viewdata(String realpath, String articlename)
			throws ConnectionException {
		Vector<ArticleTo> vato = new Vector<ArticleTo>();
		vato.clear();
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._SEARCH_ARTICLE);
			rs = psmt.executeQuery();
			System.out.println("path is:" + realpath);
			while (rs.next()) {
				String path = realpath;
				ArticleTo ab = new ArticleTo();
				ab.setArticlename(rs.getString(1));
				Blob b = rs.getBlob(2);
				byte b1[] = b.getBytes(1, (int) b.length());
				path = path + "/" + rs.getString(1);
				System.out.println("path  :" + path);
				OutputStream fout = new FileOutputStream(path);
				fout.write(b1);
				/*
				 * byte b5[]=b4.getBytes(1,(int)b4.length()); OutputStream
				 * fout2=new FileOutputStream(storepath+"/"+id+".doc");
				 * fout2.write(b5);
				 */
				ab.setArticledata(path);
				vato.add(ab);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return vato;
	}

	/**
	 * This View Articles method is used to view user's own articles posted by
	 * themselves
	 * 
	 * @return vato is variable which returns the article list.
	 */
	public Vector<ArticleTo> viewusersarticles(String user)
			throws ConnectionException {
		Vector<ArticleTo> vato = new Vector<ArticleTo>();
		vato.clear();
		System.out.println("Userid :" + user);
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._VIEW_USERS_ARTICLES);
			psmt.setString(1, user);
			System.out.println("user :" + user);
			rs = psmt.executeQuery();
			while (rs.next()) {
				System.out.println("iteration starts");
				ArticleTo ab = new ArticleTo();
				// ab.setArticleid(rs.getInt(1));
				// System.out.println("Userid :" +rs.getInt(1));
				ab.setArticlename(rs.getString(1));
				ab.setArticletype(rs.getString(2));
				ab.setCategoryname(rs.getString(3));
				ab.setCategorytype(rs.getString(4));
				ab.setPosteddate(rs.getString(5));
				vato.add(ab);
				
				System.out.println("The size of ===========>"+vato.size());
				
				
				
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return vato;
	}

	/**
	 * This Update Articles method indicates the modifying the article
	 * information
	 * 
	 * @param aname
	 *            is a variable which represents the article name.
	 * @param atype
	 *            is a variable which indicates the article type.
	 * @param cname
	 *            is a variable which shows the category name.
	 * @param ctype
	 *            is the variable which indicates category type.
	 * @return flag is a variable which give the result true or false
	 */
	public boolean updatearticles(ArticleTo ato) throws ConnectionException {
		boolean flag = false;
		String aname = ato.getArticlename();
		String atype = ato.getArticletype();
		String cname = ato.getCategoryname();
		String pdate = DateWrapper.parseDate(ato.getPosteddate());
		String ctype = ato.getCategorytype();
		try {
			con = DBConnectionFactory.getConnection();
			CallableStatement cstmt = con
					.prepareCall("{call updatearticles(?,?,?,?,?)}");
			cstmt.setString(1, atype);
			cstmt.setString(2, aname);
			cstmt.setString(3, pdate);
			cstmt.setString(4, ctype);
			cstmt.setString(5, cname);
			int i = 0;
			i = cstmt.executeUpdate();
			if (i >= 1) {
				flag = false;
			} else {
				flag = true;
				con.commit();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
			if (e
					.toString()
					.equalsIgnoreCase(
							"java.sql.SQLException: [Microsoft][ODBC driver for Oracle][Oracle]ORA-12571: TNS:packet writer failure")) {
				flag = true;
				System.out.println("n===" + flag);
			}
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * This Delete Articles method represents the article deletion
	 */
	public boolean deletearticle(int articleid) throws ConnectionException {
		boolean flag = false;
		try {
			
			System.out.println("Article id=========>"+articleid);
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._DELETE_ARTICLES);
			psmt.setInt(1, articleid);
			int i = psmt.executeUpdate();
			if (i > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		}

		finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return flag;
	}
	
	public boolean rejectarticle(int articleid) throws ConnectionException {
		boolean flag = false;
		try {
			
			System.out.println("Article id=========>"+articleid);
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._REJECT_ARTICLES);
			psmt.setInt(1, articleid);
			int i = psmt.executeUpdate();
			if (i > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		}

		finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return flag;
	}

	/**
	 * This Accept Articles method represents the accepting the article which
	 * are posted by faculty or student.The moderator can access the article.
	 */
	public boolean acceptarticles(ArticleTo ato) throws ConnectionException {
		boolean flag = false;
		// Vector<ArticleTo> vato = new Vector<ArticleTo>();
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._ACCEPT_ARTICLE);
			psmt.setString(1, ato.getStatus());
			psmt.setInt(2, ato.getArticleid());
			int i = psmt.executeUpdate();
			if (i > 0) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		}

		finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return flag;
	}

	/**
	 * This View Articles Acceptance method implements viewing Articles posted
	 * by the faculty and students
	 * 
	 * @return vat is a variable which returns the vector
	 */
	public Vector<ArticleTo> viewarticleacceptance(String realpath)
			throws ConnectionException {
		Vector<ArticleTo> vat = new Vector<ArticleTo>();
		// System.out.println("article:"+article);
		vat.clear();
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(SqlConstants._VIEW_ARTICLE);
			// psmt.setString(1, article);
			rs = psmt.executeQuery();
			System.out.println(" path :" + realpath);
			while (rs.next()) {
				@SuppressWarnings("unused")
				String path = realpath;
				ArticleTo ab = new ArticleTo();
				ab.setArticleid(rs.getInt(1));
				ab.setArticlename(rs.getString(2));
				ab.setArticletype(rs.getString(3));
				ab.setPosteddate(rs.getString(4));
				ab.setCategoryname(rs.getString(5));
				ab.setCategorytype(rs.getString(6));
				ab.setStatus(rs.getString(7));
				// ab.setArticledata(path);
				vat.add(ab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vat;
	}

	/**
	 * getReportPresent method implements the Report generation if there any
	 * reports are available in the system
	 */
	@SuppressWarnings("unchecked")
	 public Corehash getReportPresent()
 	{
 		
 		
 		Corehash aCoreHash = new Corehash();
 		aCoreHash.clear();
 		System.out.println("aCoreHash--"+aCoreHash.isEmpty());
 		int sno=1;
 		CallableStatement cstmt;
 		ArticleTo ato=null;
 		try {
 			try {
				con = DBConnectionFactory.getConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			   
 			
             	 cstmt=con.prepareCall("{call REF_CURSOR_TEST.GET_ACCOUNTS_PROCEDURE(?)}");
             	cstmt.registerOutParameter(1,OracleTypes.CURSOR);
             	 cstmt.executeUpdate();
             	 ResultSet rs=((OracleCallableStatement)cstmt).getCursor(1);
             	  while(rs.next())
                  {
               	   ato=new ArticleTo();
               	   ato.setArticlename(rs.getString(1));
               	   ato.setArticletype(rs.getString(2));
               	   ato.setCategoryname(rs.getString(3));
               	   ato.setCategorytype(rs.getString(4));
               
               	   ato.setPosteddate(rs.getString(5));
               	 aCoreHash.put(new Integer(sno),ato);
  			    sno++;
  		  
                  }
 		}
 		catch(SQLException e)
 		{e.printStackTrace();
 			LoggerManager.writeLogWarning(e);
 		}
 		finally
 		{
 		 try{
 			 if(con!=null)
 				 con.close();
 			 
 		 }
 		 catch(SQLException e){}
 		}
 		return aCoreHash;
 	}
}
