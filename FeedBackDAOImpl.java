package com.collegemagazine.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.collegemagazine.bean.FeedBackTo;
import com.collegemagazine.daoI.FeedBackDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.dbutil.SqlConstants;
import com.collegemagazine.exception.ConnectionException;

/**
 *The FeedBackDAOImpl Class implements the add feedback action,view feedbacks
 * cancel feedback action
 */
public class FeedBackDAOImpl implements FeedBackDAOI {
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	Connection con;

	public void closeConnection() {
		try {

			if (pstmt != null)
				pstmt.close();

			if (con != null)
				con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Insert FeedBacks(non-Javadoc)Method implements the adding feedbacks into
	 * the database
	 * 
	 * @see com.collegemagazine.daoI.FeedBackDAOI#insertFeedBack(com.collegemagazine.bean.FeedBackTo)
	 * @param title
	 *            is a variable which represents the title of the article
	 * @param name
	 *            is a variable which represent the name who post the feedback
	 * @param address
	 *            is a variable which indicates the address of the person who
	 *            posted the feedback
	 * @param email
	 *            is a variable which shows the email of the person
	 * @return flag is a variable which returns the inserted values
	 */
	public boolean insertFeedBack(FeedBackTo fb)throws ConnectionException {
		boolean flag = true;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._INSERT_FEEDBACK);
			String title = fb.getTitle();
			String feedback = fb.getFeedback();
			String name = fb.getName();
			String addr = fb.getAddress();
			String phone = fb.getPhone();
			String email = fb.getEmail();
			pstmt.setString(1, title);
			pstmt.setString(2, feedback);
			pstmt.setString(3, name);
			pstmt.setString(4, addr);
			pstmt.setString(5, phone);
			pstmt.setString(6, email);
			int insert = pstmt.executeUpdate();
			if (insert > 0) {
				con.commit();
			} else {
				flag = false;
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {closeConnection();}
		return flag;
	}

	/**
	 * The ViewFeedBackAction Class represents the administrator can view the
	 * feedbacks which are posted by students and faculty from the system
	 * 
	 * @return vfb is a variable which returns the vector
	 */
	public Vector<FeedBackTo> getFeedBack()throws ConnectionException {
		Vector<FeedBackTo> vfb = null;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_FEEDBACK);
			rs = pstmt.executeQuery();
			vfb = new Vector<FeedBackTo>();
			while (rs.next()) {
				int fid = rs.getInt(1);
				String title = rs.getString(2);
				String feedback = rs.getString(3);
				String username = rs.getString(4);
				String address = rs.getString(5);
				String email = rs.getString(6);
				String phone = rs.getString(7);
				FeedBackTo fb = new FeedBackTo();
				fb.setFid(fid);
				fb.setTitle(title);
				fb.setFeedback(feedback);
				fb.setName(username);
				fb.setAddress(address);
				fb.setEmail(email);
				fb.setPhone(phone);
				vfb.add(fb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return vfb;
	}

	/**
	 * View GetAllFeedbacks(non-Javadoc)Method implements to view all feedbacks
	 * posted by users
	 * 
	 * @see com.collegemagazine.daoI.FeedBackDAOI#getAllFeedBack(int) This
	 *      ViewAllFeedbacksAction Class indicate to view all the feedbacks by
	 *      administrator which are posted by the article reader in the system
	 * @return vfb is a variable which returns the vector
	 */
	public Vector<FeedBackTo> getAllFeedBack(int fid1)throws ConnectionException {
		Vector<FeedBackTo> vfb = null;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_FEEDBACK_AT);
			pstmt.setInt(1, fid1);
			rs = pstmt.executeQuery();
			vfb = new Vector<FeedBackTo>();
			while (rs.next()) {
				int fid = rs.getInt(1);
				String feedback = rs.getString(2);
				String username = rs.getString(3);
				String address = rs.getString(4);
				String email = rs.getString(5);
				String title = rs.getString(6);
				String phone = rs.getString(7);
				FeedBackTo fb = new FeedBackTo();
				fb.setAddress(address);
				fb.setTitle(title);
				fb.setEmail(email);
				fb.setFeedback(feedback);
				fb.setPhone(phone);
				fb.setName(username);
				fb.setFid(fid);
				vfb.add(fb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return vfb;
	}

	/**
	 * Cancel Feedbacks(non-Javadoc) implements the deleting the feedbacks which
	 * are posted by the users
	 * 
	 * @see com.collegemagazine.daoI.FeedBackDAOI#cancelFeedBack(int)
	 * @return flag which is a variable returns the flag value
	 */
	public boolean cancelFeedBack(int fid)throws ConnectionException {
		boolean flag = true;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._DELETE_FEEDBACK);
			pstmt.setInt(1, fid);
			int delete = pstmt.executeUpdate();
			if (delete > 0) {
				con.commit();
			} else {
				flag = false;
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		finally {
			closeConnection();
		}
		return flag;
	}
}
