package de.aljoshavieth.kartenverkauf;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.util.ArrayList;
import java.util.Arrays;

public class Kartenverkauf {
    protected Ticket[] tickets = new Ticket[100];
    protected boolean acceptReservations = true;

    public Kartenverkauf() {
        for (int i = 0; i < 100; i++) {
            Ticket ticket = new Ticket();
            ticket.setState(TicketState.AVAILABLE);
            tickets[i] = ticket;
        }
    }

    public synchronized void sellTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket nicht verfügbar!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void reserveTicket(int seat, String name) throws TicketException {
        if (!acceptReservations) {
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
        setTicketReserved(seat, name);
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
        if (!tickets[seat].getName().equals(name)) {
            throw new TicketException("Ticket ist nicht auf diesen Namen reserviert!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void cancelTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket existiert nicht!");
        }
        if (ticketIsFree(seat)) {
            throw new TicketException("Ticket ist bereits verfügbar!");
        }
        setStateOfTicket(seat, TicketState.AVAILABLE);

    }

    //protected wegen Test
    protected synchronized void cancelAllTicketReservations() {
        Arrays.stream(tickets).forEach(this::cancelTicketReservation);
        acceptReservations = false;
    }

    private synchronized void cancelTicketReservation(Ticket ticket) {
        if (ticket.getState().equals(TicketState.RESERVED)) {
            ticket.setName(null);
            ticket.setState(TicketState.AVAILABLE);
        }
    }

    private boolean ticketIsFree(int seat) {
        return tickets[seat].getState().equals(TicketState.AVAILABLE);
    }

    private boolean ticketIsReserved(int seat) {
        return tickets[seat].getState().equals(TicketState.RESERVED);
    }

    private boolean ticketNotExists(int seat) {
        return (seat < 0 || seat > 99);
    }

    private synchronized void setStateOfTicket(int seat, TicketState state) {
        Ticket ticket = tickets[seat];
        ticket.setState(state);
        tickets[seat] = ticket;
    }

    private synchronized void setTicketReserved(int seat, String name) {
        Ticket ticket = tickets[seat];
        ticket.setState(TicketState.RESERVED);
        ticket.setName(name);
        tickets[seat] = ticket;
    }

    public boolean isAcceptingReservations() {
        return acceptReservations;
    }

    public ArrayList<Integer> getAllAvailableTickets() {
        ArrayList<Integer> availableTickets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.AVAILABLE)) {
                availableTickets.add(i);
            }
        }
        return availableTickets;
    }

    public ArrayList<Integer> getAllSoldTickets() {
        ArrayList<Integer> soldTickets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.SOLD)) {
                soldTickets.add(i);
            }
        }
        return soldTickets;
    }

    public ArrayList<Integer> getAllReservedTickets() {
        ArrayList<Integer> reservedTickets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.RESERVED)) {
                reservedTickets.add(i);
            }
        }
        return reservedTickets;
    }

}
