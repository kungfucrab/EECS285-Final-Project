package GameServer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class UserAccountServlet extends HttpServlet {
  
	//get existing user given a "username"
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
		String q = req.getParameter("username");
		PrintWriter out = resp.getWriter();
	    
		boolean userCheck = checkIfUserExists(q);
		
		if(userCheck == true) {
			out.println("1");
		}
		else {
			out.println("0");
		}
  }
  
	//create new user given a "username"
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
	  String q = req.getParameter("username");
	  PrintWriter out = resp.getWriter();
	    
	  boolean userCheck = checkIfUserExists(q);
		
	  if(userCheck == true) {
		  out.println("User Already Exists");
	  }
	  else {
		  String query = "INSERT INTO users(username,score) VALUES (\"" + q +"\", 0);";
		  Connection conn = null;
		  try {
		    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

		    	Statement stmt = null;
		    	try {
		    		stmt = conn.createStatement();
		    		int insertCheck = stmt.executeUpdate(query);
		    		
		    		if(insertCheck == 1) {
		    			out.println("User Insert Success");
		    		}
		    		else {
		    			out.println("User Insert Failed");
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
  }
  
  static public boolean checkIfUserExists(String data) {
	  String q = data;
	    Connection conn = null;
	    try {
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

	    	Statement stmt = null;
	    	String query = "SELECT username FROM users WHERE username = \"" + q + "\";";
	    	
	    	try {
	    		String username = null;
	    		stmt = conn.createStatement();
	    		ResultSet rs = stmt.executeQuery(query);
	    		while(rs.next()) {
	    			username = rs.getString(1);
	    		}
	    		
	    		if(username != null && username.equals(q)) {
	    			return true;
	    		}
	    		else {
	    			return false;
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
  return false;
  }
}
