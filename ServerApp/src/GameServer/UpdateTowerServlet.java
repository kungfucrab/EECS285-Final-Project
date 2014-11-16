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

public class UpdateTowerServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String towername = req.getParameter("towername");
		String wins = req.getParameter("wins");
		String loses = req.getParameter("loses");
		PrintWriter out = resp.getWriter();
		
		if(towername != null && GetTowerServlet.checkIfTowerExists(towername) && wins != null && loses != null) {
			String query = "UPDATE towers SET wins=" + wins + ",loses=" + loses + " WHERE towername=\"" + towername + "\";";
			System.out.println(query);
			Connection conn = null;
			try {
		    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		    	conn = DriverManager.getConnection(GameServerMain.server, GameServerMain.serverUsername, GameServerMain.serverPassword);

		    	Statement stmt = null;
		    	try {
		    		stmt = conn.createStatement();
		    		int insertCheck = stmt.executeUpdate(query);
		    		
		    		if(insertCheck == 1) {
		    			out.println("Tower Updated Success");
		    		}
		    		else {
		    			out.println("Tower Update Failed");
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
			out.println("missing params or tower does not exist");
		}
	}
}
