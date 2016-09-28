package com.collegemagazine.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import com.collegemagazine.daoI.SecurityDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.profileformbean;
import com.collegemagazine.util.LoggerManager;

/**
 * SecurityDAOImpl Class implements the Login check action,change password
 * action,check user action,forget password action
 */
public class SecurityDAOImpl implements SecurityDAOI {
	Connection con;
	@SuppressWarnings("unused")
	private String desc;
	private boolean flag;
	PreparedStatement pstmt;
	ResultSet rs;

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

	/** Creates a new instance of SecurityDAO */
	/*
	 * public SecurityDAOImpl() { con = DBConnectionFactory.getConnection(); }
	 */
	/**
	 * Login Check(non-Javadoc) method implements the user login check is it
	 * valid user name and password or not
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#loginCheck(com.collegemagazine.formbeans.profileformbean)
	 * @param loginid
	 *            is a variable which represents the authenticated user name
	 * @param password
	 *            is a variable which represents the password of the particular
	 *            user
	 * @return role is a variable which returns role of the particular user
	 */
	public String loginCheck(profileformbean pro) throws ConnectionException {
		String loginid = pro.getLoginid();
		String password = pro.getPassword();
		String role = "ee";
		try {
			con = DBConnectionFactory.getConnection();
			System.out.println("con" + con);
			// con.setAutoCommit(true);
			CallableStatement cstmt = con
					.prepareCall("{call logincheck(?,?,?)}");
			cstmt.setString(1, loginid);
			cstmt.setString(2, password);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			boolean flag = cstmt.execute();
			System.out.println("flag->" + flag);
			role = cstmt.getString(3);
			System.out.println("logintype=" + role);
		} catch (SQLException ex) {
			System.out.println(ex);
			/*
			 * ex.printStackTrace(); LoggerManager.writeLogSevere(ex); desc =
			 * "Database Connection problem";
			 */flag = false;
			throw new ConnectionException(
					"some Technical problem occured During the login page");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			closeConnection();
		}
		return role;
	}

	/**
	 * Method for login audit(non-Javadoc) method represents the logout action
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#loginaudit(java.lang.String)
	 */
	public void loginaudit(String loginid) throws ConnectionException {
		try {
			con = DBConnectionFactory.getConnection();
			CallableStatement cstmt = con
					.prepareCall("{call signoutprocedure(?)}");
			cstmt.setString(1, loginid);
			cstmt.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	/**
	 * Change Password(non-Javadoc) method represents the change the user
	 * password for the user specification
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#changePassword(com.collegemagazine.formbeans.profileformbean)
	 * @param loginid
	 *            is a variable which represents the authenticated user name
	 * @param oldpassword
	 *            is a variable which represents the password of the particular
	 *            user
	 *@param newpassword
	 *            is a variable which represents the password of the particular
	 *            user
	 *@return flag which is the variable represents the flag value
	 */
	public boolean changePassword(profileformbean pro)
			throws ConnectionException {
		try {
			con = DBConnectionFactory.getConnection();
			String loginid = pro.getLoginid();
			String oldpassword = pro.getPassword();
			String newpassword = pro.getNewpassword();
			try {
				con.setAutoCommit(false);
				CallableStatement cstmt = con
						.prepareCall("{call changePassword(?,?,?,?)}");
				cstmt.setString(1, loginid);
				cstmt.setString(2, oldpassword);
				cstmt.setString(3, newpassword);
				cstmt.registerOutParameter(4, Types.INTEGER);
				cstmt.execute();
				int i = cstmt.getInt(4);
				System.out.println("i=" + i);
				if (i == 1) {
					flag = true;
					con.commit();
				} else {
					flag = false;
					con.rollback();
				}
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				LoggerManager.writeLogSevere(ex);
				flag = false;
				try {
					con.rollback();
				} catch (SQLException sex) {
					LoggerManager.writeLogSevere(sex);
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				try {
					con.rollback();
				} catch (SQLException sex) {
					sex.printStackTrace();
					LoggerManager.writeLogSevere(sex);
				}
			}
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return flag;
	}

	/**
	 * Change Secret Question(non-Javadoc)method represents the check the
	 * security question for providing the security for their profiles
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#changeQuestion(com.collegemagazine.formbeans.profileformbean)
	 * @param loginid
	 *            is a variable which represents the authenticated user name
	 * @param oldpassword
	 *            is a variable which represents the password of the particular
	 *            user
	 * @param secretequestid
	 *            is a variable which represents the authenticated their profile
	 * @param secretans
	 *            is a variable which represents the authenticated their profile
	 *            user
	 *@return flag which is the variable represents the flag value
	 */
	public boolean changeQuestion(profileformbean regbean)
			throws ConnectionException {
		String loginid = regbean.getLoginid();
		String password = regbean.getPassword();
		String secretquestid = regbean.getSquest();
		String secretans = regbean.getSecrete();
		CallableStatement cstmt;
		int i = 0;
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(false);
			cstmt = con.prepareCall("{call ChangeQuetion(?,?,?,?,?)}");
			cstmt.setString(1, loginid);
			cstmt.setString(2, password);
			cstmt.setString(3, secretquestid);
			cstmt.setString(4, secretans);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.execute();
			i = cstmt.getInt(5);
			if (i == 1) {
				flag = true;
				con.commit();
			} else {
				flag = false;
				con.rollback();
			}
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException sex) {
				LoggerManager.writeLogSevere(sex);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException sex) {
				LoggerManager.writeLogSevere(sex);
			} finally {
				closeConnection();
			}
		}
		return flag;
	}

	/**
	 * Recover Password using Existed Question(non-Javadoc)method is use to
	 * re_get the password
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#recoverPasswordByQuestion(com.collegemagazine.formbeans.profileformbean)
	 * @param loginid
	 *            is a variable which represents the authenticated user name
	 * @param secretequestid
	 *            is a variable which represents the authenticated their profile
	 * @param secretans
	 *            is a variable which represents the authenticated their profile
	 *            user
	 *@return flag which is the variable represents the flag value
	 */
	public String recoverPasswordByQuestion(profileformbean regbean) {
		String password;
		String loginid = regbean.getLoginid();
		String secretquestid = regbean.getSquest();
		String secretans = regbean.getSecrete();
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(true);
			CallableStatement cstmt = con
					.prepareCall("{call RecoverPassword(?,?,?,?)}");
			cstmt.setString(1, loginid);
			cstmt.setString(2, secretquestid);
			cstmt.setString(3, secretans);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.execute();
			password = cstmt.getString(4);
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			password = "";
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
			password = "";
		} finally {
			closeConnection();
		}
		return password;
	}

	/**
	 * Check User(non-Javadoc) method is implemented checking the authenticated
	 * user
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#checkUser(java.lang.String)
	 * @return user is a variable which returns the user
	 */
	public String checkUser(String userName) throws ConnectionException {
		String user = null;
		System.out.println("username" + userName);
		try {
			con.setAutoCommit(true);
			CallableStatement cstmt = con
					.prepareCall("{ call loginidavailablity(?,?) }");
			cstmt.setString(1, userName);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.execute();
			user = cstmt.getString(2);
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			user = null;
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
			user = null;
		} finally {
			closeConnection();
		}
		return user;
	}

	/**
	 * Login Availability(non-Javadoc) method implements the availability for
	 * the user at the time of registration
	 * 
	 * @see com.collegemagazine.daoI.SecurityDAOI#checkAvailability(java.lang.String)
	 * @return flag is a variable which returns the flag value
	 */
	public boolean checkAvailability(String userid) throws ConnectionException {
		boolean flag = false;
		try {
			con = DBConnectionFactory.getConnection();
			pstmt = con
					.prepareStatement("select userid from userdetails where loginid=?");
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			throw new ConnectionException(
					"Checking is not possible at this time");
		} finally {
			closeConnection();
		}
		return flag;
	}
}
