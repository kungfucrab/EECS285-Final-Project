package GameServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class NewTowerServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String username = req.getParameter("username");
		String towername = req.getParameter("towername");
		String towerdata = req.getParameter("towerdata");
		
		PrintWriter out = resp.getWriter();
		if(towername != null && !GetTowerServlet.checkIfTowerExists(towername))
			if(username != null && towername != null && towerdata != null && UserAccountServlet.checkIfUserExists(username)) {
				 String towerQuery = "INSERT INTO towers(towername, wins, loses, towerdata) VALUES(\""+towername+"\", 0, 0, \"" + towerdata + "\");";
				 String towerRelationQuery = "INSERT INTO tower_user_rel(username, towername) VALUES(\""+username+"\", \"" + towername + "\");";
				  Connection conn = null;
				  try {
				    	Class.forName("com.mysql.jdbc.Driver").newInstance();
				    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);
	
				    	Statement stmt = null;
				    	try {
				    		stmt = conn.createStatement();
				    		int insertTowerQueryCheck = stmt.executeUpdate(towerQuery);
				    		int towerRelationQueryCheck = stmt.executeUpdate(towerRelationQuery);
				    		
				    		if(insertTowerQueryCheck == 1 && towerRelationQueryCheck == 1) {
				    			out.println("Tower Insert Success");
				    		}
				    		else {
				    			out.println("Tower Insert Failed");
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
				out.println("Missing params or username does not exist");
			}
		else {
			out.println("Missing tower param or towername already in use");
		}
	}
}
