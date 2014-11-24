package GameServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class GetTowerServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	 {
		 PrintWriter out = resp.getWriter();
		 String q = req.getParameter("username");
		 Connection conn = null;
		 
		 //get all the users towers
		 if(q != null) {
			 ArrayList al = new ArrayList();
			 String query = "SELECT T.towername, T.wins, T.loses FROM towers T, tower_user_rel R WHERE T.towername = R.towername and R.username = \"" + q + "\";";
			 try {
		    		Class.forName("com.mysql.jdbc.Driver").newInstance();
			    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

			    	Statement stmt = null;
			    	int wins = 0;
			    	int loses = 0;
			    	String towername = "";
			    	try {
			    		stmt = conn.createStatement();
			    		ResultSet rs = stmt.executeQuery(query);
			    		
			    		while(rs.next()) {
			    			towername = rs.getString("T.towername");
			    			wins = rs.getInt("T.wins");
			    			loses = rs.getInt("T.loses");
			    		
			    			Dictionary dict = new Hashtable();
				    		dict.put("towername", towername);
				    		dict.put("wins", Integer.toString(wins));
				    		dict.put("loses", Integer.toString(loses));
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
		 //get 1 tower
		 else {
			 String query = "SELECT towername, wins, loses, towerdata FROM towers WHERE (wins) IN (SELECT MAX(wins) FROM towers );";
			 try {
		    		Class.forName("com.mysql.jdbc.Driver").newInstance();
			    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

			    	Statement stmt = null;
			    	int wins = 0;
			    	int loses = 0;
			    	String towername = "";
			    	String towerdata = "";
			    	try {
			    		stmt = conn.createStatement();
			    		ResultSet rs = stmt.executeQuery(query);
			    		
			    		while(rs.next()) {
			    			towername = rs.getString("towername");
			    			wins = rs.getInt("wins");
			    			loses = rs.getInt("loses");
			    			towerdata = rs.getString("towerdata");
			    		}
			    		
//			    		Dictionary dict = new Hashtable();
//			    		dict.put("towername", towername);
//			    		dict.put("wins", Integer.toString(wins));
//			    		dict.put("loses", Integer.toString(loses));
//			    		dict.put("towerdata", towerdata);
//			    		out.println(dict);
			    		String tower = towername + ";" + Integer.toString(wins) + ";" + Integer.toString(loses) 
			    				+ ";" + towerdata;

			    		out.println(tower);
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
	 
	 static public boolean checkIfTowerExists(String data) {
		  String q = data;
		    Connection conn = null;
		    try {
		    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

		    	Statement stmt = null;
		    	String query = "SELECT towername FROM towers WHERE towername = \"" + q + "\";";
		    	
		    	try {
		    		String towername = null;
		    		stmt = conn.createStatement();
		    		ResultSet rs = stmt.executeQuery(query);
		    		while(rs.next()) {
		    			towername = rs.getString(1);
		    		}
		    		
		    		if(towername != null && towername.equals(q)) {
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
