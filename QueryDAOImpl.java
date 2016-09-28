package com.collegemagazine.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import com.collegemagazine.bean.EventsTo;
import com.collegemagazine.bean.QueryTo;

import com.collegemagazine.daoI.QueryDAOI;
import com.collegemagazine.dbutil.DBConnectionFactory;
import com.collegemagazine.dbutil.SqlConstants;
import com.collegemagazine.exception.ConnectionException;

/**
 * QueryDAOImpl Class implements the inserting the queries into the database and
 * retrieving the queries from database,give the solution for the queries
 */
public class QueryDAOImpl implements QueryDAOI {
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs, rs1;
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
	 * Insert Query(non-Javadoc) method implements the adding queries to
	 * database
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#insertQuery(com.collegemagazine.bean.QueryTo)
	 * @param query
	 *            is a variable which represents the query posted by the user
	 * @param status
	 *            is a variable which shows the status of the posted query
	 * @param qdate
	 *            is a variable which indicates the date when the query is
	 *            posted
	 * @param from
	 *            is a variable which represents the who posted query
	 * @return flag is a variable which returns the flag value
	 */
	public boolean insertQuery(QueryTo qfb)throws ConnectionException {
		boolean flag = true;
		int uid = 0;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._INSERT_QUERY);
			String query = qfb.getQuery();
			String status = "inactive";
			String qdate = qfb.getDate();
			String from = qfb.getFrom();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select userid from userdetails where loginid="
							+ "'" + from + "'");
			if (rs.next())
				uid = rs.getInt(1);
			pstmt.setInt(1, uid);
			pstmt.setString(2, query);
			pstmt.setString(3, status);
			pstmt.setString(4, qdate);
			int insert = pstmt.executeUpdate();
			if (insert > 0) {
				con.commit();
			} else {
				flag = false;
				con.rollback();
			}
		} catch (SQLException e) {
			
			throw new ConnectionException("insertQuery not possible at this time");
		} finally {
			closeConnection();
		}
		return flag;
	}

	/**
	 * View Queries(non-Javadoc)method implements the retrieve the data from
	 * database
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#getQueries() The
	 *      ViewQueriesAction Class represents the moderator can view the
	 *      queries which are posted by the faculty and student
	 * @return vqb is a variable which returns the vector value
	 */
	public Vector<QueryTo> getQueries()throws ConnectionException {
		Vector<QueryTo> vqb = null;
		String fname = "";
		String lname = "";
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_QUERY);
			rs = pstmt.executeQuery();
			vqb = new Vector<QueryTo>();
			while (rs.next()) {
				int uid = rs.getInt(1);
				int qid = rs.getInt(2);
				String query = rs.getString(3);
				String status = rs.getString(4);
				String date = rs.getString(5);
				stmt = con.createStatement();
				rs1 = stmt
						.executeQuery("select firstname,lastname from userdetails where userid="
								+ uid);
				if (rs1.next())
					fname = rs1.getString(1);
				lname = rs1.getString(2);
				QueryTo qf = new QueryTo();
				qf.setDate(date);
				qf.setQid(qid);
				qf.setFname(fname);
				qf.setLname(lname);
				qf.setQuery(query);
				qf.setDate(date);
				qf.setQstatus(status);
				System.out.println("in DAo Status is .........." + status);
				vqb.add(qf);
			}
		} catch (SQLException e) {
			throw new ConnectionException("fdsf");
		}finally {
			closeConnection();
		}
		return vqb;
	}

	/**
	 * getQueriesAt(non-Javadoc) implements the retrieving the name user who
	 * posted query
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#getQueriesAt(int)
	 * @return vqb is a variable which returns the vector value
	 */
	public Vector<QueryTo> getQueriesAt(int qid1)throws ConnectionException {
		Vector<QueryTo> vqb = null;
		String fname = "";
		String lname = "";
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_QUERY_AT);
			pstmt.setInt(1, qid1);
			rs = pstmt.executeQuery();
			vqb = new Vector<QueryTo>();
			while (rs.next()) {
				int uid = rs.getInt(1);
				int qid = rs.getInt(2);
				String query = rs.getString(3);
				String status = rs.getString(4);
				String date = rs.getString(5);
				stmt = con.createStatement();
				rs1 = stmt
						.executeQuery("select firstname,lastname from userdetails where userid="
								+ uid);
				if (rs1.next())
					fname = rs1.getString(1);
				lname = rs1.getString(2);
				QueryTo qf = new QueryTo();
				qf.setDate(date);
				qf.setQid(qid);
				qf.setFname(fname);
				qf.setLname(lname);
				qf.setQuery(query);
				qf.setDate(date);
				qf.setQstatus(status);
				System.out.println("in DAo Status is .........." + status);
				vqb.add(qf);
			}
		} catch (SQLException e) {
			
			throw new ConnectionException("fds");
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			closeConnection();
		}
		return vqb;
	}
	
	public boolean sendSms(String sms, String pass) throws ConnectionException {
		boolean flag = true;
		int uid = 0;
		try {
			con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._SMS_QUERY);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				flag = true;
			try {
			//new Way2SmsManager().send("8143825831", pass, rs.getString(1),	sms, "");
			} catch (Exception e) {
				flag = false;
				System.out.println(e + " FAIL");
			}
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
		} finally {
			closeConnection();
		}
		return flag;
	}


	/**
	 * insertSolution(non-Javadoc) method implements the giving replies to
	 * posted queries
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#insertSolution(com.collegemagazine.bean.QueryTo)
	 * @param solution
	 *            which is a variable which gives the solution for posted
	 *            queries
	 * @param sdate
	 *            is a variable which represents the post the solution
	 * @return flag is a variable which returns the flag value
	 */
	public boolean insertSolution(QueryTo qf)throws ConnectionException {
		boolean flag = true;
		int update = 0;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._INSERT_SOLUTION);
			int qid = qf.getQid();
			String solution = qf.getSolution();
			String sdate = qf.getRdate();
			System.out.println("in DAo date is..........." + sdate);
			pstmt.setInt(1, qid);
			pstmt.setString(2, solution);
			pstmt.setString(3, sdate);
			int insert = pstmt.executeUpdate();
			if (insert > 0) {
				stmt = con.createStatement();
				update = stmt
						.executeUpdate("update query set querystatus='solved' where queryid="
								+ qid);
			}
			if (update > 0) {
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
			}throw new ConnectionException("dfd");
		} finally {
			
			closeConnection();
		}
		return flag;
	}

	/**
	 * getQueryStatus(non-Javadoc)
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#getQueryStatus(java.lang.String)
	 */
	public Vector<QueryTo> getQueryStatus(String login) throws ConnectionException{
		Vector<QueryTo> vsb = null;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_QUERY_STATUS);
			System.out.println("in Solution DAo loginid is..........." + login);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			vsb = new Vector<QueryTo>();
			while (rs.next()) {
				int qid = rs.getInt(1);
				int quid = rs.getInt(2);
				String description = rs.getString(3);
				String qdate = rs.getString(4);
				String sdate = rs.getString(5);
				String solution = rs.getString(6);
				int userid = rs.getInt(7);
				String status = rs.getString(8);
				QueryTo qb = new QueryTo();
				qb.setQid(qid);
				qb.setRdate(sdate);
				qb.setSolution(solution);
				qb.setQuery(description);
				qb.setRdate(sdate);
				qb.setRdate(qdate);
				qb.setUserid(userid);
				qb.setQuid(quid);
				qb.setStatus(status);
				vsb.add(qb);
			}
		} catch (SQLException e) {
throw new ConnectionException("w yoe");
		}
		finally {
			closeConnection();
		}
		return vsb;
	}

	/**
	 * View Solutions(non-Javadoc) method implements the view solution for the
	 * posted query
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#getSolution(int,
	 *      java.lang.String)
	 *@return vsb is a variable which returns the vector value
	 */
	public Vector<QueryTo> getSolution(int qid, String login) throws ConnectionException{
		Vector<QueryTo> vsb = null;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._GET_SOLUTION);
			pstmt.setInt(1, qid);
			pstmt.setString(2, login);
			rs = pstmt.executeQuery();
			vsb = new Vector<QueryTo>();
			while (rs.next()) {
				int qid1 = rs.getInt(1);
				int quid = rs.getInt(2);
				String description = rs.getString(3);
				String qdate = rs.getString(4);
				String sdate = rs.getString(5);
				String solution = rs.getString(6);
				int userid = rs.getInt(7);
				String status = rs.getString(8);
				QueryTo qb = new QueryTo();
				qb.setQid(qid1);
				qb.setRdate(sdate);
				qb.setSolution(solution);
				qb.setQuery(description);
				qb.setSdate(qdate);
				qb.setUserid(userid);
				qb.setQuid(quid);
				qb.setStatus(status);
				vsb.add(qb);
			}
		} catch (SQLException e) {
			
			throw new ConnectionException("w y o E ");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return vsb;
	}

	/**
	 * Insert Events(non-Javadoc) method implements the adding the events by
	 * students
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#insertevents(com.collegemagazine.bean.EventsTo)
	 * @param eventname
	 *            is a variable which represents name of the event conducted by
	 *            the college
	 * @param eventtype
	 *            is a variable which indicate the type of event
	 * @param eventdesc
	 *            is a variable which shows the description about events
	 * @return flag is a variable which returns the flag value
	 */
	public boolean insertevents(EventsTo eb)throws ConnectionException {
		boolean flag = true;
		try {con = DBConnectionFactory.getConnection();
			String eventname = eb.getEventname();
			String eventtype = eb.getEventtype();
			String eventdesc = eb.getEventdesc();
			pstmt = con.prepareStatement(SqlConstants._INSERT_EVENTS);
			pstmt.setString(1, eventname);
			pstmt.setString(2, eventtype);
			pstmt.setString(3, eventdesc);
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
			}throw new ConnectionException("write your own Exception");
		} finally {closeConnection();}
		return flag;
	}

	/**
	 * View Events(non-Javadoc) method implements the retrieving the event
	 * details from database
	 * 
	 * @see com.collegemagazine.daoI.QueryDAOI#viewevents(java.lang.String)
	 * @return vto is a variable which represents the vector value
	 */
	public Vector<EventsTo> viewevents(String eventname)throws ConnectionException{
		Vector<EventsTo> vto = new Vector<EventsTo>();
		EventsTo eto = null;
		try {con = DBConnectionFactory.getConnection();
			pstmt = con.prepareStatement(SqlConstants._VIEW_EVENTS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eto = new EventsTo();
				eto.setEventid(rs.getInt(1));
				eto.setEventname(rs.getString(2));
				eto.setEventtype(rs.getString(3));
				eto.setEventdate(rs.getString(4));
				eto.setEventdesc(rs.getString(5));
				vto.add(eto);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException se) {
				
				throw new ConnectionException("Server is Busy Please try Later ");
			}
		} finally {closeConnection();}
		return vto;
	}
}
