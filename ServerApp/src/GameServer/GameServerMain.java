package GameServer;

import java.util.logging.Handler;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class GameServerMain
{
	static public String server = "jdbc:mysql://localhost/eecs285";
	static public String serverUsername = "root";
	static public String serverPassword = ""; // To kill, use ps -ef | grep jetty (second in).
	
  static public void main(String[] args) throws Exception
  {
    Server server = new Server(7777);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(new GameServlet()),"/it/");
    context.addServlet(new ServletHolder(new UserAccountServlet()),"/UserAccount/");
    context.addServlet(new ServletHolder(new ScoresServlet()), "/Scores/");
    context.addServlet(new ServletHolder(new NewTowerServlet()), "/AddNewTower/");
    context.addServlet(new ServletHolder(new GetTowerServlet()), "/GetTower/");
    context.addServlet(new ServletHolder(new UpdateTowerServlet()), "/UpdateTower/");
    
    server.start();
    server.join();
  }
}
