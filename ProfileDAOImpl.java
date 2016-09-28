package com.collegemagazine.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import com.collegemagazine.daoI.ProfileDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.exception.ConnectionException;
import com.collegemagazine.formbeans.profileformbean;
import com.collegemagazine.util.DateWrapper;
import com.collegemagazine.util.LoggerManager;

/**
 * The ProfileDAOImpl Class implements all the actions like user registration,
 * view profile action,update user details and delete user action
 */
public class ProfileDAOImpl implements ProfileDAOI {
	public Connection con;
	private boolean flag;
	public PreparedStatement pstmt;

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

	public boolean changePass(profileformbean pro) throws ConnectionException {
		return false;
	}

	public boolean changeQuestion(profileformbean pro)
			throws ConnectionException {
		return false;
	}

	/**
	 * CheckUser method implements the check the user authentication depending
	 * upon the UserName and password
	 * 
	 * @return user which is a variable represents the checking user
	 */
	public String checkUser(String userName) throws ConnectionException {
		String user = null;
		System.out.println("username" + userName);
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(true);
			CallableStatement cstmt = con
					.prepareCall("{ call loginidavailablity(?,?) }");
			cstmt.setString(1, userName);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.execute();
			user = cstmt.getString(2);
		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			user = null;

			throw new ConnectionException("Please try later");
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
			user = null;
		} finally {
			closeConnection();
		}
		return user;
	}

	/**
	 * This is the User Registration method which performs the registration
	 * action.
	 * 
	 * @param firstname
	 *            is a variable which shows the first name of the user.
	 * @param lastname
	 *            is a variable which indicate the last name of the user.
	 * @param birthdate
	 *            is a variable which represents the date of birth of the user.
	 * @param email
	 *            is a variable which shows the email address of the user.
	 * @param username
	 *            is a variable which represents the login id for a user,using
	 *            this user name he/she enter in to the system.
	 * @param password
	 *            is a variable which represents the password for security and
	 *            all the variables also
	 * @return flag which gives result either true or false.
	 */
	public boolean insertNewUser(profileformbean pro)
			throws ConnectionException {
		boolean flag = false;
		String firstname = pro.getFirstName();
		String lastname = pro.getLastName();
		String birthdate = DateWrapper.parseDate(pro.getBirthdate());
		String squest = pro.getSquest();
		if (squest.equals("")) {
			squest = pro.getOwnquest();
		}
		String sqansw = pro.getSecrete();
		String fax = pro.getFax();
		String email = pro.getEmail();
		String gender = pro.getGender();
		String addresstype = pro.getAddressType();
		String houseno = pro.getHouseNo();
		String street = pro.getStreet();
		String city = pro.getCity();
		String state = pro.getState();
		String country = pro.getCountry();
		String pin = pro.getPin();
		String phoneno = pro.getPhoneNo();
		String logintype = pro.getLoginType();
		String username = pro.getUserName();
		String password = pro.getPassword();
		String photo = pro.getPhoto();
		try {
			con = DBConnectionFactory.getConnection();
			System.out.println("photo=" + photo);
			File f = new File(photo);
			FileInputStream fis = new FileInputStream(f);
			System.out.println("fole=" + f.length());
			CallableStatement cstmt = con
					.prepareCall("{call insertprocedure(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setBinaryStream(1, fis, (int) f.length());
			cstmt.setString(2, firstname);
			cstmt.setString(3, lastname);
			cstmt.setString(4, birthdate);
			cstmt.setString(5, squest);
			cstmt.setString(6, sqansw);
			cstmt.setString(7, email);
			cstmt.setString(8, fax);
			cstmt.setString(9, gender);
			cstmt.setString(10, username);
			cstmt.setString(11, password);
			cstmt.setString(12, logintype);
			cstmt.setString(13, addresstype);
			cstmt.setString(14, houseno);
			cstmt.setString(15, street);
			cstmt.setString(16, city);
			cstmt.setString(17, state);
			cstmt.setString(18, country);
			cstmt.setString(19, pin);
			cstmt.setString(20, phoneno);
			int i = cstmt.executeUpdate();
			if (i >= 0) {
				flag = true;
			} else {
				flag = false;
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
			throw new ConnectionException(
					"Some internal problem occured please try after some time");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			closeConnection();
		}
		return flag;
	}

	// Getting profile
	public profileformbean getProfile(String loginname, String path)
			throws ConnectionException {
		profileformbean rb = new profileformbean();
		try {
			con = DBConnectionFactory.getConnection();
			CallableStatement cs = con
					.prepareCall("{call showprofile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, loginname);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.registerOutParameter(9, Types.VARCHAR);
			cs.registerOutParameter(10, Types.VARCHAR);
			cs.registerOutParameter(11, Types.VARCHAR);
			cs.registerOutParameter(12, Types.VARCHAR);
			cs.registerOutParameter(13, Types.VARCHAR);
			cs.registerOutParameter(14, Types.VARCHAR);
			cs.registerOutParameter(15, Types.VARCHAR);
			cs.registerOutParameter(16, Types.BLOB);
			cs.execute();
			rb.setPassword(cs.getString(2));
			rb.setFirstName(cs.getString(3));
			rb.setLastName(cs.getString(4));
			rb.setBirthdate(cs.getString(5));
			// rb.setPhoto(cs.getString());
			rb.setEmail(cs.getString(6));
			rb.setFax(cs.getString(7));
			// rb.setHome(cs.getString(8));
			rb.setHouseNo(cs.getString(9));
			rb.setStreet(cs.getString(10));
			rb.setCity(cs.getString(11));
			rb.setState(cs.getString(12));
			// rb.setPin(cs.getString(13));
			rb.setCountry(cs.getString(13));
			rb.setPin(cs.getString(14));
			rb.setPhoneNo(cs.getString(15));
			Blob b = cs.getBlob(16);
			byte b1[] = b.getBytes(1, (int) b.length());
			OutputStream fout = new FileOutputStream(path + "/" + loginname
					+ "+.jpg");
			fout.write(b1);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new ConnectionException(
					"Sorry Your Details Are not displayed now because some internal problem occured");

		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			closeConnection();
		}
		return rb;
	}

	/**
	 * This Update Profile method represents the modifying the user details when
	 * the user view their profile.
	 * 
	 * @param firstname
	 *            is a variable represents the user first name.
	 * @param lastname
	 *            is a variable shows the user last name and other variables.
	 * @return flag is variable which returns the true or false
	 */
	public boolean updateProfile(profileformbean prof)
			throws ConnectionException {
		boolean flag = false;
		String username = prof.getUserName();
		String firstname = prof.getFirstName();
		String lastname = prof.getLastName();
		String birthdate = DateWrapper.parseDate(prof.getBirthdate());
		String photo = prof.getPhoto();
		System.out.println("photo" + photo);
		if (photo.equals("")) {
			photo = prof.getPhoto();
		}
		String fax = prof.getFax();
		String email = prof.getEmail();
		String houseno = prof.getHouseNo();
		String street = prof.getStreet();
		String city = prof.getCity();
		String state = prof.getState();
		String country = prof.getCountry();
		String pin = prof.getPin();
		String phoneno = prof.getPhoneNo();
		try {
			con = DBConnectionFactory.getConnection();
			System.out.println("photo=" + photo);
			File f = new File(photo);
			FileInputStream fis = new FileInputStream(f);
			System.out.println("fole=" + f.length());
			CallableStatement cstmt = con
					.prepareCall("{call changeprofile(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setBinaryStream(1, fis, (int) f.length());
			System.out.println("photo:" + photo);
			cstmt.setString(2, firstname);
			cstmt.setString(3, lastname);
			cstmt.setString(5, birthdate);
			cstmt.setString(6, email);
			cstmt.setString(7, fax);
			cstmt.setString(4, username);
			cstmt.setString(8, houseno);
			cstmt.setString(9, street);
			cstmt.setString(10, city);
			cstmt.setString(11, state);
			cstmt.setString(12, country);
			cstmt.setString(13, pin);
			cstmt.setString(14, phoneno);
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

				throw new ConnectionException(
						"The System not ready to update ur profile now because problem occured in network");
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
		} finally {
			closeConnection();
		}
		return flag;
	}

	public boolean logout(String loginid) throws ConnectionException {
		return false;
	}

	public void logout1(String user) throws ConnectionException {
	}

	public String passwordRecovery(profileformbean pro)
			throws ConnectionException {
		return null;
	}

	// changeAccountStatus
	public boolean changeAccountStatus(String loginid, int status)
			throws ConnectionException {
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(false);
			PreparedStatement cstmt = con
					.prepareStatement("UPDATE logindetails SET loginstatus=? WHERE loginid=?");
			cstmt.setInt(1, status);
			cstmt.setString(2, loginid);
			int i = cstmt.executeUpdate();
			if (i == 1) {
				flag = true;
				con.commit();
			} else {
				flag = false;
				con.rollback();
			}
			con.close();
		} catch (SQLException ex) {
			LoggerManager.writeLogSevere(ex);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				LoggerManager.writeLogSevere(se);
			}
			throw new ConnectionException("Sorry the system is not work now");
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				LoggerManager.writeLogSevere(se);
			}
		} finally {
			closeConnection();
		}
		return flag;
	}
}
