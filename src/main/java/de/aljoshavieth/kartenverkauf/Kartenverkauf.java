package de.aljoshavieth.kartenverkauf;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Kartenverkauf {
    private Connection connection;
    private static Logger logger = Logger.getLogger("Kartenverkauf");

    public Kartenverkauf(Connection connection) {
        this.connection = connection;
        logger.info("Created Kartenverkauf!");
    }

    public synchronized void sellTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket nicht verfügbar!");
        }
        try {
            setStateOfTicket(seat, TicketState.SOLD);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Verkaufen des Tickets: " + throwables.getMessage());
        }

    }

    public synchronized void reserveTicket(int seat, String name) throws TicketException {
        if (!isAcceptingReservations()) {
            throw new TicketException("Reservierungen sind nicht verfügbar!");
        }
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket nicht verfügbar!");
        }
        if (name == null || name.length() == 0) {
            throw new TicketException("Kein Name angegeben!");
        }
        try {
            setTicketReserved(seat, name);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim reservieren des Tickets: " + throwables.getMessage());
        }
    }

    public synchronized void sellReservedTicket(int seat, String name) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (ticketIsFree(seat)) {
            throw new TicketException("Ticket nicht verfügbar!");
        }
        if (name == null) {
            throw new TicketException("Kein Name angegeben!");
        }
        if (!ticketIsReserved(seat)) {
            throw new TicketException("Ticket ist nicht reserviert!");
        }
        if (!nameOfReservedTicketIsEqual(seat, name)) {
            throw new TicketException("Ticket ist nicht auf diesen Namen reserviert!");
        }
        try {
            setStateOfTicket(seat, TicketState.SOLD);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Verkaufen eines reservierten Tickets: " + throwables.getMessage());
        }

    }

    public synchronized void cancelTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (ticketIsFree(seat)) {
            throw new TicketException("Ticket ist bereits verfügbar!");
        }
        try {
            setStateOfTicket(seat, TicketState.AVAILABLE);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Canceln eines Tickets: " + throwables.getMessage());
        }

    }

    public synchronized void cancelAllTicketReservations() throws TicketException {
        for (int i = 1; i < 101; i++) {
            cancelTicketReservation(i);
        }
        try {
            String query = "UPDATE settings SET state = 0 WHERE id = 'acceptreservations'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private synchronized void cancelTicketReservation(int seat) throws TicketException {
        try {
            if (checkState(seat, TicketState.RESERVED)) {
                setStateOfTicket(seat, TicketState.AVAILABLE);
            }
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Canceln aller Reservierungen: " + throwables.getMessage());
        }
    }

    private boolean ticketIsFree(int seat) throws TicketException {
        try {
            return checkState(seat, TicketState.AVAILABLE);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Überprüfen ob ein Ticket frei ist: " + throwables.getMessage());
        }
    }


    private boolean ticketIsReserved(int seat) throws TicketException {
        try {
            return checkState(seat, TicketState.RESERVED);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Überprüfen ob ein Ticket reserviert ist: " + throwables.getMessage());
        }
    }

    private boolean checkState(int seat, TicketState ticketState) throws SQLException {
        //String query = "SELECT IFNULL( (SELECT state FROM tickets WHERE id = ? LIMIT 1) ,'" + ticketState.toString() + "');";
        String query = "(SELECT state FROM tickets WHERE id = ?) UNION (SELECT 'AVAILABLE') LIMIT 1;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, seat);
        logger.info("QUERY: " + preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return ticketState.toString().equalsIgnoreCase(resultSet.getString("state"));
    }

    private boolean ticketNotExists(int seat) {
        return (seat < 0 || seat > 99);
    }

    private synchronized void setStateOfTicket(int seat, TicketState state) throws SQLException {
        if (state == TicketState.AVAILABLE) {
            String query = "DELETE FROM tickets WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, seat);
            logger.info("QUERY: " + preparedStatement.toString());
            preparedStatement.execute();
        } else {
            String query = "INSERT INTO tickets (id, state, name) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE state = ?, name = ''";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, seat);
            preparedStatement.setString(2, state.toString());
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, state.toString());
            logger.info("QUERY: " + preparedStatement.toString());
            preparedStatement.execute();
        }
    }

    private synchronized void setTicketReserved(int seat, String name) throws SQLException {
        String query = "INSERT INTO tickets (id, state, name) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE state = VALUES(state), name = VALUES(name)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, seat);
        preparedStatement.setString(2, TicketState.RESERVED.toString());
        preparedStatement.setString(3, name);
        preparedStatement.execute();
    }

    private boolean nameOfReservedTicketIsEqual(int seat, String nameToCheck) throws TicketException {
        try {

            String query = "SELECT name FROM tickets WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, seat);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String storedName = resultSet.getString("name");
            return nameToCheck.equals(storedName);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Überprüfen ob angegebener Name mit dem in der Reservierung hinterlegten Namen übereinstimmt: " + throwables.getMessage());
        }
    }

    public boolean isAcceptingReservations() throws TicketException {
        String query = "SELECT state FROM settings WHERE id = 'acceptreservations'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("state");
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Überprüfungen ob Reservierungen angenommen werden: " + throwables.getMessage());
        }
    }

    public ArrayList<Integer> getAllAvailableTickets() throws SQLException {
        ArrayList<Integer> notAvailableSeats = new ArrayList<>();
        String query = "SELECT id FROM tickets";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            notAvailableSeats.add(resultSet.getInt("id"));
        }

        ArrayList<Integer> availableTickets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (!notAvailableSeats.contains(i)) {
                availableTickets.add(i);
            }
        }
        return availableTickets;
    }

    public ArrayList<Integer> getAllSoldTickets() throws TicketException {
        try {
            return getAllTicketsOfOneState(TicketState.SOLD);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Zugriff auf alle verkauften Tickets: " + throwables.getMessage());
        }
    }

    public ArrayList<Integer> getAllReservedTickets() throws TicketException {
        try {
            return getAllTicketsOfOneState(TicketState.RESERVED);
        } catch (SQLException throwables) {
            throw new TicketException("MySQL Fehler beim Zugriff auf alle reservierten Tickets: " + throwables.getMessage());
        }
    }

    private ArrayList<Integer> getAllTicketsOfOneState(TicketState ticketState) throws SQLException {
        ArrayList<Integer> tickets = new ArrayList<>();
        String query = "SELECT id FROM tickets WHERE state = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ticketState.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            tickets.add(resultSet.getInt("id"));
        }
        return tickets;
    }

}
