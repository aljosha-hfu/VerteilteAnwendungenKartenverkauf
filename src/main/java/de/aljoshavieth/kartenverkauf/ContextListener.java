package de.aljoshavieth.kartenverkauf;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        Kartenverkauf kartenverkauf = new Kartenverkauf();
        sce.getServletContext().setAttribute("Kartenverkauf", kartenverkauf);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
