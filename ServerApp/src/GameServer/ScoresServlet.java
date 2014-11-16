package GameServer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class ScoresServlet extends HttpServlet
{
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	  {
		PrintWriter out = resp.getWriter();
	    String q = req.getParameter("username");
	    Connection conn = null;
	    //get score for individual user
	    if(q != null) {
	    	String query = "SELECT score, username FROM users WHERE users.username = \"" + q + "\";";
	    	try {
	    		Class.forName("com.mysql.jdbc.Driver").newInstance();
		    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

		    	Statement stmt = null;
		    	int score = 0;
		    	String username = "";
		    	try {
		    		stmt = conn.createStatement();
		    		ResultSet rs = stmt.executeQuery(query);
		    		
		    		while(rs.next()) {
		    			score = rs.getInt("score");
		    			username = rs.getString("username");
		    		}
		    		Dictionary dict = new Hashtable();
		    		dict.put("username", username);
		    		dict.put("score", Integer.toString(score));
		    		out.println(dict);
		    		
		    	}
		    	catch (SQLException e ) {
		    		System.out.println(e.toString());
		    	} 
		    	finally {
		    		if (stmt != null) { stmt.close(); }
		    	}

		    	conn.close();
	    	}
		    catch (SQLException ex) {
		    	System.out.println(ex.toString());
		    }
		    catch (Exception e) {
		    	System.out.println(e.toString());
		    }
	    }
	    //get all scores
	    else {
	    	ArrayList al = new ArrayList();
	    	
	    	String query = "SELECT score, username FROM users;";
	    	try {
	    		Class.forName("com.mysql.jdbc.Driver").newInstance();
		    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

		    	Statement stmt = null;
		    	int score = 0;
		    	String username = "";
		    	try {
		    		stmt = conn.createStatement();
		    		ResultSet rs = stmt.executeQuery(query);
		    		
		    		while(rs.next()) {
		    			score = rs.getInt(1);
		    			username = rs.getString(2);
		    			
		    			Dictionary dict = new Hashtable();
			    		dict.put("username", username);
			    		dict.put("score", Integer.toString(score));
			    		
			    		al.add(dict);
		    		}
		    		out.println(al);
		    		
		    	}
		    	catch (SQLException e ) {
		    		System.out.println(e.toString());
		    	} 
		    	finally {
		    		if (stmt != null) { stmt.close(); }
		    	}

		    	conn.close();
	    	}
		    catch (SQLException ex) {
		    	System.out.println(ex.toString());
		    }
		    catch (Exception e) {
		    	System.out.println(e.toString());
		    }
	    }
	  }
	  
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	  {
	    String username = req.getParameter("username");
	    String score = req.getParameter("score");
	    PrintWriter out = resp.getWriter();
	    
	    if(score != null && username != null && UserAccountServlet.checkIfUserExists(username)) {
	    	String query = "UPDATE users SET username=\"" + username +"\", score=" + score + " WHERE username=\"" + username +"\";";
			  Connection conn = null;
			  try {
			    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

			    	Statement stmt = null;
			    	try {
			    		stmt = conn.createStatement();
			    		int insertCheck = stmt.executeUpdate(query);
			    		
			    		if(insertCheck == 1) {
			    			out.println("User Score Updated Success");
			    		}
			    		else {
			    			out.println("User Score Update Failed");
			    		}
			    	}
			    	catch (SQLException e ) {
			    		System.out.println(e.toString());
			    	} 
			    	finally {
			    		if (stmt != null) { stmt.close(); }
			    	}

			    	conn.close();
			    }
			    catch (SQLException ex) {
			    	System.out.println(ex.toString());
			    }
			    catch (Exception e) {
			    	System.out.println(e.toString());
			    }
	    }
	    else {
	    	out.println("username does not exist or missing param");
	    }
	  }
	}
