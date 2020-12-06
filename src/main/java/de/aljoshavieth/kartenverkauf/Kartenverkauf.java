package de.aljoshavieth.kartenverkauf;

import java.util.ArrayList;
import java.util.Arrays;

public class Kartenverkauf {
    protected Ticket[] tickets = new Ticket[100];
    protected boolean acceptReservations = true;

    //TODO change
    public Kartenverkauf(){
        for (int i = 0; i < 100; i++) {
            Ticket ticket = new Ticket();
            if (i % 2 == 0) {
                ticket.setState(TicketState.RESERVED);
            } else {
                ticket.setState(TicketState.AVAILABLE);
            }
            tickets[i] = ticket;
        }
    }

    public synchronized void sellTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void reserveTicket(int seat, String name) throws TicketException {
        if (!acceptReservations) {
            throw new TicketException("Reservations are not available at this time!");
        }
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        if (name == null || name.length() == 0) {
            throw new TicketException("No name entered!");
        }
        setTicketReserved(seat, name);
    }

    public synchronized void sellReservedTicket(int seat, String name) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        if (name == null) {
            throw new TicketException("No name entered!");
        }
        if (!ticketIsReserved(seat)) {
            throw new TicketException("Ticket is not reserved!");
        }
        if (!tickets[seat].getName().equals(name)) {
            throw new TicketException("Ticket not reserved under this name!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void cancelTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (ticketIsFree(seat)) {
            throw new TicketException("Ticket is already available!");
        }
        setStateOfTicket(seat, TicketState.AVAILABLE);

    }

    public synchronized void cancelTicketReservations() {
        acceptReservations = false;
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

    private void setStateOfTicket(int seat, TicketState state) {
        Ticket ticket = tickets[seat];
        ticket.setState(state);
        tickets[seat] = ticket;
    }

    private void setTicketReserved(int seat, String name) {
        Ticket ticket = tickets[seat];
        ticket.setState(TicketState.RESERVED);
        ticket.setName(name);
        tickets[seat] = ticket;
    }

    protected void cancelAllTicketReservations() {
        Arrays.stream(tickets).forEach(this::cancelTicketReservation);
    }

    private void cancelTicketReservation(Ticket ticket) {
        if (ticket.getState().equals(TicketState.RESERVED)) {
            ticket.setName(null);
            ticket.setState(TicketState.AVAILABLE);
        }
    }

    public ArrayList<Integer> getAllAvailableTickets(){
        ArrayList<Integer> availableTickets = new ArrayList<>();
        for (int i=0; i<100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.AVAILABLE)) {
                availableTickets.add(i);
            }
        }
        return availableTickets;
    }

    public ArrayList<Integer> getAllSoldTickets(){
        ArrayList<Integer> soldTickets = new ArrayList<>();
        for (int i=0; i<100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.SOLD)) {
                soldTickets.add(i);
            }
        }
        return soldTickets;
    }

    public ArrayList<Integer> getAllReservedTickets(){
        ArrayList<Integer> reservedTickets = new ArrayList<>();
        for (int i=0; i<100; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getState().equals(TicketState.RESERVED)) {
                reservedTickets.add(i);
            }
        }
        return reservedTickets;
    }

}
