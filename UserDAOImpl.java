package com.collegemagazine.daoImpl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import com.collegemagazine.daoI.UserDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.dbutil.SqlConstants;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.profileformbean;
import com.collegemagazine.util.DateWrapper;
import com.collegemagazine.util.LoggerManager;

/**
 * UserDAOImpl Class implements the view users action,update user status
 * action,view profile action and delete user action
 */
public class UserDAOImpl implements UserDAOI {
	public Connection con;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	boolean flag = false;

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
	 * The ViewUserAction method represents the view the number of user present
	 * in the system.Administrator can have only this permission
	 * 
	 * @return v is a variable which represents the vector value
	 */
	public Vector<profileformbean> viewUser(String realpath, String user,
			String status) throws ConnectionException {
		Vector<profileformbean> v = new Vector<profileformbean>();
		profileformbean pro = null;
		v.clear();
		try {
			con = DBConnectionFactory.getConnection();
			String path = realpath;
			pstmt = con.prepareStatement(SqlConstants._VIEW_USER);
			pstmt.setString(1, status);
			pstmt.setString(2, user);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				path = realpath;
				pro = new profileformbean();
				pro.setUserid(rs.getInt(1));
				pro.setFirstName(rs.getString(2));
				System.out.println("haiiiiiii" + rs.getString(2));
				pro.setEmail(rs.getString(3));
				pro.setGender(rs.getString(4));
				pro.setFax(rs.getString(5));
				Blob b = rs.getBlob(6);
				byte b1[] = b.getBytes(1, (int) b.length());
				path = path + "/" + rs.getInt(1) + ".jpg";
				System.out.println("path  :@@@@@@@@@" + path);
				System.out.println("Realpath  :@@@@@@@@@" + realpath);
				OutputStream fout = new FileOutputStream(path);
				fout.write(b1);
				pro.setPhoto(path);
				pro.setStatus(rs.getString(7));
				pro.setHouseNo(rs.getString(8));
				pro.setStreet(rs.getString(9));
				pro.setCity(rs.getString(10));
				pro.setState(rs.getString(11));
				pro.setCountry(rs.getString(12));
				pro.setPin(rs.getString(13));
				pro.setPhoneNo(rs.getString(14));
				pro.setLoginType(rs.getString(15));
				pro.setUserName(rs.getString(16));
				v.add(pro);
			}
		} catch (SQLException se) {
			throw new ConnectionException("View User Not possible NOw");

		} catch (Exception e) {
			System.out.println(e);
			LoggerManager.writeLogWarning(e);
		} finally {

			closeConnection();
		}
		return v;
	}

	/**
	 * Update User Status(non-Javadoc)
	 * 
	 * @see com.collegemagazine.daoI.UserDAOI#updateUserStatus(int) This
	 *      UpdateUserStatusAction Class represents the user status here
	 *      administrator can update the user status
	 *@return flag is a variable which returns the flag value
	 */
	public boolean updateUserStatus(int userid) {
		try {
			con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._UPDATE_USER_STATUS);
			pstmt.setInt(1, userid);
			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
			}
		} catch (SQLException se) {
			LoggerManager.writeLogWarning(se);
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				LoggerManager.writeLogWarning(se);
			}
		}
		return flag;
	}

	/**
	 * View Profile(non-Javadoc)
	 * 
	 * @see com.collegemagazine.daoI.UserDAOI#viewprofile(java.lang.String,
	 *      java.lang.String) The ViewProfileAction Class represents the view
	 *      the user own profile when he/she enter into the system with their
	 *      user name and password
	 * @return v is a variable which returns the vector value
	 */
	public Vector<profileformbean> viewprofile(String path, String user) {
		Vector<profileformbean> v = new Vector<profileformbean>();
		profileformbean pr = null;
		v.clear();
		System.out.println("user :" + user);
		try {
			con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._VIEW_USER_PROFILE);
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pr = new profileformbean();
				pr.setUserid(rs.getInt(1));
				pr.setFirstName(rs.getString(2));
				pr.setLastName(rs.getString(3));
				pr.setBirthdate(DateWrapper.parseDate(rs.getDate(4)));
				pr.setEmail(rs.getString(5));
				pr.setFax(rs.getString(6));
				Blob b = rs.getBlob(7);
				byte b1[] = b.getBytes(1, (int) b.length());
				path = path + "/" + rs.getInt(1) + ".jpg";
				System.out.println("path  :" + path);
				OutputStream fout = new FileOutputStream(path);
				fout.write(b1);
				pr.setPhoto(path);
				pr.setAddressType(rs.getString(8));
				pr.setHouseNo(rs.getString(9));
				pr.setStreet(rs.getString(10));
				pr.setCity(rs.getString(11));
				pr.setState(rs.getString(12));
				pr.setCountry(rs.getString(13));
				pr.setPin(rs.getString(14));
				pr.setPhoneNo(rs.getString(15));
				v.add(pr);
			}
		} catch (SQLException se) {
			LoggerManager.writeLogWarning(se);
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				LoggerManager.writeLogWarning(se);
			}
		}
		return v;
	}

	/**
	 * Delete User(non-Javadoc)
	 * 
	 * @see com.collegemagazine.daoI.UserDAOI#deleteUser(int) DeleteUserAction
	 *      Class implements Deleting the user from database successfully
	 * @return flag which returns the flag value
	 */
	public boolean deleteUser(int userid) throws ConnectionException {
		try {
			con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._DELETE_USER);
			pstmt.setInt(1, userid);
			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
			}
		} catch (SQLException se) {
			LoggerManager.writeLogWarning(se);
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				LoggerManager.writeLogWarning(se);
			} finally {
				closeConnection();
			}
		}
		return flag;
	}
}
