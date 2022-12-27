package web_app;

import actions_with_db.Data;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import web_app.servlets.CurrencieServlet;
import web_app.servlets.ItemServlet;
import web_app.servlets.PlayerServlet;
import web_app.servlets.ProgressServlet;

public class Server {
    public static void start(Data data){


        int port = 8080;

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // http://localhost:8080/func
        context.addServlet(new ServletHolder( new PlayerServlet( data) ),"/player");
        context.addServlet(new ServletHolder( new CurrencieServlet( data )),"/currencie");
        context.addServlet(new ServletHolder( new ItemServlet( data )),"/item");
        context.addServlet(new ServletHolder( new ProgressServlet( data )),"/progress");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);

        try {
            server.start();
            System.out.println("Listening port : " + port );

            server.join();
        } catch (Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }
}
