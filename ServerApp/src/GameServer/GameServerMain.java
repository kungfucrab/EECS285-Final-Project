package GameServer;

import java.util.logging.Handler;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class GameServerMain
{
  static public void main(String[] args) throws Exception
  {
    Server server = new Server(7777);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(new GameServlet()),"/it/");
    
    server.start();
    server.join();
  }
}
