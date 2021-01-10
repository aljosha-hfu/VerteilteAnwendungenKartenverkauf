package de.aljoshavieth.kartenverkauf;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class ContextListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger("Kartenverkauf");
    private Connection connection;

    public void contextInitialized(ServletContextEvent sce) {
        //Create db connection
        try {
            Context initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/TicketDB");
            connection = dataSource.getConnection();
            logger.info("Datasource available");

            String createTabeleQuery = "CREATE TABLE IF NOT EXISTS `tickets` (`id` TINYINT NOT NULL, `state` VARCHAR(9) NOT NULL, `name` VARCHAR(100));";
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTabeleQuery);
            statement.executeQuery("select * from tickets");
            logger.info("Successfully tested datasource");

            sce.getServletContext().setAttribute("datasource", dataSource);
        } catch (NamingException | SQLException e) {
            logger.severe("Cannot access database! ");
            e.printStackTrace();
        }


        Kartenverkauf kartenverkauf = new Kartenverkauf();
        sce.getServletContext().setAttribute("Kartenverkauf", kartenverkauf);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
